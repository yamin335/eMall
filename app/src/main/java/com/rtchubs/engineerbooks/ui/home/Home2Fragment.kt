package com.rtchubs.engineerbooks.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.rtchubs.engineerbooks.BR
import com.rtchubs.engineerbooks.R
import com.rtchubs.engineerbooks.databinding.Home2Binding
import com.rtchubs.engineerbooks.ui.LogoutHandlerCallback
import com.rtchubs.engineerbooks.ui.NavDrawerHandlerCallback
import com.rtchubs.engineerbooks.ui.common.BaseFragment
import com.rtchubs.engineerbooks.ui.login.SliderView

class Home2Fragment : BaseFragment<Home2Binding, HomeViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_main2
    override val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    lateinit var paymentListAdapter: PaymentMethodListAdapter

    private var listener: LogoutHandlerCallback? = null

    private var drawerListener: NavDrawerHandlerCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LogoutHandlerCallback) {
            listener = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }

        if (context is NavDrawerHandlerCallback) {
            drawerListener = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        drawerListener = null
    }

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

//        viewDataBinding.cardTopUp.setOnClickListener {
//            navController.navigate(Home2FragmentDirections.actionHome2FragmentToTopUpMobileFragment(
//                TopUpHelper()
//            ))
//        }
//
//        val token = preferencesHelper.getAccessTokenHeader()
//
//        paymentListAdapter = PaymentMethodListAdapter(appExecutors) {
//            //navController.navigate(HomeFragmentDirections.actionBooksToChapterList(it))
//        }
//
//
//
        viewDataBinding.appLogo.setOnClickListener {
            drawerListener?.toggleNavDrawer()
        }

        viewModel.slideDataList.forEach { slideData ->
            val slide = SliderView(requireContext())
            slide.sliderTextTitle = slideData.textTitle
            slide.sliderTextDescription = slideData.descText
            slide.sliderImage(slideData.slideImage)
            viewDataBinding.sliderLayout.addSlider(slide)
        }

        viewDataBinding.item1.setOnClickListener {
            navController.navigate(Home2FragmentDirections.actionHome2FragmentToChapterListFragment())
        }

        viewDataBinding.item6.setOnClickListener {
            navController.navigate(Home2FragmentDirections.actionHome2FragmentToMoreBookListFragment())
        }
//
//        Log.e("res", preferencesHelper.getAccessTokenHeader())
//        paymentListAdapter.submitList(viewModel.paymentMethodList)
//        viewDataBinding.recyclerPaymentMethods.adapter = paymentListAdapter
//
//
//
//        paymentListAdapter.onClicked.observe(viewLifecycleOwner, Observer {
//            if (it != null) {
//                if (it.id == "-1") {
//                    /**
//                     * add payment method
//                     */
//                    val action = Home2FragmentDirections.actionHome2FragmentToAddPaymentMethodsFragment()
//                    navController.navigate(action)
//                }
//            }
//        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.toolbar_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
}