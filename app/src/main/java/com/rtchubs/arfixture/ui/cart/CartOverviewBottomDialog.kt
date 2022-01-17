package com.rtchubs.arfixture.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rtchubs.arfixture.AppExecutors
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.binding.FragmentDataBindingComponent
import com.rtchubs.arfixture.databinding.CartOverviewBottomDialogBinding
import com.rtchubs.arfixture.models.MerchantWiseOrder
import com.rtchubs.arfixture.util.autoCleared
import com.rtchubs.arfixture.util.screenSizeInDp

class CartOverviewBottomDialog constructor(private val callback: CheckoutOptionBottomDialogCallback, private val appExecutors: AppExecutors, private val order: MerchantWiseOrder) : BottomSheetDialogFragment() {

    private var binding by autoCleared<CartOverviewBottomDialogBinding>()
    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent()

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_bottom_order_overview,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val offsetFromTop = requireActivity().screenSizeInDp.y / 3
        //val offsetFromTop = 100
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isDraggable = false
            isFitToContents = true
            expandedOffset = offsetFromTop
            isHideable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.btnCancel.setOnClickListener {
            callback.onCancelled()
        }

        binding.btnOrderNow.setOnClickListener {
            callback.onOrder()
        }

        val adapter = CartOverviewProductListAdapter(appExecutors)
        //binding.rvCartItems.setHasFixedSize(true)
        binding.rvCartItems.adapter = adapter
        adapter.submitList(order.orderProductList)
        binding.totalPrice = order.totalPrice
        binding.shopName = "Shop Name: ${order.merchantName}"
    }

    interface CheckoutOptionBottomDialogCallback {
        fun onCancelled()
        fun onOrder()
    }
}