package com.rtchubs.engineerbooks.ui.topup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.rtchubs.engineerbooks.R
import com.rtchubs.engineerbooks.BR
import com.rtchubs.engineerbooks.databinding.TopUpBankCardFragmentBinding
import com.rtchubs.engineerbooks.ui.common.BaseFragment

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