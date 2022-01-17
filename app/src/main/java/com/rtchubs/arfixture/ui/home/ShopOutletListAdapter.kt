package com.rtchubs.arfixture.ui.home

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rtchubs.arfixture.AppExecutors
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.ShopOutletListItemBinding
import com.rtchubs.arfixture.models.Merchant

import com.rtchubs.arfixture.models.PaymentMethod
import com.rtchubs.arfixture.util.DataBoundListAdapter

class ShopOutletListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((Merchant) -> Unit)? = null

) : DataBoundListAdapter<Merchant, ShopOutletListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<Merchant>() {
        override fun areItemsTheSame(oldItem: Merchant, newItem: Merchant): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Merchant,
            newItem: Merchant
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    val onClicked = MutableLiveData<PaymentMethod>()
    override fun createBinding(parent: ViewGroup): ShopOutletListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_shop_outlet, parent, false
        )
    }


    override fun bind(binding: ShopOutletListItemBinding, position: Int) {
        binding.logo.setImageResource(R.drawable.shopping_mall)
        val item = getItem(position)
        binding.merchant = item

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }

        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.logo.setImageResource(R.drawable.shopping_mall)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }
    }
}