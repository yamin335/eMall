package com.rtchubs.arfixture.ui.tou

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.databinding.TouBinding
import com.rtchubs.arfixture.ui.common.BaseFragment

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