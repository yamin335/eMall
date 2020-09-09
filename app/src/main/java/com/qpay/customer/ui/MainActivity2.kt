package com.qpay.customer.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.security.ProviderInstaller
import com.qpay.customer.R
import com.qpay.customer.databinding.MainActivity2Binding
import com.qpay.customer.prefs.PreferencesHelper
import com.qpay.customer.ui.pin_number.PinNumberFragmentDirections
import com.qpay.customer.ui.splash.SplashFragmentDirections
import com.qpay.customer.util.CommonUtils
import com.qpay.customer.util.hideKeyboard
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity2 : DaggerAppCompatActivity(), NavigationHost {

    lateinit var binding: MainActivity2Binding
    private val navController: NavController
        get() = findNavController(R.id.container)
    private var currentNavController: LiveData<NavController>? = null

    private var currentDestinationId: Int = 0

    @Inject
    lateinit var preference: PreferencesHelper

    /*fragment ids which doesn't need bottom navigation or drawer*/
    private val fragmentArrayWithoutBottomNav = intArrayOf(
        R.id.splashFragment,
        R.id.viewPagerFragment,
        R.id.signInFragment,
        R.id.termsAndConditions,
        R.id.otpSignInFragment,
        R.id.pinNumberFragment,
        R.id.profileSignInFragment,
        R.id.preOnBoardFragment,
        R.id.howWorksFragment,
        R.id.registrationFragment,
        R.id.otpFragment,
        R.id.touFragment,
        R.id.setupFragment,
        R.id.setupCompleteFragment,
        R.id.chapterListFragment,
        R.id.videoPlayFragment,
        R.id.cameraFragment,
        R.id.NIDScanCameraXFragment
    )

    private val fragmentArrayWithoutSideNav = intArrayOf(
        R.id.splashFragment,
        R.id.viewPagerFragment,
        R.id.signInFragment,
        R.id.termsAndConditions,
        R.id.otpSignInFragment,
        R.id.pinNumberFragment,
        R.id.profileSignInFragment,
        R.id.preOnBoardFragment,
        R.id.howWorksFragment,
        R.id.registrationFragment,
        R.id.otpFragment,
        R.id.touFragment,
        R.id.setupFragment,
        R.id.setupCompleteFragment,
        R.id.chapterListFragment,
        R.id.videoPlayFragment,
        R.id.cameraFragment,
        R.id.NIDScanCameraXFragment,
        R.id.addPaymentMethodsFragment,
        R.id.addBankFragment,
        R.id.addCardFragment,
        R.id.topUpMobileFragment,
        R.id.topUpAmountFragment,
        R.id.topUpPinFragment,
        R.id.topUpBankCardFragment
    )

    private val topLevelDestinations = setOf(
        R.id.home2Fragment,
        R.id.payFragment,
        R.id.transactionFragment,
        R.id.moreFragment
    )

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(topLevelDestinations)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)
        binding = DataBindingUtil.setContentView(this@MainActivity2, R.layout.activity_main2)
        binding.lifecycleOwner = this
        window.statusBarColor = Color.parseColor("#1E4356")

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            hideKeyboard()
            currentDestinationId = destination.id
            val hasBottomNav = destination.id !in fragmentArrayWithoutBottomNav
            binding.contentMain.showBottomNav = hasBottomNav
            if (hasBottomNav) {
                setupBottomNav(destination.id)
                /*set title explicitly to avoid title error on locale changes*/
                val label = destination.label
                if (!label.isNullOrBlank()) title = label
            }
            if (destination.id !in fragmentArrayWithoutBottomNav) {

            }
        }

//        if (!preference.isAccessTokenExpired && preference.isLoggedIn && preference.getAccessTokenHeader() != "") {
//            val action = SplashFragmentDirections.actionSplashToMain()
//            navController.navigate(action)
//        } else {
//            val action = SplashFragmentDirections.actionSplashToAuth()
//            navController.navigate(action)
//        }

//        binding.appBarMain.bottomNavigationView.setOnNavigationItemReselectedListener { item ->
//            if (item.itemId == R.id.pay_nav_graph) {
//                val tt = "Working"
//            }
//        }


    }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController)
        if (currentDestinationId !in fragmentArrayWithoutSideNav) {
            setSupportActionBar(toolbar)
            toolbar.setupWithNavController(navController, appBarConfiguration)
            toolbar.setNavigationOnClickListener {
                if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }
            }
        }
    }

    private fun setupBottomNav(id: Int) {

        binding.contentMain.bottomNavigationView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(binding.contentMain.bottomNavigationView, navController)

        // Setting Up ActionBar with Navigation Controller
        //Prevent Back arrow in action bar of top level destination we can use appbar configuration for back arrow issue
        //setupActionBarWithNavController method use for change the action bar title when bottom nav item changed
        // setupActionBarWithNavController(navController,appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() ?: false
    }
}