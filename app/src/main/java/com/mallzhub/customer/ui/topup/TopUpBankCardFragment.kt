package com.mallzhub.customer.ui.topup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mallzhub.customer.R
import com.mallzhub.customer.BR
import com.mallzhub.customer.databinding.TopUpBankCardFragmentBinding
import com.mallzhub.customer.ui.common.BaseFragment

class TopUpBankCardFragment : BaseFragment<TopUpBankCardFragmentBinding, TopUpBankCardViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_topup_bank_card
    override val viewModel: TopUpBankCardViewModel by viewModels {
        viewModelFactory
    }

    val args: TopUpAmountFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStatusBarBackgroundColor("#1E4356")
        registerToolbar(viewDataBinding.toolbar)

        val helper = args.topUpHelper
    }
}