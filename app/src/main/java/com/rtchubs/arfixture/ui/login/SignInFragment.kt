package com.rtchubs.arfixture.ui.login

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.databinding.LayoutOperatorSelectionBinding
import com.rtchubs.arfixture.databinding.SignInBinding
import com.rtchubs.arfixture.models.registration.RegistrationHelperModel
import com.rtchubs.arfixture.ui.common.BaseFragment
import com.rtchubs.arfixture.util.AppConstants.commonErrorMessage
import com.rtchubs.arfixture.util.hideKeyboard
import com.rtchubs.arfixture.util.showErrorToast

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
        // This callback will only be called when HomeFragment is at least Started.
        requireActivity().onBackPressedDispatcher.addCallback(this, true) {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStatusBarBackgroundColor("#161E2C")
        //registerToolbar(viewDataBinding.toolbar)

        viewDataBinding.backButton.setOnClickListener {
            requireActivity().finish()
            requireActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        viewModel.mobileNo.observe(viewLifecycleOwner, Observer {  mobileNo ->
            mobileNo?.let {
                viewDataBinding.btnNext.isEnabled = (it.length == 11) && (it[0] == '0')
            }
        })

        viewDataBinding.btnNext.setOnClickListener {
            hideKeyboard()
            tempOpenOperatorSelectionDialog()
//            viewModel.mobileNo.value?.let { mobileNo ->
//                inquireAccount(mobileNo, Build.ID)
//            }
        }
    }

    private fun tempOpenOperatorSelectionDialog() {
        val bottomSheetDialog = BottomSheetDialog(mActivity)
        val binding = DataBindingUtil.inflate<LayoutOperatorSelectionBinding>(
            layoutInflater,
            R.layout.layout_operator_selection,
            null,
            false
        )
        bottomSheetDialog.setContentView(binding.root)


        binding.btnBanglalink.setOnClickListener {
            bottomSheetDialog.dismiss()
            val action = SignInFragmentDirections.actionSignInFragmentToOtpSignInFragment(registrationHelper)
            navController.navigate(action)
        }

        binding.btnGrameenphone.setOnClickListener {
            bottomSheetDialog.dismiss()
            val action = SignInFragmentDirections.actionSignInFragmentToOtpSignInFragment(registrationHelper)
            navController.navigate(action)
        }

        binding.btnRobi.setOnClickListener {
            bottomSheetDialog.dismiss()
            val action = SignInFragmentDirections.actionSignInFragmentToOtpSignInFragment(registrationHelper)
            navController.navigate(action)
        }

        binding.btnTeletalk.setOnClickListener {
            bottomSheetDialog.dismiss()
            val action = SignInFragmentDirections.actionSignInFragmentToOtpSignInFragment(registrationHelper)
            navController.navigate(action)
        }
        bottomSheetDialog.show()
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
        val action = SignInFragmentDirections.actionSignInFragmentToOtpSignInFragment(registrationHelper)
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