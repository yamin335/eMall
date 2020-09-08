package com.qpay.customer.ui.login

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
import com.qpay.customer.models.registration.RegistrationHelperModel
import com.qpay.customer.ui.common.BaseFragment
import com.qpay.customer.util.AppConstants.commonErrorMessage
import com.qpay.customer.util.hideKeyboard
import com.qpay.customer.util.showErrorToast

class SignInFragment : BaseFragment<SignInBinding, SignInViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_sign_in
    override val viewModel: SignInViewModel by viewModels {
        viewModelFactory
    }

    val registrationHelper =
        RegistrationHelperModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStatusBarBackgroundColor("#1E4356")
        registerToolbar(viewDataBinding.toolbar)

        viewModel.mobileNo.observe(viewLifecycleOwner, Observer {  mobileNo ->
            mobileNo?.let {
                viewDataBinding.btnProceed.isEnabled = (it.length == 11) && (it[0] == '0')
            }
        })

        viewDataBinding.btnProceed.setOnClickListener {
            hideKeyboard()
            viewModel.mobileNo.value?.let { mobileNo ->
                inquireAccount(mobileNo, Build.ID)
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
            goForRegistration(bottomSheetDialog, "Banglalink")
        }

        binding.btnGrameenphone.setOnClickListener {
            goForRegistration(bottomSheetDialog, "Grameenphone")
        }

        binding.btnRobi.setOnClickListener {
            goForRegistration(bottomSheetDialog, "Robi")
        }

        binding.btnTeletalk.setOnClickListener {
            goForRegistration(bottomSheetDialog, "Teletalk")
        }
        bottomSheetDialog.show()
    }

    private fun goForRegistration(dialog: BottomSheetDialog, operator: String) {
        dialog.dismiss()
        registrationHelper.isRegistered = false
        registrationHelper.operator = operator
        val action = SignInFragmentDirections.actionSignInFragmentToTermsFragment(registrationHelper)
        navController.navigate(action)
    }

    private fun inquireAccount(mobile: String, deviceId: String) {
        viewModel.inquireAccount(mobile, deviceId).observe(viewLifecycleOwner, Observer {response ->
            response?.body?.let {
                if (it.isRegistered == false) {
                    registrationHelper.mobile = mobile
                    openOperatorSelectionDialog()
                } else if (it.isRegistered == true && it.isAllowed == true) {
                    registrationHelper.isRegistered = true
                    registrationHelper.isTermsAccepted = true
                    registrationHelper.mobile = mobile
                    val action = SignInFragmentDirections.actionSignInFragmentToOtpSignInFragment(registrationHelper)
                    navController.navigate(action)
                } else {
                    showErrorToast(mContext, response.body.message ?: commonErrorMessage)
                }
            }
        })
    }
}