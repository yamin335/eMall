package com.mallzhub.customer.ui.more

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.MoreFragmentBinding
import com.mallzhub.customer.ui.LogoutHandlerCallback
import com.mallzhub.customer.ui.NavDrawerHandlerCallback
import com.mallzhub.customer.ui.common.BaseFragment
import com.mallzhub.customer.ui.splash.SplashFragment

class MoreFragment : BaseFragment<MoreFragmentBinding, MoreViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_more

    override val viewModel: MoreViewModel by viewModels { viewModelFactory }

    private var listener: LogoutHandlerCallback? = null

    private var drawerListener: NavDrawerHandlerCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LogoutHandlerCallback) {
            listener = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }

        if (context is NavDrawerHandlerCallback) {
            drawerListener = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        drawerListener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.logout.setOnClickListener {
            SplashFragment.fromLogout = true
            preferencesHelper.isLoggedIn = false
            listener?.onLoggedOut()
        }

        viewDataBinding.appLogo.setOnClickListener {
            drawerListener?.toggleNavDrawer()
        }

        viewDataBinding.addProduct.setOnClickListener {
            navigateTo(MoreFragmentDirections.actionMoreFragmentToAddProductFragment())
        }
    }

}