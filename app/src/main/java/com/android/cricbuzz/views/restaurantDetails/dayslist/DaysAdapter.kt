package com.android.cricbuzz.views.restaurantDetails.dayslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.cricbuzz.databinding.ItemDaysBinding
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class DaysAdapter @Inject constructor(
    val clickListener: ClickListener
) :  ListAdapter<DaysModel, DaysAdapter.ViewHolder>(UsersListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,position, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemDaysBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: DaysModel,
            position: Int,
            clickListener: ClickListener
        ) {
            binding.data = item
            binding.executePendingBindings()
            binding.mClickListener = clickListener
            binding.position=position
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDaysBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class UsersListDiffCallback : DiffUtil.ItemCallback<DaysModel>() {
        override fun areItemsTheSame(
            oldItem: DaysModel,
            newItem: DaysModel
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: DaysModel,
            newItem: DaysModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    class ClickListener @Inject constructor() {
        var onItemClick: ((DaysModel, position: Int) -> Unit)? = null
        fun onClick(data: DaysModel, position: Int) {
            onItemClick?.invoke(data, position)
        }
    }
}
