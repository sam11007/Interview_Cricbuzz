package com.android.cricbuzz.views.restaurantDetails.resturantMenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cricbuzz.databinding.ItemMenuBinding
import com.android.cricbuzz.databinding.ItemRestaurantMenuBinding
import com.android.cricbuzz.network.model.MenuModel
import com.android.cricbuzz.network.model.RestaurantModel
import com.android.cricbuzz.views.menu.foodAdapter.FoodAdapter
import com.android.cricbuzz.views.restaurantDetails.menulist.RestaurantMenuListAdapter
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class RestaurantMenuAdapter @Inject constructor(
    val clickListener: ClickListener
) :  ListAdapter<MenuModel.Category, RestaurantMenuAdapter.ViewHolder>(UsersListDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item,position, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(private val binding: ItemRestaurantMenuBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: MenuModel.Category,
            position: Int,
            clickListener: ClickListener
        ) {
            binding.data = item
            binding.executePendingBindings()
            binding.mClickListener = clickListener
            binding.position=position

            val menuListAdapter = RestaurantMenuListAdapter()
            binding.rvRestaurantMenu.adapter = menuListAdapter
            var list = item.menuItems
            menuListAdapter.submitList(list)

        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRestaurantMenuBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class UsersListDiffCallback : DiffUtil.ItemCallback<MenuModel.Category>() {
        override fun areItemsTheSame(
            oldItem: MenuModel.Category,
            newItem: MenuModel.Category
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: MenuModel.Category,
            newItem: MenuModel.Category
        ): Boolean {
            return oldItem == newItem
        }
    }

    class ClickListener @Inject constructor() {
        var onItemClick: ((MenuModel.Category, position: Int) -> Unit)? = null
        fun onClick(data: MenuModel.Category, position: Int) {
            onItemClick?.invoke(data, position)
        }
    }
}
