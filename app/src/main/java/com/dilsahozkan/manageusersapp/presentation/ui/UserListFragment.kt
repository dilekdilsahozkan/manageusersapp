package com.dilsahozkan.manageusersapp.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dilsahozkan.manageusersapp.R
import com.dilsahozkan.manageusersapp.common.ViewState
import com.dilsahozkan.manageusersapp.databinding.FragmentUserListBinding
import com.dilsahozkan.manageusersapp.presentation.adapter.UserListAdapter
import com.dilsahozkan.manageusersapp.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var viewModel: UserViewModel

    private val userListAdapter by lazy {
        UserListAdapter(
            onRowClick = {
                findNavController().navigate(
                    R.id.action_movieFragment_to_movieDetailFragment,
                    bundleOf(
                        UserActivity.BUNDLE_ID to it.id
                    )
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        viewModel.getUserInfo()
        addObserver()

        binding.addUser.setOnClickListener {
            findNavController().navigate(
                R.id.action_movieFragment_to_newUserFragment
            )
        }

        binding.recyclerView.adapter = userListAdapter

        return binding.root
    }

    private fun addObserver() {
        lifecycleScope.launch {
            viewModel.userListState.collect {
                when (it) {
                    is ViewState.Success -> {
                        userListAdapter.submitList(it.data)
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isEnabled = true
                    }

                    is ViewState.Error -> {
                        println(it.message)
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isEnabled = true
                    }

                    is ViewState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.recyclerView.isEnabled = false
                    }

                    else -> {}
                }
            }
        }
    }
}