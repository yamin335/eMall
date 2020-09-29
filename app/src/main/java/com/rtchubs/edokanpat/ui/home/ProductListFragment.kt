package com.rtchubs.edokanpat.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.MoreShoppingListFragmentBinding
import com.rtchubs.edokanpat.databinding.ProductListFragmentBinding
import com.rtchubs.edokanpat.ui.common.BaseFragment
import com.rtchubs.edokanpat.util.GridRecyclerItemDecorator

class ProductListFragment :
    BaseFragment<ProductListFragmentBinding, ProductListViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_product_list
    override val viewModel: ProductListViewModel by viewModels {
        viewModelFactory
    }

    lateinit var productListAdapter: ProductListAdapter
    val args: ProductListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        viewDataBinding.toolbar.title = args.merchant.name

        productListAdapter = ProductListAdapter(
                appExecutors
            ) { item ->
            navController.navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(item))
        }

        viewDataBinding.rvProductList.addItemDecoration(GridRecyclerItemDecorator(2, 20, true))
        viewDataBinding.rvProductList.layoutManager = GridLayoutManager(mContext, 2)
        viewDataBinding.rvProductList.adapter = productListAdapter

        viewModel.productListResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.data?.let { productList ->
                productListAdapter.submitList(productList)
            }
        })

        viewModel.getProductList(args.merchant.id.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_product_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }
        }

        return true
    }
}