package com.rtchubs.arfixture.ui.home

import androidx.fragment.app.viewModels
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.SetAFragmentBinding
import com.rtchubs.arfixture.ui.common.BaseFragment

class SetAFragment : BaseFragment<SetAFragmentBinding, SetAViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_a

    override val viewModel: SetAViewModel by viewModels { viewModelFactory }
}