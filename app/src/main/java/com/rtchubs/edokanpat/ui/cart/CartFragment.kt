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
import com.rtchubs.edokanpat.ui.add_payment_methods.AddPaymentMethodsFragment
import com.rtchubs.edokanpat.ui.add_payment_methods.AddPaymentMethodsFragmentDirections
import com.rtchubs.edokanpat.ui.add_payment_methods.BankOrCardListAdapter
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

    lateinit var cartItemListAdapter: CartItemListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        cartItemListAdapter = CartItemListAdapter(
            appExecutors
        ) { item ->
            viewModel.deleteCartItem(item)
        }

        viewDataBinding.rvCartItems.adapter = cartItemListAdapter

        viewModel.cartItems.observe(viewLifecycleOwner, Observer {
            it?.let { list ->

                if (list.isEmpty()) {
                    viewDataBinding.container.visibility = View.GONE
                    viewDataBinding.emptyView.visibility = View.VISIBLE
                } else {
                    viewDataBinding.container.visibility = View.VISIBLE
                    viewDataBinding.emptyView.visibility = View.GONE
                    cartItemListAdapter.submitList(list)

                    var total = 0.0
                    list.forEach { item ->
                        val price = item.product.mrp ?: 0.0
                        val quantity = item.quantity ?: 0
                        total += price * quantity
                    }
                    viewDataBinding.totalPrice = total.toString()
                }
            }
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