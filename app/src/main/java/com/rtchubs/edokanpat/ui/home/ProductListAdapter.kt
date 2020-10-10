package com.rtchubs.edokanpat.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rtchubs.edokanpat.AppExecutors
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.MoreShoppingListItemBinding
import com.rtchubs.edokanpat.databinding.ProductListItemBinding
import com.rtchubs.edokanpat.databinding.ShopListItemBinding
import com.rtchubs.edokanpat.models.Merchant

import com.rtchubs.edokanpat.models.PaymentMethod
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.util.DataBoundListAdapter
import kotlinx.android.synthetic.main.popup_menu_product_item.view.*

class ProductListAdapter(
    private val appExecutors: AppExecutors,
    private val itemActionCallback: ProductListActionCallback,
    private val itemCallback: ((Product) -> Unit)? = null
) : DataBoundListAdapter<Product, ProductListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    // Properties
    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    val onClicked = MutableLiveData<Product>()
    override fun createBinding(parent: ViewGroup): ProductListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_product, parent, false
        )
    }


    override fun bind(binding: ProductListItemBinding, position: Int) {
        val context = binding.root.context
        val item = getItem(position)
        binding.productName = item.name
        binding.imageUrl = item.thumbnail
        binding.productPrice = "$ ${item.mrp}"

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }

        binding.menu.setOnClickListener {
            Toast.makeText(binding.root.context, "PopUp Menu Working", Toast.LENGTH_LONG).show()
        }

        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.logo.setImageResource(R.drawable.image_placeholder)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewPopupMenu = inflater.inflate(R.layout.popup_menu_product_item, null)
        val popupMenu = PopupWindow(viewPopupMenu, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupMenu.isOutsideTouchable = true
        //popupMenu.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        popupMenu.animationStyle = android.R.style.Animation_Dialog
        popupMenu.elevation = 20F

        popupMenu.setOnDismissListener {
            //        Toast.makeText(context, "Dismissed!!!", Toast.LENGTH_LONG).show()
        }

        val popupMenuView = popupMenu.contentView
        popupMenuView.menuFavorite.setOnClickListener {
            itemActionCallback.addToFavorite(item)
            popupMenu.dismiss()
        }

        popupMenuView.menuCart.setOnClickListener {
            itemActionCallback.addToCart(item)
            popupMenu.dismiss()
        }

        binding.menu.setOnClickListener {
            popupMenu.showAsDropDown(binding.menu,-200, -150, Gravity.NO_GRAVITY)
        }
    }

    interface ProductListActionCallback {
        fun addToFavorite(item: Product)
        fun addToCart(item: Product)
    }
}