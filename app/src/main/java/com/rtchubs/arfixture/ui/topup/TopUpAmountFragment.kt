package com.rtchubs.arfixture.ui.topup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.databinding.TopUpAmountFragmentBinding
import com.rtchubs.arfixture.ui.common.BaseFragment
import com.rtchubs.arfixture.util.hideKeyboard

class TopUpAmountFragment : BaseFragment<TopUpAmountFragmentBinding, TopUpAmountViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_topup_amount
    override val viewModel: TopUpAmountViewModel by viewModels {
        viewModelFactory
    }

    val args: TopUpAmountFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStatusBarBackgroundColor("#1E4356")
        registerToolbar(viewDataBinding.toolbar)

        val helper = args.topUpHelper

        viewModel.amount.observe(viewLifecycleOwner, Observer {  amount ->
            val validAmount = isValidAmount(amount)
            if ( validAmount != null) {
                helper.amount = validAmount
                viewDataBinding.btnProceed.isEnabled = true
            } else {
                viewDataBinding.btnProceed.isEnabled = false
            }
        })

        viewDataBinding.btnProceed.setOnClickListener {
            hideKeyboard()
            navController.navigate(TopUpAmountFragmentDirections.actionTopUpAmountFragmentToTopUpBankCardFragment(helper))
        }
    }

    private fun isValidAmount(amount: String?)
            = if (!amount.isNullOrBlank() && "^(?=\\d)(?=.*[1-9])(\\d*)\\.?\\d+".toRegex().matches(amount)) { amount } else { null }
}