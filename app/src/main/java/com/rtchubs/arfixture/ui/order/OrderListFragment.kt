package com.rtchubs.arfixture.ui.order

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.OrderListFragmentBinding
import com.rtchubs.arfixture.models.order.SalesData
import com.rtchubs.arfixture.ui.NavDrawerHandlerCallback
import com.rtchubs.arfixture.ui.common.BaseFragment
import com.rtchubs.arfixture.BR

class OrderListFragment : BaseFragment<OrderListFragmentBinding, OrderViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_order_list
    override val viewModel: OrderViewModel by viewModels {
        viewModelFactory
    }

    lateinit var orderListAdapter: OrderListAdapter

    private var drawerListener: NavDrawerHandlerCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavDrawerHandlerCallback) {
            drawerListener = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        drawerListener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        if (orderList.isEmpty()) {
            viewModel.getOrderList(1, "test@gmail.com")
        } else {
            orderListAdapter.submitList(orderList)
        }

        visibleGoneEmptyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.appLogo.setOnClickListener {
            drawerListener?.toggleNavDrawer()
        }

        orderListAdapter = OrderListAdapter(appExecutors) {
            navigateTo(OrderListFragmentDirections.actionOrderListFragmentToOrderTrackHistoryFragment(it))
        }

        viewDataBinding.orderRecycler.adapter = orderListAdapter

        viewModel.orderItems.observe(viewLifecycleOwner, Observer {
            orderList = it as ArrayList<SalesData>
            orderListAdapter.submitList(orderList)
            visibleGoneEmptyView()
        })

        viewDataBinding.cartMenu.setOnClickListener {
            navController.navigate(OrderListFragmentDirections.actionOrderListFragmentToCartNavGraph())
        }

        viewModel.cartItemCount.observe(viewLifecycleOwner, Observer {
            it?.let { value ->
                if (value < 1) {
                    viewDataBinding.badge.visibility = View.INVISIBLE
                    return@Observer
                } else {
                    viewDataBinding.badge.visibility = View.VISIBLE
                    viewDataBinding.badge.text = value.toString()
                }
            }
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