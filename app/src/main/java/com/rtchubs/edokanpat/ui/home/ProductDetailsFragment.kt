package com.rtchubs.edokanpat.ui.home

import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.ProductDetailsFragmentBinding
import com.rtchubs.edokanpat.local_db.dao.CartDao
import com.rtchubs.edokanpat.local_db.db.AppDatabase
import com.rtchubs.edokanpat.local_db.dbo.CartItem
import com.rtchubs.edokanpat.ui.common.BaseFragment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsFragment :
    BaseFragment<ProductDetailsFragmentBinding, ProductDetailsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_product_details
    override val viewModel: ProductDetailsViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var cart: CartDao

    val args: ProductDetailsFragmentArgs by navArgs()

    lateinit var pdImageSampleAdapter: PDImageSampleAdapter
    lateinit var pdColorChooserAdapter: PDColorChooserAdapter
    lateinit var pdSizeChooserAdapter: PDSizeChooserAdapter
    var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        val product = args.product

        viewDataBinding.toolbar.title = product.name
        viewDataBinding.name = product.name
        viewDataBinding.price = "$${product.mrp}"

        pdImageSampleAdapter = PDImageSampleAdapter(
            appExecutors
        ) { item ->
            viewDataBinding.imageUrl = item
        }

        viewDataBinding.rvSampleImage.adapter = pdImageSampleAdapter

        pdImageSampleAdapter.submitList(listOf(product.product_image1, product.product_image2, product.product_image3, product.product_image4, product.product_image5))

        viewDataBinding.imageUrl = product.thumbnail
        viewDataBinding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                viewDataBinding.imageView.setImageResource(R.drawable.product_image)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }

        pdColorChooserAdapter = PDColorChooserAdapter(
            appExecutors
        ) { item ->
            //viewDataBinding.imageUrl = item
        }

        viewDataBinding.rvColorChooser.adapter = pdColorChooserAdapter

        pdColorChooserAdapter.submitList(listOf("#d32f2f", "#0AB939", "#2A5D79", "#C7A90D", "#FD87A9", "#E91E63", "#D500F9"))

        pdSizeChooserAdapter = PDSizeChooserAdapter(
            appExecutors
        ) { item ->
            //viewDataBinding.imageUrl = item
        }

        viewDataBinding.rvSizeChooser.adapter = pdSizeChooserAdapter

        pdSizeChooserAdapter.submitList(listOf("XS", "S", "M", "L", "XL", "XXL", "3XL"))

        viewDataBinding.quantity.text = quantity.toString()

        viewDataBinding.decrementQuantity.setOnClickListener {
            if (quantity > 1) --quantity
            viewDataBinding.quantity.text = quantity.toString()
        }

        viewDataBinding.incrementQuantity.setOnClickListener {
            ++quantity
            viewDataBinding.quantity.text = quantity.toString()
        }

        viewDataBinding.addToCart.setOnClickListener {
            try {
                val handler = CoroutineExceptionHandler { _, exception ->
                    println("Caught during database creation --> $exception")
                }

                lifecycleScope.launch(handler) {
                    cart.addItemToCart(CartItem(product.id, product.name, product.barcode, product.mrp, product.category_id, product.merchant_id))
                }
            } catch (e: SQLiteException) {
                e.printStackTrace()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_product_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }

            R.id.menu_cart -> {
                navController.navigate(ProductDetailsFragmentDirections.actionProductDetailsFragmentToCartFragment())
            }
        }

        return true
    }
}