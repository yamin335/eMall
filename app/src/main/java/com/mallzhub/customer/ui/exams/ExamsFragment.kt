package com.mallzhub.customer.ui.exams

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.customer.R
import com.mallzhub.customer.BR
import com.mallzhub.customer.databinding.ExamsFragmentBinding
import com.mallzhub.customer.ui.common.BaseFragment

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