package com.mallzhub.customer.ui.how_works

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.customer.R
import com.mallzhub.customer.BR
import com.mallzhub.customer.databinding.HowWorksBinding
import com.mallzhub.customer.ui.common.BaseFragment


class HowWorksFragment : BaseFragment<HowWorksBinding, HowWorksViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_how_works
    override val viewModel: HowWorksViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.btnOnboardingStart.setOnClickListener {
        }
    }
}