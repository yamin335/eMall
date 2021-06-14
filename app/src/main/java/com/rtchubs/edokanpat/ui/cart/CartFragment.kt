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
import com.rtchubs.edokanpat.models.Order
import com.rtchubs.edokanpat.models.OrderItem
import com.rtchubs.edokanpat.ui.add_payment_methods.AddPaymentMethodsFragment
import com.rtchubs.edokanpat.ui.add_payment_methods.AddPaymentMethodsFragmentDirections
import com.rtchubs.edokanpat.ui.add_payment_methods.BankOrCardListAdapter
import com.rtchubs.edokanpat.ui.common.BaseFragment
import com.rtchubs.edokanpat.ui.home.ProductListAdapter
import com.rtchubs.edokanpat.ui.home.ProductListFragmentDirections
import com.rtchubs.edokanpat.util.GridRecyclerItemDecorator
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CartFragment : BaseFragment<CartFragmentBinding, CartViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_cart

    override val viewModel: CartViewModel by viewModels { viewModelFactory }

    lateinit var cartItemListAdapter: CartItemListAdapter

    var invoiceID: String? = null

    var order: Order? = null

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

                    val orderItems = ArrayList<OrderItem>()
                    var total = 0.0
                    var merchantId: Int? = 0
                    list.forEach { item ->
                        merchantId = item.product.merchant_id
                        val price = item.product.mrp ?: 0
                        val quantity = item.quantity ?: 0
                        total += price * quantity

                        orderItems.add(OrderItem(item.product.id, item.product.description, "qty",
                        quantity, price, 0,
                        "0.0", 0, "0.0",
                        price * quantity, item.product, null))
                    }

                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(
                        Date()
                    )

                    order = Order(6, merchantId, "",
                        invoiceID ?: generateInvoiceID(), today, "inclusive",
                    "", total.toInt(), 0,
                    0, total.toInt(), 0,
                        total.toInt(), orderItems)
                    viewDataBinding.totalPrice = total.toString()
                }
            }
        })

        viewDataBinding.checkoutNow.setOnClickListener {

        }

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

    private fun generateInvoiceID(): String {
        val random1 = "${1 + SecureRandom().nextInt(9999999)}"
        val random2 = "${1 + SecureRandom().nextInt(999999)}"
        return "IV-${random1}${random2}"
    }
}