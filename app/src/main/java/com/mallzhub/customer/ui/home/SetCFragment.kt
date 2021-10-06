package com.mallzhub.customer.ui.home

import androidx.fragment.app.viewModels
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.SetCFragmentBinding
import com.mallzhub.customer.ui.common.BaseFragment

class SetCFragment : BaseFragment<SetCFragmentBinding, SetCViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_c

    override val viewModel: SetCViewModel by viewModels { viewModelFactory }
}