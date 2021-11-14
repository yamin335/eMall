package com.mallzhub.customer.ui.profiles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.ProfileFragmentBinding
import com.mallzhub.customer.ui.common.BaseFragment

class ProfilesFragment : BaseFragment<ProfileFragmentBinding, ProfilesViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_profile
    override val viewModel: ProfilesViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

    }
}