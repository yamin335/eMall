package com.mallzhub.customer.ui.gift_point

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mallzhub.customer.AppExecutors
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.GiftPointHistoryListItemBinding
import com.mallzhub.customer.models.GiftPointHistoryItem
import com.mallzhub.customer.util.DataBoundListAdapter

class GiftPointsListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((GiftPointHistoryItem) -> Unit)? = null
) : DataBoundListAdapter<GiftPointHistoryItem, GiftPointHistoryListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<GiftPointHistoryItem>() {
        override fun areItemsTheSame(oldItem: GiftPointHistoryItem, newItem: GiftPointHistoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: GiftPointHistoryItem,
            newItem: GiftPointHistoryItem
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): GiftPointHistoryListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_gift_point_history, parent, false
        )
    }


    override fun bind(binding: GiftPointHistoryListItemBinding, position: Int) {
        val item = getItem(position)
        binding.shopName = item.shopName ?: "Unknown Shop"
        binding.giftPoint = item.point?.toString() ?: "0"

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}