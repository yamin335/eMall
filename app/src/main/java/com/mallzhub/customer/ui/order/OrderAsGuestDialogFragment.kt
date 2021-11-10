package com.mallzhub.customer.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mallzhub.customer.R
import com.mallzhub.customer.binding.FragmentDataBindingComponent
import com.mallzhub.customer.databinding.OrderAsGuestDialogFragmentBinding
import com.mallzhub.customer.models.order.OrderStoreProduct
import com.mallzhub.customer.util.autoCleared
import com.mallzhub.customer.util.showSuccessToast
import com.mallzhub.customer.util.showWarningToast
import dagger.android.support.DaggerDialogFragment
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class OrderAsGuestDialogFragment internal constructor(
    private val callBack: PlaceOrderCallback,
    private val orderItems: ArrayList<OrderStoreProduct>,
    private val totalAmount: Int
): DaggerDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: OrderAsGuestDialogViewModel by viewModels {
        // Get the ViewModel.
        viewModelFactory
    }
    private var binding by autoCleared<OrderAsGuestDialogFragmentBinding>()
    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent()

    override fun getTheme(): Int {
        return R.style.DialogFullScreenTheme
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_fragment_order_as_guest,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        viewModel.name.observe(viewLifecycleOwner, Observer {
            binding.btnPlaceOrder.isEnabled = !it.isNullOrBlank()
        })
        
        viewModel.orderPlaceResponse.observe(viewLifecycleOwner, Observer {
            it?.let { response ->
                showSuccessToast(requireContext(), "Your order is placed successfully")
                dismiss()
            }
            binding.loader.visibility = View.GONE
        })

        binding.btnPlaceOrder.setOnClickListener {
            dismiss()
//            if (orderItems.isEmpty()) {
//                showWarningToast(requireContext(), "No items found in your cart, please add product to order!")
//                return@setOnClickListener
//            }
//            val items = ArrayList<OrderItem>()
//            orderItems.forEach {
//                items.add(OrderItem(1, it.product.name, it.quantity, it.product.mrp?.toInt(), 0))
//            }
//            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            val currentDate = dateFormatter.format(System.currentTimeMillis())
//            val order = Order(1, viewModel.name.value, viewModel.mobile.value, viewModel.address.value,
//                "", "", "", "pending", "", currentDate, totalAmount, items)
//            viewModel.placeOrder(order)
//            binding.loader.visibility = View.VISIBLE
        }
    }

    interface PlaceOrderCallback {
        fun onOrderPlaced()
    }
}