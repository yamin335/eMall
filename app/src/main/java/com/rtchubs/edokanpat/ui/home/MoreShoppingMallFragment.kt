package com.rtchubs.edokanpat.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.MoreShoppingListFragmentBinding
import com.rtchubs.edokanpat.ui.common.BaseFragment
import com.rtchubs.edokanpat.util.GridRecyclerItemDecorator

class MoreShoppingMallFragment :
    BaseFragment<MoreShoppingListFragmentBinding, MoreShoppingMallViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_more_shopping_mall
    override val viewModel: MoreShoppingMallViewModel by viewModels {
        viewModelFactory
    }

    lateinit var shoppingMallListAdapter: MoreShoppingMallListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        shoppingMallListAdapter = MoreShoppingMallListAdapter(
                appExecutors
            ) { item ->
            navController.navigate(MoreShoppingMallFragmentDirections.actionMoreShoppingMallFragmentToAllShopListFragment(item))
        }

        viewDataBinding.rvMoreShoppingMallList.addItemDecoration(GridRecyclerItemDecorator(2, 40, true))
        viewDataBinding.rvMoreShoppingMallList.layoutManager = GridLayoutManager(mContext, 2)
        viewDataBinding.rvMoreShoppingMallList.adapter = shoppingMallListAdapter

        viewModel.allShoppingMallResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.data?.let { mallList ->
                shoppingMallListAdapter.submitList(mallList)
            }
        })

        viewModel.getAllShoppingMallList()
    }
}