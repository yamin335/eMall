package com.rtchubs.engineerbooks.ui.exams

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.rtchubs.engineerbooks.R
import com.rtchubs.engineerbooks.BR
import com.rtchubs.engineerbooks.databinding.ExamsFragmentBinding
import com.rtchubs.engineerbooks.databinding.SettingsBinding
import com.rtchubs.engineerbooks.ui.common.BaseFragment

class ExamsFragment : BaseFragment<ExamsFragmentBinding, ExamsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_exams
    override val viewModel: ExamsViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}