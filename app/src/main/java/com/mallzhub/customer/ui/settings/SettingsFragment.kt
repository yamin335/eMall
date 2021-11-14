package com.mallzhub.customer.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.customer.R
import com.mallzhub.customer.BR
import com.mallzhub.customer.databinding.SettingsFragmentBinding
import com.mallzhub.customer.ui.common.BaseFragment

class SettingsFragment : BaseFragment<SettingsFragmentBinding, SettingsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)
    }
}