package com.rtchubs.arfixture.ui.offer

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rtchubs.arfixture.AppExecutors
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.OfferProductListItemBinding
import com.rtchubs.arfixture.models.OfferProduct
import com.rtchubs.arfixture.util.DataBoundListAdapter

class OfferProductListAdapter(
    private val appExecutors: AppExecutors,
    private val itemSelectionCallback: ((OfferProduct) -> Unit)
) : DataBoundListAdapter<OfferProduct, OfferProductListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<OfferProduct>() {
        override fun areItemsTheSame(oldItem: OfferProduct, newItem: OfferProduct): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: OfferProduct,
            newItem: OfferProduct
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): OfferProductListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_offer_product, parent, false
        )
    }

    override fun bind(binding: OfferProductListItemBinding, position: Int) {
        val item = getItem(position)
        binding.item = item

        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.thumbnail.setImageResource(R.drawable.image_placeholder)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }

        binding.root.setOnClickListener {
            itemSelectionCallback(item)
        }

        binding.mrp.apply {
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            text = "${binding.root.context.getString(R.string.sign_taka)}120"
        }
    }
}