package com.android.cricbuzz.views.restaurantDetails.menulist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cricbuzz.databinding.ItemDaysBinding
import com.android.cricbuzz.databinding.ItemMenuListBinding
import com.android.cricbuzz.network.model.MenuModel
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class RestaurantMenuListAdapter @Inject constructor(
) :  ListAdapter<MenuModel.MenuItem, RestaurantMenuListAdapter.ViewHolder>(UsersListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemMenuListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: MenuModel.MenuItem,
            position: Int,
        ) {
            binding.data = item
            binding.executePendingBindings()
            binding.position=position
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMenuListBinding.inflate(layoutInflater, parent, false)
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

}
