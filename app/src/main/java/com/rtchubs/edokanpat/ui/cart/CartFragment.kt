package com.rtchubs.edokanpat.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.CartFragmentBinding
import com.rtchubs.edokanpat.databinding.MoreFragmentBinding
import com.rtchubs.edokanpat.databinding.SetAFragmentBinding
import com.rtchubs.edokanpat.ui.common.BaseFragment
import com.rtchubs.edokanpat.ui.home.ProductListAdapter
import com.rtchubs.edokanpat.ui.home.ProductListFragmentDirections
import com.rtchubs.edokanpat.util.GridRecyclerItemDecorator

class CartFragment : BaseFragment<CartFragmentBinding, CartViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_cart

    override val viewModel: CartViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        viewModel.cartItems.observe(viewLifecycleOwner, Observer {
            val list = it
            val tt = list
        })

//        viewDataBinding.toolbar.title = args.merchant.name
//
//        productListAdapter = ProductListAdapter(
//            appExecutors
//        ) { item ->
//            navController.navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(item))
//        }
//
//        viewDataBinding.rvProductList.addItemDecoration(GridRecyclerItemDecorator(2, 20, true))
//        viewDataBinding.rvProductList.layoutManager = GridLayoutManager(mContext, 2)
//        viewDataBinding.rvProductList.adapter = productListAdapter
//
//        viewModel.productListResponse.observe(viewLifecycleOwner, Observer { response ->
//            response?.data?.let { productList ->
//                productListAdapter.submitList(productList)
//            }
//        })
//
//        viewModel.getProductList(args.merchant.id.toString())
    }
}