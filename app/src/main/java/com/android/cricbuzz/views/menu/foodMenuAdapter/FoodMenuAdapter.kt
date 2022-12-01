package com.android.cricbuzz.views.menu.foodMenuAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cricbuzz.databinding.ItemFoodMenuBinding
import com.android.cricbuzz.network.model.MenuModel
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class FoodMenuAdapter @Inject constructor(
) :  ListAdapter<MenuModel.MenuItem, FoodMenuAdapter.ViewHolder>(UsersListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemFoodMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: MenuModel.MenuItem,
            position: Int
        ) {
            binding.data = item
            binding.executePendingBindings()
            binding.position=position
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFoodMenuBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class UsersListDiffCallback : DiffUtil.ItemCallback<MenuModel.MenuItem>() {
        override fun areItemsTheSame(
            oldItem: MenuModel.MenuItem,
            newItem: MenuModel.MenuItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: MenuModel.MenuItem,
            newItem: MenuModel.MenuItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    class ClickListener @Inject constructor() {
        var onItemClick: ((MenuModel.MenuItem, position: Int) -> Unit)? = null
        fun onClick(data: MenuModel.MenuItem, position: Int) {
            onItemClick?.invoke(data, position)
        }
    }
}
