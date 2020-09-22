package com.rtchubs.edokanpat.ui.home

import androidx.fragment.app.viewModels
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.MoreFragmentBinding
import com.rtchubs.edokanpat.databinding.SetAFragmentBinding
import com.rtchubs.edokanpat.databinding.SetBFragmentBinding
import com.rtchubs.edokanpat.ui.common.BaseFragment

class SetBFragment : BaseFragment<SetBFragmentBinding, SetBViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_b

    override val viewModel: SetBViewModel by viewModels { viewModelFactory }
}