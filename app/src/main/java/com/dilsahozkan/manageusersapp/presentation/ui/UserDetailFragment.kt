package com.dilsahozkan.manageusersapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dilsahozkan.manageusersapp.common.ViewState
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDetailDto
import com.dilsahozkan.manageusersapp.databinding.FragmentUserDetailBinding
import com.dilsahozkan.manageusersapp.presentation.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var viewModel: UserViewModel

    private val userId: Int? by lazy {
        arguments?.getInt(UserActivity.BUNDLE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.deleteUser.setOnClickListener {
            viewModel.deleteUser(userId)
        }

        binding.updateUser.setOnClickListener {
            val updatedUser = UserDetailDto(
                name = null,
                email = null,
                username = null,
                phone = null,
                website = null,
                address = null,
                company = null
            )
            viewModel.updateUser(userId, updatedUser)
        }

        userId?.let {
            viewModel.getUserDetail(it.toString()) }

        addObserver()

        return binding.root
    }

    private fun addObserver() {
        lifecycleScope.launch {
            viewModel.userState.collect {
                when (it) {
                    is ViewState.Success -> {
                        binding.item = it.data
                        binding.progressBar.isVisible = false
                        binding.infoLinear.isVisible = true
                        binding.moreInfo.isVisible = true
                    }
                    is ViewState.Error -> {
                        Snackbar.make(binding.root, it.message ?: "Kullanıcıları çekme başarısız", Snackbar.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = false
                        binding.infoLinear.isVisible = true
                        binding.moreInfo.isVisible = true
                    }

                    is ViewState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.infoLinear.isVisible = false
                        binding.moreInfo.isVisible = false
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.updateState.collect {
                when (it) {
                    is ViewState.Success -> {
                        Snackbar.make(binding.root, "Güncelleme başarılı, HTTP Kod: ${it.responseCode}, Yanıt: ${it.message}", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    is ViewState.Error -> {
                        Snackbar.make(binding.root, it.message ?: "Güncelleme işlemi başarısız", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.deleteState.collect {
                when (it) {
                    is ViewState.Success -> {
                        val message = """
                             Silme başarılı, HTTP Kod: ${it.responseCode}
                             Yanıt: ${it.message}
                             """.trimIndent()
                        Snackbar.make(binding.root, "Silme Başarılı, HTTP Kod: ${it.responseCode}, Yanıt: ${it.message}" , Snackbar.LENGTH_SHORT).show()
                        Log.d("DEBUG", message)
                        findNavController().popBackStack()
                    }
                    is ViewState.Error -> {
                        Snackbar.make(binding.root, it.message ?: "Silme işlemi başarısız", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }
}