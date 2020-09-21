package com.rtchubs.engineerbooks.ui.on_boarding.tou

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.rtchubs.engineerbooks.R
import com.rtchubs.engineerbooks.BR
import com.rtchubs.engineerbooks.databinding.TouBinding
import com.rtchubs.engineerbooks.ui.common.BaseFragment

class TouFragment  : BaseFragment<TouBinding, TouViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_tou
    override val viewModel: TouViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.btnAgree.setOnClickListener {
        }
    }
}