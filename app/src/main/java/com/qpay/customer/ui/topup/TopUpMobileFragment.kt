package com.qpay.customer.ui.topup

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qpay.customer.R
import com.qpay.customer.BR
import com.qpay.customer.databinding.LayoutOperatorSelectionBinding
import com.qpay.customer.databinding.SignInBinding
import com.qpay.customer.databinding.TopUpMobileFragmentBinding
import com.qpay.customer.models.registration.RegistrationHelperModel
import com.qpay.customer.models.topup.TopUpHelper
import com.qpay.customer.ui.common.BaseFragment
import com.qpay.customer.util.AppConstants.commonErrorMessage
import com.qpay.customer.util.hideKeyboard
import com.qpay.customer.util.showErrorToast
import com.qpay.customer.util.showWarningToast

class TopUpMobileFragment : BaseFragment<TopUpMobileFragmentBinding, TopUpMobileViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_topup_mobile
    override val viewModel: TopUpMobileViewModel by viewModels {
        viewModelFactory
    }

    val topUpHelper = TopUpHelper()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStatusBarBackgroundColor("#1E4356")
        registerToolbar(viewDataBinding.toolbar)

        viewDataBinding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            // Respond to button selection
            if (isChecked) {
                when (checkedId) {
                    R.id.prepaid -> topUpHelper.simType = "Prepaid"
                    R.id.postPaid -> topUpHelper.simType = "PostPaid"
                }
            }
        }

        viewModel.mobileNo.observe(viewLifecycleOwner, Observer {  mobileNo ->
            mobileNo?.let {
                topUpHelper.mobile = mobileNo
                viewDataBinding.btnProceed.isEnabled = (it.length == 11) && (it[0] == '0')
            }
        })

        viewDataBinding.btnProceed.setOnClickListener {
            hideKeyboard()
            if (topUpHelper.simType.isNotBlank()) {
                openOperatorSelectionDialog()
            } else {
                showWarningToast(mContext, "Please select SIM type!")
            }

        }
    }

    private fun openOperatorSelectionDialog() {
        val bottomSheetDialog = BottomSheetDialog(mActivity)
        val binding = DataBindingUtil.inflate<LayoutOperatorSelectionBinding>(
            layoutInflater,
            R.layout.layout_operator_selection,
            null,
            false
        )
        bottomSheetDialog.setContentView(binding.root)


        binding.btnBanglalink.setOnClickListener {
            goForAmount(bottomSheetDialog, "Banglalink")
        }

        binding.btnGrameenphone.setOnClickListener {
            goForAmount(bottomSheetDialog, "Grameenphone")
        }

        binding.btnRobi.setOnClickListener {
            goForAmount(bottomSheetDialog, "Robi")
        }

        binding.btnTeletalk.setOnClickListener {
            goForAmount(bottomSheetDialog, "Teletalk")
        }
        bottomSheetDialog.show()
    }

    private fun goForAmount(dialog: BottomSheetDialog, operator: String) {
        dialog.dismiss()
        topUpHelper.operator = operator
        navController.navigate(TopUpMobileFragmentDirections.actionTopUpMobileFragmentToTopUpAmountFragment(topUpHelper))

    }
}