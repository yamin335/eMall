package com.mallzhub.customer.ui.transactions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mallzhub.customer.AppExecutors
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.OrderListItemBinding
import com.mallzhub.customer.databinding.TransactionListItemBinding
import com.mallzhub.customer.models.order.SalesData
import com.mallzhub.customer.util.AppConstants.orderCancelled
import com.mallzhub.customer.util.AppConstants.orderDelivered
import com.mallzhub.customer.util.AppConstants.orderPicked
import com.mallzhub.customer.util.AppConstants.orderShipped
import com.mallzhub.customer.util.DataBoundListAdapter

class TransactionsListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((SalesData) -> Unit)? = null
) : DataBoundListAdapter<SalesData, TransactionListItemBinding>(
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

    override fun createBinding(parent: ViewGroup): TransactionListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_transaction, parent, false
        )
    }


    override fun bind(binding: TransactionListItemBinding, position: Int) {
        val item = getItem(position)
        binding.invoice = item.OurReference ?: "Undefined Invoice"
        binding.date = item.date ?: "No date found"
        binding.subTotal = "${binding.root.context.getString(R.string.sign_taka)} ${item.grand_total?.toString() ?: "0"}"

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}