package com.rtchubs.arfixture.ui.setup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.databinding.SetupBinding
import com.rtchubs.arfixture.ui.common.BaseFragment

class SetupFragment  : BaseFragment<SetupBinding, SetupViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_setup
    override val viewModel: SetupViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.btnProceed.setOnClickListener {
        }

    }
}