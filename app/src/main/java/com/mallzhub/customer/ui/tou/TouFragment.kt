package com.mallzhub.customer.ui.tou

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.customer.R
import com.mallzhub.customer.BR
import com.mallzhub.customer.databinding.TouBinding
import com.mallzhub.customer.ui.common.BaseFragment

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