package com.dilsahozkan.manageusersapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dilsahozkan.manageusersapp.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dilsahozkan.manageusersapp.data.remote.dto.UserDto
import com.dilsahozkan.manageusersapp.databinding.ItemUserBinding

class UserListAdapter (private val onRowClick: ((result: UserDto) -> Unit)? = null
) :
    ListAdapter<UserDto, UserListAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<UserDto>() {
        override fun areItemsTheSame(
            oldItem: UserDto,
            newItem: UserDto
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: UserDto,
            newItem: UserDto
        ): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.item, item)
    }

    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.rootView.setOnClickListener {
                selectItem(bindingAdapterPosition)
            }
        }
    }

    fun selectItem(position: Int) {
        onRowClick?.invoke(getItem(position))
    }
}