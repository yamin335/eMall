package com.mallzhub.customer.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.CartFragmentBinding
import com.mallzhub.customer.models.Order
import com.mallzhub.customer.models.OrderItem
import com.mallzhub.customer.ui.common.BaseFragment
import com.mallzhub.customer.util.showSuccessToast
import com.mallzhub.shop.models.order.OrderStoreBody
import com.mallzhub.shop.models.order.OrderStoreProduct
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

    var order: OrderStoreBody? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        viewModel.orderPlaceResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            response?.let {
                if (it.data?.sale != null) {
                    viewModel.deleteAllCartItems()
                    showSuccessToast(requireContext(), "Order submitted successfully!")
                    navController.popBackStack()
                }
            }
        })

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

                    val orderItems = ArrayList<OrderStoreProduct>()
                    var total = 0.0
                    var merchantId: Int? = 0
                    list.forEach { item ->
                        merchantId = item.product.merchant_id
                        val price = item.product.mrp ?: 0
                        val quantity = item.quantity ?: 0
                        total += price * quantity

                        val product = item.product

                        orderItems.add(OrderStoreProduct(product.id, product.description, "qty",
                            quantity, price, 0, "0",
                            0, "0", price * quantity, ""))
                    }

                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(
                        Date()
                    )

                    order = OrderStoreBody(8, 1,
                        "", viewModel.invoiceNumber.value ?: viewModel.generateInvoiceID(),
                        today, "inclusive",
                        "", total.toInt(),
                        0, 0, total.toInt(), 0, total.toInt(), orderItems)

                    viewDataBinding.totalPrice = total.toString()
                }
            }
        })

        viewDataBinding.checkoutNow.setOnClickListener {
            order?.let {
                viewModel.placeOrder(it)
            }
        }
    }
}