package com.android.cricbuzz.views.menu.menuAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cricbuzz.databinding.ItemMenuBinding
import com.android.cricbuzz.network.model.RestaurantModel
import com.android.cricbuzz.views.menu.foodAdapter.FoodAdapter
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class MenuAdapter @Inject constructor(
    val clickListener: ClickListener
) :  ListAdapter<RestaurantModel.Restaurant, MenuAdapter.ViewHolder>(UsersListDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item,position, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(private val binding: ItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: RestaurantModel.Restaurant,
            position: Int,
            clickListener: ClickListener
        ) {
            binding.data = item
            binding.executePendingBindings()
            binding.mClickListener = clickListener
            binding.position=position

            val foodAdapter = FoodAdapter()
            binding.rvMenu.adapter = foodAdapter
            var list = item.categories
            foodAdapter.submitList(list)

        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMenuBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class UsersListDiffCallback : DiffUtil.ItemCallback<RestaurantModel.Restaurant>() {
        override fun areItemsTheSame(
            oldItem: RestaurantModel.Restaurant,
            newItem: RestaurantModel.Restaurant
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: RestaurantModel.Restaurant,
            newItem: RestaurantModel.Restaurant
        ): Boolean {
            return oldItem == newItem
        }
    }

    class ClickListener @Inject constructor() {
        var onItemClick: ((RestaurantModel.Restaurant, position: Int) -> Unit)? = null
        fun onClick(data: RestaurantModel.Restaurant, position: Int) {
            onItemClick?.invoke(data, position)
        }
    }
}
