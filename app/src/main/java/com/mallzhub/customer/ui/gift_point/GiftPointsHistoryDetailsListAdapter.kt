package com.mallzhub.customer.ui.gift_point

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mallzhub.customer.AppExecutors
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.GiftPointHistoryDetailsListItemBinding
import com.mallzhub.customer.models.GiftPointsHistoryDetailsRewards
import com.mallzhub.customer.util.DataBoundListAdapter

class GiftPointsHistoryDetailsListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((GiftPointsHistoryDetailsRewards) -> Unit)? = null
) : DataBoundListAdapter<GiftPointsHistoryDetailsRewards, GiftPointHistoryDetailsListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<GiftPointsHistoryDetailsRewards>() {
        override fun areItemsTheSame(oldItem: GiftPointsHistoryDetailsRewards, newItem: GiftPointsHistoryDetailsRewards): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: GiftPointsHistoryDetailsRewards,
            newItem: GiftPointsHistoryDetailsRewards
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): GiftPointHistoryDetailsListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_gift_point_history_details, parent, false
        )
    }


    override fun bind(binding: GiftPointHistoryDetailsListItemBinding, position: Int) {
        val item = getItem(position)
        binding.title = item.remarks ?: "Reason not found"
        binding.description = if (item.is_approved == 1) "Status: Approved" else "Status: Not Approved"
        binding.point = item.reward?.toString() ?: "0"

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}