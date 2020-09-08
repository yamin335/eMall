package com.qpay.customer.ui.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.navOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.qpay.customer.BR
import com.qpay.customer.R
import com.qpay.customer.databinding.Home2Binding
import com.qpay.customer.databinding.LayoutAddPaymentMethodBinding
import com.qpay.customer.models.Bank
import com.qpay.customer.models.registration.DefaultResponse
import com.qpay.customer.models.topup.TopUpHelper
import com.qpay.customer.ui.common.BaseFragment
import com.qpay.customer.ui.login.SliderView
import com.qpay.customer.ui.pin_number.PinNumberFragmentDirections
import com.qpay.customer.util.showWarningToast
import org.json.JSONObject

class Home2Fragment : BaseFragment<Home2Binding, HomeViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_main2
    override val viewModel: HomeViewModel by viewModels {
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

        viewDataBinding.cardTopUp.setOnClickListener {
            navController.navigate(Home2FragmentDirections.actionHome2FragmentToTopUpMobileFragment(
                TopUpHelper()
            ))
        }

        val token = preferencesHelper.getAccessTokenHeader()

        paymentListAdapter = PaymentMethodListAdapter(appExecutors) {
            //navController.navigate(HomeFragmentDirections.actionBooksToChapterList(it))
        }



        viewModel.slideDataList.forEach { slideData ->
            val slide = SliderView(requireContext())
            slide.sliderTextTitle = slideData.textTitle
            slide.sliderTextDescription = slideData.descText
            slide.sliderImage(slideData.slideImage)
            viewDataBinding.sliderLayout.addSlider(slide)
        }

        Log.e("res", preferencesHelper.getAccessTokenHeader())
        paymentListAdapter.submitList(viewModel.paymentMethodList)
        viewDataBinding.recyclerPaymentMethods.adapter = paymentListAdapter



        paymentListAdapter.onClicked.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.id == "-1") {
                    /**
                     * add payment method
                     */
                    val action = Home2FragmentDirections.actionHome2FragmentToAddPaymentMethodsFragment()
                    navController.navigate(action)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}