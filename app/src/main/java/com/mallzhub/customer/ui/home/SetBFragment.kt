package com.mallzhub.customer.ui.home

import androidx.fragment.app.viewModels
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.SetBFragmentBinding
import com.mallzhub.customer.ui.common.BaseFragment

class SetBFragment : BaseFragment<SetBFragmentBinding, SetBViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_b

    override val viewModel: SetBViewModel by viewModels { viewModelFactory }
}