package com.mallzhub.customer.ui.cart

import android.content.Context
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
import com.mallzhub.customer.ui.OrderTabSelection
import com.mallzhub.customer.ui.order.OrderAsGuestDialogFragment
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

    lateinit var checkoutOptionBottomDialog: CheckoutOptionBottomDialog
    lateinit var cartOverviewBottomDialog: CartOverviewBottomDialog

    private var orderTabSelection: OrderTabSelection? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OrderTabSelection) {
            orderTabSelection = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

//        viewModel.orderPlaceResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
//            response?.let {
//                if (it.data?.sale != null) {
//                    viewModel.deleteAllCartItems()
//                    showSuccessToast(requireContext(), "Order submitted successfully!")
//                    navController.popBackStack()
//                }
//            }
//        })

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
                                val orderItems = ArrayList<OrderStoreProduct>()
                                val total = merchantWiseOrder.totalPrice
                                merchantWiseOrder.orderProductList.forEach { cartItem ->
                                    val price = cartItem.product.mrp ?: 0
                                    val quantity = cartItem.quantity ?: 0

                                    val product = cartItem.product

                                    orderItems.add(
                                        OrderStoreProduct(product.id, product.description, "qty",
                                            quantity, price, 0, "0",
                                            0, "0", price * quantity, "")
                                    )
                                }

                                val today = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(
                                    Date()
                                )

                                val order = OrderStoreBody(8, merchantWiseOrder.merchantId,
                                    "", viewModel.generateInvoiceID(),
                                    today, "inclusive",
                                    "", total.toDouble().toInt(),
                                    0, 0, total.toDouble().toInt(), 0, total.toDouble().toInt(), orderItems)

                                val orderAsGuestDialog = OrderAsGuestDialogFragment(object : OrderAsGuestDialogFragment.PlaceOrderCallback {
                                    override fun onOrderPlaced() {
                                        navController.popBackStack()
                                        orderTabSelection?.selectOrderTab()
                                    }
                                }, order)

                                orderAsGuestDialog.show(childFragmentManager, "#Order_As_Guest_Dialog_Fragment")
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

                            merchantWiseProductsList.add(MerchantWiseOrder(key, merchantName, total.toString(), productsList))
                        }
                    }

                    cartItemListAdapter.submitList(merchantWiseProductsList)
                }
            }
        })
    }
}