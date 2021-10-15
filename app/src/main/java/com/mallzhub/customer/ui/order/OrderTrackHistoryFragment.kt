package com.mallzhub.customer.ui.order

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.OrderTrackHistoryFragmentBinding
import com.mallzhub.customer.models.order.OrderTrackHistory
import com.mallzhub.customer.ui.common.BaseFragment

class OrderTrackHistoryFragment : BaseFragment<OrderTrackHistoryFragmentBinding, OrderTrackHistoryViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_order_track_history
    override val viewModel: OrderTrackHistoryViewModel by viewModels {
        viewModelFactory
    }

    lateinit var orderTrackHistoryListAdapter: OrderTrackHistoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        viewDataBinding.toolbar.title = title

        orderTrackHistoryListAdapter = OrderTrackHistoryListAdapter (appExecutors)

        viewDataBinding.trackRecycler.adapter = orderTrackHistoryListAdapter

        val trackHistory = ArrayList<OrderTrackHistory>()
        var i = 0
        while (i < 8) {
            var isActive = false
            if (i == 0) isActive = true
            trackHistory.add(OrderTrackHistory(i, "Jan 02, 2021","10:05 AM",
                "Arrived at Delivery Facility in SAN FRANCISCO GATEWAY - USA",
                "SAN FRANCISCO GATEWAY,CA - US, United States", isActive))
            i++
        }

        orderTrackHistoryListAdapter.submitList(trackHistory)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }
        }
        return true
    }

    companion object {
        var title = ""
    }
}