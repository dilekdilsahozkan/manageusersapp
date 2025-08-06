package com.dilsahozkan.manageusersapp.presentation.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dilsahozkan.manageusersapp.common.ViewState
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDetailDto
import com.dilsahozkan.manageusersapp.databinding.FragmentNewUserBinding
import com.dilsahozkan.manageusersapp.presentation.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class NewUserFragment : Fragment() {

    private lateinit var binding: FragmentNewUserBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewUserBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.saveUser.setOnClickListener {
            val name = binding.editName.text.toString()
            val email = binding.editEmail.text.toString()

            if (name.isBlank()) {
                Snackbar.make(binding.root, "Lütfen isminizi giriniz!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
                Snackbar.make(binding.root, "Geçerli bir e-posta giriniz!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newUser = UserDetailDto(
                name = name,
                email = email ,
                username = null,
                phone = null,
                website = null,
                address = null,
                company = null
            )

            viewModel.createUser(newUser)
        }
        addObserver()

        return binding.root
    }

    private fun addObserver() {
        lifecycleScope.launch {
            viewModel.userState.collect {
                when (it) {
                    is ViewState.Success -> {
                        Snackbar.make(binding.root, "Yeni kullanıcı kaydedildi!, HTTP Kod: ${it.responseCode}, Yanıt: ${it.message}", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }

                    is ViewState.Error -> {
                        Snackbar.make(binding.root, it.message ?: "Kayıt başarısız oldu", Snackbar.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }
    }
}