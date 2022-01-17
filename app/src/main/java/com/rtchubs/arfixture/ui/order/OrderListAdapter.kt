package com.rtchubs.arfixture.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.rtchubs.arfixture.AppExecutors
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.OrderListItemBinding
import com.rtchubs.arfixture.models.order.SalesData
import com.rtchubs.arfixture.util.AppConstants.orderCancelled
import com.rtchubs.arfixture.util.AppConstants.orderDelivered
import com.rtchubs.arfixture.util.AppConstants.orderPicked
import com.rtchubs.arfixture.util.AppConstants.orderShipped
import com.rtchubs.arfixture.util.DataBoundListAdapter

class OrderListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((SalesData) -> Unit)? = null
) : DataBoundListAdapter<SalesData, OrderListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<SalesData>() {
        override fun areItemsTheSame(oldItem: SalesData, newItem: SalesData): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: SalesData,
            newItem: SalesData
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): OrderListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_order, parent, false
        )
    }


    override fun bind(binding: OrderListItemBinding, position: Int) {
        val item = getItem(position)
        binding.invoice = item.OurReference ?: "Undefined Invoice"
        binding.date = item.date ?: "No date found"
        binding.orderStatus = item.status ?: "Undefined"

        val context = binding.root.context

        when(item.status) {
            orderPicked -> {
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.status_order_picked)
                binding.status.setTextColor(context.getColor(R.color.orderPicked))
            }
            orderShipped -> {
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.status_order_shipped)
                binding.status.setTextColor(context.getColor(R.color.orderShipped))
            }
            orderDelivered -> {
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.status_order_delivered)
                binding.status.setTextColor(context.getColor(R.color.orderDelivered))
            }
            orderCancelled -> {
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.status_order_cancelled)
                binding.status.setTextColor(context.getColor(R.color.orderCancelled))
            }
            else -> {
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.status_order_processing)
                binding.status.setTextColor(context.getColor(R.color.orderProcessing))
            }
        }

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}