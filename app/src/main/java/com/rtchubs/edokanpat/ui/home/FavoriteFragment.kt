package com.rtchubs.edokanpat.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.FavoriteFragmentBinding
import com.rtchubs.edokanpat.databinding.ProductListFragmentBinding
import com.rtchubs.edokanpat.ui.LogoutHandlerCallback
import com.rtchubs.edokanpat.ui.NavDrawerHandlerCallback
import com.rtchubs.edokanpat.ui.common.BaseFragment

class FavoriteFragment : BaseFragment<FavoriteFragmentBinding, FavoriteViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_favorite
    override val viewModel: FavoriteViewModel by viewModels {
        viewModelFactory
    }

    lateinit var favoriteListAdapter: FavoriteListAdapter

    private var drawerListener: NavDrawerHandlerCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavDrawerHandlerCallback) {
            drawerListener = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        drawerListener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.appLogo.setOnClickListener {
            drawerListener?.toggleNavDrawer()
        }

        favoriteListAdapter = FavoriteListAdapter(
            appExecutors
        ) { item ->
            viewModel.deleteFavoriteItem(item)
        }

        viewDataBinding.rvFavoriteList.adapter = favoriteListAdapter

        viewModel.favoriteItems.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                favoriteListAdapter.submitList(list)
            }
        })
    }

}