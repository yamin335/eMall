package com.mallzhub.customer.di

import com.mallzhub.customer.ar_location.ARLocationFragment
import com.mallzhub.customer.nid_scan.NIDScanCameraXFragment
import com.mallzhub.customer.ui.add_payment_methods.AddBankFragment
import com.mallzhub.customer.ui.add_payment_methods.AddCardFragment
import com.mallzhub.customer.ui.add_payment_methods.AddPaymentMethodsFragment
import com.mallzhub.customer.ui.add_product.AddProductFragment
import com.mallzhub.customer.ui.cart.CartFragment
import com.mallzhub.customer.ui.chapter_list.ChapterListFragment
import com.mallzhub.customer.ui.exams.ExamsFragment
import com.mallzhub.customer.ui.home.*
import com.mallzhub.customer.ui.how_works.HowWorksFragment
import com.mallzhub.customer.ui.info.InfoFragment
import com.mallzhub.customer.ui.live_chat.BotFragment
import com.mallzhub.customer.ui.login.SignInFragment
import com.mallzhub.customer.ui.terms_and_conditions.TermsAndConditionsFragment
import com.mallzhub.customer.ui.tou.TouFragment
import com.mallzhub.customer.ui.pre_on_boarding.PreOnBoardingFragment
import com.mallzhub.customer.ui.profiles.ProfilesFragment
import com.mallzhub.customer.ui.registration.RegistrationFragment
import com.mallzhub.customer.ui.settings.SettingsFragment
import com.mallzhub.customer.ui.setup.SetupFragment
import com.mallzhub.customer.ui.setup_complete.SetupCompleteFragment
import com.mallzhub.customer.ui.splash.SplashFragment
import com.mallzhub.customer.ui.video_play.LoadWebViewFragment
import com.mallzhub.customer.ui.video_play.VideoPlayFragment
import com.mallzhub.customer.ui.more.MoreFragment
import com.mallzhub.customer.ui.offer.OfferFragment
import com.mallzhub.customer.ui.order.OrderAsGuestDialogFragment
import com.mallzhub.customer.ui.order.OrderListFragment
import com.mallzhub.customer.ui.order.OrderTrackHistoryFragment
import com.mallzhub.customer.ui.otp_signin.OtpSignInFragment
import com.mallzhub.customer.ui.pin_number.PinNumberFragment
import com.mallzhub.customer.ui.shops.ShopDetailsContactUsFragment
import com.mallzhub.customer.ui.shops.ShopDetailsFragment
import com.mallzhub.customer.ui.shops.ShopDetailsProductListFragment
import com.mallzhub.customer.ui.topup.TopUpAmountFragment
import com.mallzhub.customer.ui.topup.TopUpBankCardFragment
import com.mallzhub.customer.ui.topup.TopUpMobileFragment
import com.mallzhub.customer.ui.topup.TopUpPinFragment
import com.mallzhub.customer.ui.transactions.TransactionDetailsFragment
import com.mallzhub.customer.ui.transactions.TransactionsFragment
import com.mallzhub.customer.ui.wallet.WalletFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeTermsAndConditionsFragment(): TermsAndConditionsFragment

    @ContributesAndroidInjector
    abstract fun contributeMoreBookListFragment(): MoreBookListFragment

    @ContributesAndroidInjector
    abstract fun contributeMoreShoppingMallFragment(): MoreShoppingMallFragment

    @ContributesAndroidInjector
    abstract fun contributeAllShopListFragment(): AllShopListFragment

    @ContributesAndroidInjector
    abstract fun contributeShopDetailsFragment(): ShopDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeShopDetailsProductListFragment(): ShopDetailsProductListFragment

    @ContributesAndroidInjector
    abstract fun contributeShopDetailsContactUsFragment(): ShopDetailsContactUsFragment

    @ContributesAndroidInjector
    abstract fun contributeProductListFragment(): ProductListFragment

    @ContributesAndroidInjector
    abstract fun contributeProductDetailsFragment(): ProductDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeCartFragment(): CartFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector
    abstract fun contributeSignInFragment(): SignInFragment

    @ContributesAndroidInjector
    abstract fun contributeExamsFragment(): ExamsFragment

    @ContributesAndroidInjector
    abstract fun contributeInfoFragment(): InfoFragment

    @ContributesAndroidInjector
    abstract fun contributeNIDScanCameraXFragment(): NIDScanCameraXFragment

    @ContributesAndroidInjector
    abstract fun contributePreOnBoardingFragment(): PreOnBoardingFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMoreFragment(): MoreFragment

    @ContributesAndroidInjector
    abstract fun contributeSetAFragment(): SetAFragment

    @ContributesAndroidInjector
    abstract fun contributeSetBFragment(): SetBFragment

    @ContributesAndroidInjector
    abstract fun contributeSetCFragment(): SetCFragment

    @ContributesAndroidInjector
    abstract fun contributeHome2Fragment(): Home2Fragment

    @ContributesAndroidInjector
    abstract fun contributeAddPaymentMethodsFragment(): AddPaymentMethodsFragment


    @ContributesAndroidInjector
    abstract fun contributeHowWorksFragment(): HowWorksFragment

    @ContributesAndroidInjector
    abstract fun contributeRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    abstract fun contributeTouFragment(): TouFragment

    @ContributesAndroidInjector
    abstract fun contributeSetupFragment(): SetupFragment

    @ContributesAndroidInjector
    abstract fun contributeSetupCompleteFragment(): SetupCompleteFragment

    @ContributesAndroidInjector
    abstract fun contributeProfilesFragment(): ProfilesFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeChapterListFragment(): ChapterListFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoPlayFragment(): VideoPlayFragment

    @ContributesAndroidInjector
    abstract fun contributeLoadWebViewFragment(): LoadWebViewFragment

    @ContributesAndroidInjector
    abstract fun contributeOtpSignInFragment(): OtpSignInFragment

    @ContributesAndroidInjector
    abstract fun contributePinNumberFragment(): PinNumberFragment

    @ContributesAndroidInjector
    abstract fun contributeAddBankFragment(): AddBankFragment

    @ContributesAndroidInjector
    abstract fun contributeAddCardFragment(): AddCardFragment

    @ContributesAndroidInjector
    abstract fun contributeTopUpMobileFragment(): TopUpMobileFragment

    @ContributesAndroidInjector
    abstract fun contributeTopUpAmountFragment(): TopUpAmountFragment

    @ContributesAndroidInjector
    abstract fun contributeTopUpPinFragment(): TopUpPinFragment

    @ContributesAndroidInjector
    abstract fun contributeTopUpBankCardFragment(): TopUpBankCardFragment

    @ContributesAndroidInjector
    abstract fun contributeARLocationFragment(): ARLocationFragment

    @ContributesAndroidInjector
    abstract fun contributeAddProductFragment(): AddProductFragment

    @ContributesAndroidInjector
    abstract fun contributeOfferFragment(): OfferFragment

    @ContributesAndroidInjector
    abstract fun contributeBotFragment(): BotFragment

    @ContributesAndroidInjector
    abstract fun contributeOrderListFragment(): OrderListFragment

    @ContributesAndroidInjector
    abstract fun contributeOrderTrackHistoryFragment(): OrderTrackHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeOrderAsGuestDialogFragment(): OrderAsGuestDialogFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionsFragment(): TransactionsFragment

    @ContributesAndroidInjector
    abstract fun contributeTransactionDetailsFragment(): TransactionDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeWalletFragment(): WalletFragment
}