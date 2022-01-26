package com.rtchubs.arfixture.ui.product_ar_view

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.ProductARViewListItemBinding
import com.rtchubs.arfixture.models.Product

private const val PAYLOAD_SELECTED_INDICATOR = "PAYLOAD_SELECTED_INDICATOR"
class ProductARViewListAdapter constructor(
    private val itemCallback: (Product) -> Unit
): RecyclerView.Adapter<ProductARViewListAdapter.ViewHolder>() {
    private var productList: ArrayList<Product> = ArrayList()
    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ProductARViewListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_product_ar_view, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.itemView.isSelected = selectedPosition == position

        if (payloads.isNotEmpty()) {
            val payload = payloads[0]
            if (payload is String && payload == PAYLOAD_SELECTED_INDICATOR) {
                holder.updateSelectedIndicator()
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateViewSelectedState(position: Int = RecyclerView.NO_POSITION) {
        val prevSelectedPosition = selectedPosition
        selectedPosition = position

        notifyItemChanged(prevSelectedPosition, PAYLOAD_SELECTED_INDICATOR)
        notifyItemChanged(selectedPosition, PAYLOAD_SELECTED_INDICATOR)
    }

    fun submitList(productList: ArrayList<Product>) {
        this.productList.clear()
        this.productList.addAll(productList)
        notifyDataSetChanged()
    }

    inner class ViewHolder (private val binding: ProductARViewListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.productName = item.name
            binding.imageUrl = item.thumbnail

            binding.root.setOnClickListener {
                updateViewSelectedState(adapterPosition)
                itemCallback.invoke(item)
            }

            binding.imageRequestListener = object: RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    binding.imageView.setImageResource(R.drawable.ic_ar)
                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false
                }
            }
            updateSelectedIndicator()
            binding.executePendingBindings()
        }

        fun updateSelectedIndicator() {
            binding.root.isSelected = itemView.isSelected
        }
    }
}
