package com.rtchubs.arfixture.ui.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.databinding.InfoFragmentBinding
import com.rtchubs.arfixture.ui.common.BaseFragment

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