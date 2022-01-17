package com.rtchubs.arfixture.ui.terms_and_conditions

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.TermsBinding
import com.rtchubs.arfixture.ui.common.BaseFragment
import com.rtchubs.arfixture.util.AppConstants.TERMS_AND_CONDITIONS_URL


class TermsAndConditionsFragment : BaseFragment<TermsBinding, TermsViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_terms_and_condition
    override val viewModel: TermsViewModel by viewModels {
        viewModelFactory
    }

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

        viewDataBinding.btnAccept.setOnClickListener {
//            val helper = args.registrationHelper
//            helper.isTermsAccepted = true
//            navController.navigate(TermsAndConditionsFragmentDirections.actionTermsAndConditionsToOtpSignInFragment3(helper))
        }

        viewDataBinding.webView.settings.javaScriptEnabled = true
        viewDataBinding.webView.settings.loadWithOverviewMode = true

        viewDataBinding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
        viewDataBinding.webView.loadUrl(TERMS_AND_CONDITIONS_URL)
    }
}