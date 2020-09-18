package com.qpay.customer.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.qpay.customer.BR
import com.qpay.customer.R
import com.qpay.customer.databinding.MoreFragmentBinding
import com.qpay.customer.databinding.SetAFragmentBinding
import com.qpay.customer.ui.LogoutHandlerCallback
import com.qpay.customer.ui.common.BaseFragment
import com.qpay.customer.ui.profile_signin.ProfileSignInViewModel

class SetAFragment : BaseFragment<SetAFragmentBinding, SetAViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_a

    override val viewModel: SetAViewModel by viewModels { viewModelFactory }
}