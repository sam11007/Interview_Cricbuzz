package com.android.cricbuzz.views.restaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cricbuzz.databinding.ListRestaurantBinding
import com.android.cricbuzz.network.model.RestaurantModel
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class RestaurantAdapter @Inject constructor(
    val clickListener: ClickListener
) :  ListAdapter<RestaurantModel.Restaurant, RestaurantAdapter.ViewHolder>(UsersListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item,position, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: RestaurantModel.Restaurant,
            position: Int,
            clickListener: ClickListener
        ) {
            binding.data = item
            binding.executePendingBindings()
              binding.mClickListener = clickListener
            binding.position=position
            val reviewList = item.reviews
            val size = reviewList?.size ?: 0
            val avg  = if(size!=0) (reviewList?.sumOf { it?.rating ?: 0 })?.div(size) else 0
            binding.ratingBar.rating = avg?.toFloat() ?:  0.0f
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListRestaurantBinding.inflate(layoutInflater, parent, false)
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
