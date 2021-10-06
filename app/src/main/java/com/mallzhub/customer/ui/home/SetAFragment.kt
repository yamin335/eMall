package com.mallzhub.customer.ui.home

import androidx.fragment.app.viewModels
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.SetAFragmentBinding
import com.mallzhub.customer.ui.common.BaseFragment

class SetAFragment : BaseFragment<SetAFragmentBinding, SetAViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_a

    override val viewModel: SetAViewModel by viewModels { viewModelFactory }
}