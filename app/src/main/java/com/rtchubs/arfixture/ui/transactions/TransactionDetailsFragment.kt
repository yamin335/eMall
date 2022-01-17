package com.rtchubs.arfixture.ui.transactions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.TransactionDetailsFragmentBinding
import com.rtchubs.arfixture.models.order.SalesData
import com.rtchubs.arfixture.ui.common.BaseFragment
import com.rtchubs.arfixture.ui.order.OrderDetailsProductListAdapter

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