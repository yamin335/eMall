package com.qpay.customer.ui.add_payment_methods

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qpay.customer.BR
import com.qpay.customer.R
import com.qpay.customer.databinding.AddBankFragmentBinding
import com.qpay.customer.databinding.LayoutOperatorSelectionBinding
import com.qpay.customer.databinding.SignInBinding
import com.qpay.customer.models.registration.RegistrationHelperModel
import com.qpay.customer.ui.common.BaseFragment
import com.qpay.customer.ui.login.SignInFragmentDirections
import com.qpay.customer.ui.login.SignInViewModel
import com.qpay.customer.ui.profile_signin.ProfileSignInFragmentDirections
import com.qpay.customer.util.AppConstants
import com.qpay.customer.util.showErrorToast
import com.qpay.customer.util.showSuccessToast
import com.qpay.customer.util.showWarningToast

class AddBankFragment : BaseFragment<AddBankFragmentBinding, AddBankViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_add_bank
    override val viewModel: AddBankViewModel by viewModels {
        viewModelFactory
    }

    val args: AddBankFragmentArgs by navArgs()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mActivity.window?.setSoftInputMode(
//            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
//        )
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        requireActivity().window?.setSoftInputMode(
//            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
//        )
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStatusBarBackgroundColor("#1E4356")
        registerToolbar(viewDataBinding.toolbar)

        viewModel.bankId = args.selectedBank.id ?: 0

        viewModel.bankAccount.observe(viewLifecycleOwner, Observer { accountNummber ->
            accountNummber?.let { validAccountNumber ->
                viewDataBinding.btnAdd.isEnabled = validAccountNumber.length >= 10
            }
        })

        viewModel.addBankAccountResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let { validResponse ->
                when {
                    validResponse.isSuccess == true && validResponse.body?.isSuccess == true-> {
                        showSuccessToast(mContext, validResponse.body.message ?: AppConstants.saveSuccessfulMessage)
                        navController.navigate(AddBankFragmentDirections.actionAddBankFragmentToHome2Fragment())
                    }
                    validResponse.isSuccess == false && validResponse.errorMessage != null -> {
                        showWarningToast(mContext, validResponse.errorMessage)
                    }
                    else -> {
                        showWarningToast(mContext, AppConstants.commonErrorMessage)
                    }
                }
            }
        })
    }
}