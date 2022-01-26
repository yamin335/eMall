package com.rtchubs.arfixture.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.Home2Binding
import com.rtchubs.arfixture.models.Merchant
import com.rtchubs.arfixture.models.ShoppingMall
import com.rtchubs.arfixture.sceneform.HomeActivity
import com.rtchubs.arfixture.ui.LogoutHandlerCallback
import com.rtchubs.arfixture.ui.MainActivity
import com.rtchubs.arfixture.ui.NavDrawerHandlerCallback
import com.rtchubs.arfixture.ui.common.BaseFragment
import com.rtchubs.arfixture.ui.login.SliderView

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

    private var allShoppingMall = ArrayList<ShoppingMall>()

    private lateinit var shopOutletListAdapter: ShopOutletListAdapter

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


        shopOutletListAdapter = ShopOutletListAdapter(appExecutors) {
            navigateTo(Home2FragmentDirections.actionHome2FragmentToProductListFragment(it))
        }

        viewDataBinding.recyclerShopOutlets.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
        viewDataBinding.recyclerShopOutlets.adapter = shopOutletListAdapter

        viewDataBinding.btnNewChat.setOnClickListener {
            navigateTo(Home2FragmentDirections.actionHome2FragmentToBotNav())
        }

        viewDataBinding.appLogo.setOnClickListener {
            drawerListener?.toggleNavDrawer()
        }

        viewDataBinding.cartMenu.setOnClickListener {
            navController.navigate(Home2FragmentDirections.actionHome2FragmentToCartNavGraph())
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

        viewModel.slideDataList.forEach { slideData ->
            val slide = SliderView(requireContext())
            slide.sliderTextTitle = slideData.textTitle
            slide.sliderTextDescription = slideData.descText
            slide.sliderImage(slideData.slideImage)
            viewDataBinding.sliderLayout.addSlider(slide)
        }

//        viewModel.allShoppingMallResponse.observe(viewLifecycleOwner, Observer { response ->
//            response?.data?.let { mallList ->
//                allShoppingMall = mallList as ArrayList<ShoppingMall>
//                if (mallList.isEmpty()) return@Observer
//
//            }
//        })

        viewModel.allShopListResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.data?.let { shopList ->
                visibleGoneEmptyView(shopList)
                if (shopList.isEmpty()) return@Observer
                shopOutletListAdapter.submitList(shopList)

//                val mallId = args.shoppingMall.id ?: -1
//                val shops = ArrayList<Merchant>()
//                shopList.forEach { merchant ->
//                    if (merchant.shopping_mall_id == mallId)
//                        shops.add(merchant)
//                }
//
//                if (shops.isEmpty())
//                    return@Observer
//
//                var levels = ArrayList<ShoppingMallLevel>()
//                val levelsMap = HashMap<Int, ArrayList<Merchant>>()
//                shops.forEach { merchant ->
//                    val id = merchant.shopping_mall_level_id ?: -1
//                    if (levelsMap.containsKey(id)) {
//                        val arrayList = levelsMap[id]
//                        arrayList?.add(merchant)
//                    } else if (id != -1) {
//                        val arrayList = ArrayList<Merchant>()
//                        arrayList.add(merchant)
//                        levelsMap[id] = arrayList
//                        val lvls = merchant.shopping_mall?.levels
//                        if (levels.isEmpty() && !lvls.isNullOrEmpty()) {
//                            levels = lvls as ArrayList<ShoppingMallLevel>
//                        }
//                    }
//                }
//
//                if (shops.isEmpty() || levels.isEmpty())
//                    return@Observer
//
//                val lvlMap = HashMap<Int, ShoppingMallLevel>()
//                levels.forEach { level ->
//                    val id = level.id
//                    if (id != null && levelsMap.containsKey(id)) {
//                        lvlMap[id] = level
//                    }
//                }
//
//                val levelWiseShops = ArrayList<LevelWiseShops>()
//                val keys = levelsMap.keys.sorted()
//                keys.forEach { key ->
//                    levelWiseShops.add(LevelWiseShops(lvlMap[key], levelsMap[key]))
//                }
//                allShopListAdapter.submitList(levelWiseShops)
            }
        })

        viewModel.getAllShopList()
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

    private fun visibleGoneEmptyView(malls: List<Merchant>) {
        if (malls.isEmpty()) {
            viewDataBinding.recyclerShopOutlets.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.recyclerShopOutlets.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.toolbar_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
}