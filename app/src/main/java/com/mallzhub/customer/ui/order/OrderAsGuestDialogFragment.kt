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
import com.mallzhub.customer.models.order.OrderStoreBody
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
    private val orderPlaceCallback: PlaceOrderCallback,
    private val orderStoreBody: OrderStoreBody
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

        viewModel.userNote.observe(viewLifecycleOwner, Observer {
            orderStoreBody.customer_note = it
        })

        viewModel.orderPlaceResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            response?.let {
                if (it.data?.sale != null) {
                    val items = orderStoreBody.list ?: ArrayList()
                    val ids = ArrayList<Int>()
                    for (item in items) {
                        ids.add(item.product_id ?: 0)
                    }
                    viewModel.deleteCartItemsByIds(ids)
                    showSuccessToast(requireContext(), "Your order is placed successfully")
                    dismiss()
                    orderPlaceCallback.onOrderPlaced()
                }
                binding.loader.visibility = View.GONE
            }
        })

        binding.btnPlaceOrder.setOnClickListener {
            binding.loader.visibility = View.VISIBLE
            viewModel.placeOrder(orderStoreBody)
        }
    }

    interface PlaceOrderCallback {
        fun onOrderPlaced()
    }
}