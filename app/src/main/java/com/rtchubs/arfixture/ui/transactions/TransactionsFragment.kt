package com.rtchubs.arfixture.ui.transactions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.databinding.TransactionsFragmentBinding
import com.rtchubs.arfixture.models.order.SalesData
import com.rtchubs.arfixture.ui.common.BaseFragment

class TransactionsFragment : BaseFragment<TransactionsFragmentBinding, TransactionsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_transactions
    override val viewModel: TransactionsViewModel by viewModels {
        viewModelFactory
    }

    lateinit var transactionsListAdapter: TransactionsListAdapter

    override fun onResume() {
        super.onResume()
        if (orderList.isEmpty()) {
            viewModel.getOrderList(1, "test@gmail.com")
        } else {
            transactionsListAdapter.submitList(orderList)
        }

        visibleGoneEmptyView()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            android.R.id.home -> {
//                navController.navigateUp()
//            }
//        }
//        return true
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        transactionsListAdapter = TransactionsListAdapter(appExecutors) {
            navigateTo(TransactionsFragmentDirections.actionTransactionsFragmentToTransactionDetailsFragment(it))
        }

        viewDataBinding.transactionsRecycler.adapter = transactionsListAdapter

        viewModel.orderItems.observe(viewLifecycleOwner, Observer {
            orderList = it as ArrayList<SalesData>
            transactionsListAdapter.submitList(orderList)
            visibleGoneEmptyView()
        })
    }

    private fun visibleGoneEmptyView() {
        if (orderList.isEmpty()) {
            viewDataBinding.container.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.container.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

    companion object {
        var orderList = ArrayList<SalesData>()
    }
}