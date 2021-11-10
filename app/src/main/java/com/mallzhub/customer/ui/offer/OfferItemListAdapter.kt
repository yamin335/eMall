package com.mallzhub.customer.ui.offer

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mallzhub.customer.AppExecutors
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.ShopWiseCartListItemBinding
import com.mallzhub.customer.databinding.ShopWiseOfferListItemBinding
import com.mallzhub.customer.local_db.dbo.CartItem
import com.mallzhub.customer.models.MerchantWiseOffer
import com.mallzhub.customer.models.MerchantWiseOrder
import com.mallzhub.customer.models.OfferProductListResponseData

import com.mallzhub.customer.util.DataBoundListAdapter
import com.mallzhub.customer.models.order.OrderStoreBody
import com.mallzhub.customer.models.order.OrderStoreProduct
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OfferItemListAdapter(
    private val appExecutors: AppExecutors,
    private val productSelectionCallback: (OfferProductListResponseData) -> Unit

) : DataBoundListAdapter<MerchantWiseOffer, ShopWiseOfferListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<MerchantWiseOffer>() {
        override fun areItemsTheSame(oldItem: MerchantWiseOffer, newItem: MerchantWiseOffer): Boolean {
            return oldItem.merchantId == newItem.merchantId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MerchantWiseOffer,
            newItem: MerchantWiseOffer
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): ShopWiseOfferListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_offer, parent, false
        )
    }

    override fun bind(binding: ShopWiseOfferListItemBinding, position: Int) {
        val item = getItem(position)

        val offerProductListAdapter = OfferProductListAdapter(appExecutors) {
            productSelectionCallback.invoke(it)
        }

        binding.rvOfferItems.setHasFixedSize(true)
        binding.rvOfferItems.adapter = offerProductListAdapter
        offerProductListAdapter.submitList(item.offerProductList)
        binding.shopName = item.merchantName
    }
}