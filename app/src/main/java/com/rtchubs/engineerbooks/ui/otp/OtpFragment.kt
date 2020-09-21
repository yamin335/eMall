package com.rtchubs.engineerbooks.ui.otp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.rtchubs.engineerbooks.R
import com.rtchubs.engineerbooks.BR
import com.rtchubs.engineerbooks.databinding.OtpBinding
import com.rtchubs.engineerbooks.ui.common.BaseFragment

class OtpFragment  : BaseFragment<OtpBinding, OtpViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_otp
    override val viewModel: OtpViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.btnVerify.setOnClickListener {
        }
    }
}