package com.mallzhub.customer.ui.gift_point

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mallzhub.customer.AppExecutors
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.GiftPointHistoryListItemBinding
import com.mallzhub.customer.models.ShopWiseGiftPointRewards
import com.mallzhub.customer.util.DataBoundListAdapter

class GiftPointsListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((ShopWiseGiftPointRewards) -> Unit)? = null
) : DataBoundListAdapter<ShopWiseGiftPointRewards, GiftPointHistoryListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<ShopWiseGiftPointRewards>() {
        override fun areItemsTheSame(oldItem: ShopWiseGiftPointRewards, newItem: ShopWiseGiftPointRewards): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: ShopWiseGiftPointRewards,
            newItem: ShopWiseGiftPointRewards
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
        binding.shopName = item.shop_name ?: "Unknown Shop"
        binding.giftPoint = item.total_rewards?.toString() ?: "0"

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}