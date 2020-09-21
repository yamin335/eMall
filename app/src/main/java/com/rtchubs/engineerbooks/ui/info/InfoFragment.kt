package com.rtchubs.engineerbooks.ui.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.rtchubs.engineerbooks.R
import com.rtchubs.engineerbooks.BR
import com.rtchubs.engineerbooks.databinding.InfoFragmentBinding
import com.rtchubs.engineerbooks.databinding.SettingsBinding
import com.rtchubs.engineerbooks.ui.common.BaseFragment

class InfoFragment : BaseFragment<InfoFragmentBinding, InfoViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_info
    override val viewModel: InfoViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}