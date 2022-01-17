package com.rtchubs.arfixture.ui.home

import androidx.fragment.app.viewModels
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.SetCFragmentBinding
import com.rtchubs.arfixture.ui.common.BaseFragment

class SetCFragment : BaseFragment<SetCFragmentBinding, SetCViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_c

    override val viewModel: SetCViewModel by viewModels { viewModelFactory }
}