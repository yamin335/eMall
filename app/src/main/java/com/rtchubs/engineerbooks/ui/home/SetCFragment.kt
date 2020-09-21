package com.rtchubs.engineerbooks.ui.home

import androidx.fragment.app.viewModels
import com.rtchubs.engineerbooks.BR
import com.rtchubs.engineerbooks.R
import com.rtchubs.engineerbooks.databinding.MoreFragmentBinding
import com.rtchubs.engineerbooks.databinding.SetAFragmentBinding
import com.rtchubs.engineerbooks.databinding.SetCFragmentBinding
import com.rtchubs.engineerbooks.ui.common.BaseFragment

class SetCFragment : BaseFragment<SetCFragmentBinding, SetCViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_c

    override val viewModel: SetCViewModel by viewModels { viewModelFactory }
}