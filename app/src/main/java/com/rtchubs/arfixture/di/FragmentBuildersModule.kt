package com.rtchubs.arfixture.di

import com.rtchubs.arfixture.ar_location.ARLocationFragment
import com.rtchubs.arfixture.nid_scan.NIDScanCameraXFragment
import com.rtchubs.arfixture.ui.add_payment_methods.AddBankFragment
import com.rtchubs.arfixture.ui.add_payment_methods.AddCardFragment
import com.rtchubs.arfixture.ui.add_payment_methods.AddPaymentMethodsFragment
import com.rtchubs.arfixture.ui.add_product.AddProductFragment
import com.rtchubs.arfixture.ui.cart.CartFragment
import com.rtchubs.arfixture.ui.chapter_list.ChapterListFragment
import com.rtchubs.arfixture.ui.exams.ExamsFragment
import com.rtchubs.arfixture.ui.gift_point.GiftPointHistoryDetailsFragment
import com.rtchubs.arfixture.ui.gift_point.GiftPointHistoryFragment
import com.rtchubs.arfixture.ui.home.*
import com.rtchubs.arfixture.ui.how_works.HowWorksFragment
import com.rtchubs.arfixture.ui.info.InfoFragment
import com.rtchubs.arfixture.ui.live_chat.BotFragment
import com.rtchubs.arfixture.ui.login.SignInFragment
import com.rtchubs.arfixture.ui.terms_and_conditions.TermsAndConditionsFragment
import com.rtchubs.arfixture.ui.tou.TouFragment
import com.rtchubs.arfixture.ui.pre_on_boarding.PreOnBoardingFragment
import com.rtchubs.arfixture.ui.profiles.ProfilesFragment
import com.rtchubs.arfixture.ui.registration.RegistrationFragment
import com.rtchubs.arfixture.ui.settings.SettingsFragment
import com.rtchubs.arfixture.ui.setup.SetupFragment
import com.rtchubs.arfixture.ui.setup_complete.SetupCompleteFragment
import com.rtchubs.arfixture.ui.splash.SplashFragment
import com.rtchubs.arfixture.ui.video_play.LoadWebViewFragment
import com.rtchubs.arfixture.ui.video_play.VideoPlayFragment
import com.rtchubs.arfixture.ui.more.MoreFragment
import com.rtchubs.arfixture.ui.offer.OfferFragment
import com.rtchubs.arfixture.ui.order.OrderAsGuestDialogFragment
import com.rtchubs.arfixture.ui.order.OrderListFragment
import com.rtchubs.arfixture.ui.order.OrderTrackHistoryFragment
import com.rtchubs.arfixture.ui.otp_signin.OtpSignInFragment
import com.rtchubs.arfixture.ui.pin_number.PinNumberFragment
import com.rtchubs.arfixture.ui.shops.ShopDetailsContactUsFragment
import com.rtchubs.arfixture.ui.shops.ShopDetailsFragment
import com.rtchubs.arfixture.ui.shops.ShopDetailsProductListFragment
import com.rtchubs.arfixture.ui.topup.TopUpAmountFragment
import com.rtchubs.arfixture.ui.topup.TopUpBankCardFragment
import com.rtchubs.arfixture.ui.topup.TopUpMobileFragment
import com.rtchubs.arfixture.ui.topup.TopUpPinFragment
import com.rtchubs.arfixture.ui.transactions.TransactionDetailsFragment
import com.rtchubs.arfixture.ui.transactions.TransactionsFragment
import com.rtchubs.arfixture.ui.wallet.WalletFragment
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

    @ContributesAndroidInjector
    abstract fun contributeGiftPointHistoryFragment(): GiftPointHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeGiftPointHistoryDetailsFragment(): GiftPointHistoryDetailsFragment
}