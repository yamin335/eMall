package com.rtchubs.arfixture.ui.home

import androidx.fragment.app.viewModels
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.SetBFragmentBinding
import com.rtchubs.arfixture.ui.common.BaseFragment

class SetBFragment : BaseFragment<SetBFragmentBinding, SetBViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_b

    override val viewModel: SetBViewModel by viewModels { viewModelFactory }
}