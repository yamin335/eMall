package com.mallzhub.customer.ui.wallet

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.WalletFragmentBinding
import com.mallzhub.customer.models.topup.TopUpHelper
import com.mallzhub.customer.ui.common.BaseFragment
import com.mallzhub.customer.ui.home.Home2FragmentDirections
import com.mallzhub.customer.ui.home.PaymentMethodListAdapter
import com.mallzhub.customer.ui.login.SliderView

class WalletFragment : BaseFragment<WalletFragmentBinding, WalletViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_wallet
    override val viewModel: WalletViewModel by viewModels {
        viewModelFactory
    }


    lateinit var paymentListAdapter: PaymentMethodListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mActivity.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //registerToolbar(viewDataBinding.toolbar)

        viewDataBinding.btnShow.setOnClickListener {
            navigateTo(WalletFragmentDirections.actionWalletFragmentToGiftPointHistoryFragment())
        }

        viewDataBinding.cartMenu.setOnClickListener {
            navController.navigate(WalletFragmentDirections.actionWalletFragmentToCartNavGraph())
        }

        viewDataBinding.cardTopUp.setOnClickListener {
            navController.navigate(WalletFragmentDirections.actionWalletFragmentToTopUpMobileFragment(
                TopUpHelper()
            ))
        }

        val token = preferencesHelper.getAccessTokenHeader()

        paymentListAdapter = PaymentMethodListAdapter(appExecutors) {
            //navController.navigate(HomeFragmentDirections.actionBooksToChapterList(it))
        }

//        viewModel.slideDataList.forEach { slideData ->
//            val slide = SliderView(requireContext())
//            slide.sliderTextTitle = slideData.textTitle
//            slide.sliderTextDescription = slideData.descText
//            slide.sliderImage(slideData.slideImage)
//            viewDataBinding.sliderLayout.addSlider(slide)
//        }

        paymentListAdapter.submitList(viewModel.paymentMethodList)
        viewDataBinding.recyclerPaymentMethods.adapter = paymentListAdapter

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

        paymentListAdapter.onClicked.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.id == "-1") {
                    /**
                     * add payment method
                     */
                    val action = WalletFragmentDirections.actionWalletFragmentToAddPaymentMethodsFragment()
                    navController.navigate(action)
                }
            }
        })
    }
}