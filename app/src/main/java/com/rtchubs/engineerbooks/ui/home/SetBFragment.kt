package com.rtchubs.engineerbooks.ui.home

import androidx.fragment.app.viewModels
import com.rtchubs.engineerbooks.BR
import com.rtchubs.engineerbooks.R
import com.rtchubs.engineerbooks.databinding.MoreFragmentBinding
import com.rtchubs.engineerbooks.databinding.SetAFragmentBinding
import com.rtchubs.engineerbooks.databinding.SetBFragmentBinding
import com.rtchubs.engineerbooks.ui.common.BaseFragment

class SetBFragment : BaseFragment<SetBFragmentBinding, SetBViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_b

    override val viewModel: SetBViewModel by viewModels { viewModelFactory }
}