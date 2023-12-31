package com.rtchubs.arfixture.ui.gift_point

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.GiftPointHistoryDetailsFragmentBinding
import com.rtchubs.arfixture.models.GiftPointsHistoryDetailsRewards
import com.rtchubs.arfixture.ui.common.BaseFragment

class GiftPointHistoryDetailsFragment : BaseFragment<GiftPointHistoryDetailsFragmentBinding, GiftPointHistoryDetailsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_gift_point_history_details
    override val viewModel: GiftPointHistoryDetailsViewModel by viewModels {
        viewModelFactory
    }

    lateinit var pointHistoryListAdapter: GiftPointsHistoryDetailsListAdapter

    private val args: GiftPointHistoryDetailsFragmentArgs by navArgs()

    override fun onResume() {
        super.onResume()
        viewModel.getGiftPointsHistoryDetails(8, args.merchantId)
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

        viewDataBinding.toolbar.title = args.title

        pointHistoryListAdapter = GiftPointsHistoryDetailsListAdapter(appExecutors) {
            //navigateTo(TransactionsFragmentDirections.actionTransactionsFragmentToTransactionDetailsFragment(it))
        }

        viewDataBinding.historyRecycler.adapter = pointHistoryListAdapter

        viewModel.giftPointsHistoryDetailsResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                giftPointHistoryList = it.rewards as ArrayList<GiftPointsHistoryDetailsRewards>
                viewDataBinding.totalPoints = response.total_reward?.toString() ?: "0"
                pointHistoryListAdapter.submitList(giftPointHistoryList)
                visibleGoneEmptyView()
            }
        })
    }

    private fun visibleGoneEmptyView() {
        if (giftPointHistoryList.isEmpty()) {
            viewDataBinding.container.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.container.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

    companion object {
        var giftPointHistoryList = ArrayList<GiftPointsHistoryDetailsRewards>()
    }
}