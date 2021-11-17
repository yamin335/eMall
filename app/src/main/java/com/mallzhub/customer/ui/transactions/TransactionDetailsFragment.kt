package com.mallzhub.customer.ui.transactions

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.OrderTrackHistoryFragmentBinding
import com.mallzhub.customer.databinding.TransactionDetailsFragmentBinding
import com.mallzhub.customer.models.order.OrderTrackHistory
import com.mallzhub.customer.models.order.SalesData
import com.mallzhub.customer.ui.common.BaseFragment
import com.mallzhub.customer.ui.order.OrderDetailsProductListAdapter
import com.mallzhub.customer.ui.order.OrderTrackHistoryViewModel

class TransactionDetailsFragment : BaseFragment<TransactionDetailsFragmentBinding, TransactionDetailsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_transaction_details
    override val viewModel: TransactionDetailsViewModel by viewModels {
        viewModelFactory
    }

    lateinit var orderDetailsProductListAdapter: OrderDetailsProductListAdapter
    lateinit var order: SalesData

    val args: TransactionDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        order = args.order
        viewDataBinding.toolbar.title = order.OurReference

        viewDataBinding.name.text = order.customer?.name
        viewDataBinding.email.text = order.customer?.email
        viewDataBinding.address.text = order.customer?.address

        orderDetailsProductListAdapter = OrderDetailsProductListAdapter(appExecutors) {

        }

        viewDataBinding.productRecycler.setHasFixedSize(true)
        viewDataBinding.productRecycler.adapter = orderDetailsProductListAdapter
        orderDetailsProductListAdapter.submitList(order.details)

        viewDataBinding.vatTax = order.tax_type_total?.toString() ?: "0"
        viewDataBinding.discount = order.discount_type_total?.toString() ?: "0"
        viewDataBinding.totalPrice = order.grand_total?.toString() ?: "0"
        viewDataBinding.totalPaid = order.paid_amount?.toString() ?: "0"
        viewDataBinding.totalDue = order.due_amount?.toString() ?: "0"
    }
}