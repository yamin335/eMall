package com.mallzhub.customer.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.CartFragmentBinding
import com.mallzhub.customer.models.MerchantWiseOrder
import com.mallzhub.customer.ui.common.BaseFragment
import com.mallzhub.customer.util.showSuccessToast
import com.mallzhub.customer.models.order.OrderStoreBody
import com.mallzhub.customer.models.order.OrderStoreProduct
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

    lateinit var checkoutOptionBottomDialog: CheckoutOptionBottomDialog
    lateinit var cartOverviewBottomDialog: CartOverviewBottomDialog

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
            appExecutors,
            object : CartItemListAdapter.CartItemActionCallback {
                override fun incrementCartItemQuantity(id: Int) {
                    viewModel.incrementOrderItemQuantity(id)
                }

                override fun decrementCartItemQuantity(id: Int) {
                    viewModel.decrementOrderItemQuantity(id)
                }

            }, { item ->
                viewModel.deleteCartItem(item)
            }, { merchantWiseOrder ->

                cartOverviewBottomDialog = CartOverviewBottomDialog( object: CartOverviewBottomDialog.CheckoutOptionBottomDialogCallback {
                    override fun onCancelled() {
                        cartOverviewBottomDialog.dismiss()
                    }

                    override fun onOrder() {
                        cartOverviewBottomDialog.dismiss()
                        checkoutOptionBottomDialog = CheckoutOptionBottomDialog(object : CheckoutOptionBottomDialog.CheckoutOptionBottomDialogCallback {
                            override fun onGuestSelected() {
                                checkoutOptionBottomDialog.dismiss()

//                        val orderDialog = OrderDialogFragment(object : OrderDialogFragment.PlaceOrderCallback {
//                            override fun onOrderPlaced() {
//
//                            }
//                        }, viewModel.cartItems.value as ArrayList<CartItem>, total.toInt())
//
//                        orderDialog.show(childFragmentManager, "#Order_Dialog_Fragment")
                            }

                            override fun onLoginSelected() {
                                checkoutOptionBottomDialog.dismiss()
                            }
                        })
                        checkoutOptionBottomDialog.show(childFragmentManager, "#Checkout_Option_Dialog")
                    }
                }, appExecutors, merchantWiseOrder)
                cartOverviewBottomDialog
                cartOverviewBottomDialog.show(childFragmentManager, "#Checkout_Overview_Dialog")




//                val orderItems = ArrayList<OrderStoreProduct>()
//                var total = 0.0
//                var merchantId: Int? = 0
//                item.orderProductList.forEach { cartItem ->
//                    merchantId = cartItem.product.merchant_id
//                    val price = cartItem.product.mrp ?: 0
//                    val quantity = cartItem.quantity ?: 0
//                    total += price * quantity
//
//                    val product = cartItem.product
//
//                    orderItems.add(
//                        OrderStoreProduct(product.id, product.description, "qty",
//                            quantity, price, 0, "0",
//                            0, "0", price * quantity, "")
//                    )
//                }
//
//                val today = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(
//                    Date()
//                )
//
//                order = OrderStoreBody(8, 1,
//                    "", viewModel.invoiceNumber.value ?: viewModel.generateInvoiceID(),
//                    today, "inclusive",
//                    "", total.toInt(),
//                    0, 0, total.toInt(), 0, total.toInt(), orderItems)
//                order?.let {
//                    viewModel.placeOrder(it)
//                }
            }
        )

        viewDataBinding.rvCartItems.adapter = cartItemListAdapter

        viewModel.cartItems.observe(viewLifecycleOwner, Observer { cartItem ->
            cartItem?.let { list ->
                if (list.isEmpty()) {
                    viewDataBinding.rvCartItems.visibility = View.GONE
                    viewDataBinding.emptyView.visibility = View.VISIBLE
                } else {
                    viewDataBinding.rvCartItems.visibility = View.VISIBLE
                    viewDataBinding.emptyView.visibility = View.GONE

                    val merchantWiseProductsMap = list.groupBy { it.product.merchant_id }

                    val merchantWiseProductsList: ArrayList<MerchantWiseOrder> = ArrayList()
                    for (key in merchantWiseProductsMap.keys) {
                        val productsList = merchantWiseProductsMap[key]
                        val merchantName = if (!productsList.isNullOrEmpty()) {
                            val shopName = productsList[0].product.merchant?.shop_name
                            if (shopName.isNullOrBlank()) "Unknown Shop" else shopName
                        } else {
                            "Unknown Shop"
                        }

                        if (key != null && !productsList.isNullOrEmpty()) {
                            var total = 0.0
                            productsList.forEach { cartItem ->
                                val price = cartItem.product.mrp ?: 0
                                val quantity = cartItem.quantity ?: 0
                                total += price * quantity
                            }

                            val ss = productsList + productsList
                            merchantWiseProductsList.add(MerchantWiseOrder(key, merchantName, total.toString(), ss))
                        }
                    }

                    cartItemListAdapter.submitList(merchantWiseProductsList)
                }
            }
        })
    }
}