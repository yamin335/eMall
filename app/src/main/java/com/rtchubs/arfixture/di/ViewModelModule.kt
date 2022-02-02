package com.rtchubs.arfixture.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rtchubs.arfixture.ViewModelFactory
import com.rtchubs.arfixture.ar_location.ARLocationViewModel
import com.rtchubs.arfixture.nid_scan.NIDScanCameraXViewModel
import com.rtchubs.arfixture.ui.LoginActivityViewModel
import com.rtchubs.arfixture.ui.MainActivityViewModel
import com.rtchubs.arfixture.ui.add_payment_methods.AddBankViewModel
import com.rtchubs.arfixture.ui.add_payment_methods.AddCardViewModel
import com.rtchubs.arfixture.ui.add_payment_methods.AddPaymentMethodsViewModel
import com.rtchubs.arfixture.ui.add_product.AddProductViewModel
import com.rtchubs.arfixture.ui.cart.CartViewModel
import com.rtchubs.arfixture.ui.chapter_list.ChapterListViewModel
import com.rtchubs.arfixture.ui.exams.ExamsViewModel
import com.rtchubs.arfixture.ui.gift_point.GiftPointHistoryDetailsViewModel
import com.rtchubs.arfixture.ui.gift_point.GiftPointHistoryViewModel
import com.rtchubs.arfixture.ui.home.*
import com.rtchubs.arfixture.ui.how_works.HowWorksViewModel
import com.rtchubs.arfixture.ui.info.InfoViewModel
import com.rtchubs.arfixture.ui.live_chat.LiveChatViewModel
import com.rtchubs.arfixture.ui.login.SignInViewModel
import com.rtchubs.arfixture.ui.more.MoreViewModel
import com.rtchubs.arfixture.ui.offer.OfferViewModel
import com.rtchubs.arfixture.ui.order.OrderAsGuestDialogViewModel
import com.rtchubs.arfixture.ui.order.OrderTrackHistoryViewModel
import com.rtchubs.arfixture.ui.order.OrderViewModel
import com.rtchubs.arfixture.ui.otp_signin.OtpSignInViewModel
import com.rtchubs.arfixture.ui.pin_number.PinNumberViewModel
import com.rtchubs.arfixture.ui.pre_on_boarding.PreOnBoardingViewModel
import com.rtchubs.arfixture.ui.product_ar_view.ProductARViewModel
import com.rtchubs.arfixture.ui.profiles.ProfilesViewModel
import com.rtchubs.arfixture.ui.registration.RegistrationViewModel
import com.rtchubs.arfixture.ui.settings.SettingsViewModel
import com.rtchubs.arfixture.ui.setup.SetupViewModel
import com.rtchubs.arfixture.ui.setup_complete.SetupCompleteViewModel
import com.rtchubs.arfixture.ui.shops.ShopDetailsContactUsViewModel
import com.rtchubs.arfixture.ui.shops.ShopDetailsViewModel
import com.rtchubs.arfixture.ui.splash.SplashViewModel
import com.rtchubs.arfixture.ui.terms_and_conditions.TermsViewModel
import com.rtchubs.arfixture.ui.topup.TopUpAmountViewModel
import com.rtchubs.arfixture.ui.topup.TopUpBankCardViewModel
import com.rtchubs.arfixture.ui.topup.TopUpMobileViewModel
import com.rtchubs.arfixture.ui.topup.TopUpPinViewModel
import com.rtchubs.arfixture.ui.tou.TouViewModel
import com.rtchubs.arfixture.ui.transactions.TransactionDetailsViewModel
import com.rtchubs.arfixture.ui.transactions.TransactionsViewModel
import com.rtchubs.arfixture.ui.video_play.LoadWebViewViewModel
import com.rtchubs.arfixture.ui.video_play.VideoPlayViewModel
import com.rtchubs.arfixture.ui.wallet.WalletViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    // PSB View Model Bind Here
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel::class)
    abstract fun bindLoginActivityViewModel(viewModel: LoginActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreBookListViewModel::class)
    abstract fun bindMoreBookListViewModel(viewModel: MoreBookListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    abstract fun bindProductListViewModel(viewModel: ProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindCartViewModel(viewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailsViewModel::class)
    abstract fun bindProductDetailsViewModel(viewModel: ProductDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreShoppingMallViewModel::class)
    abstract fun bindMoreShoppingMallViewModel(viewModel: MoreShoppingMallViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllShopListViewModel::class)
    abstract fun bindAllShopListViewModel(viewModel: AllShopListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopDetailsViewModel::class)
    abstract fun bindShopDetailsViewModel(viewModel: ShopDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopDetailsContactUsViewModel::class)
    abstract fun bindShopDetailsContactUsViewModel(viewModel: ShopDetailsContactUsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreViewModel::class)
    abstract fun bindMoreViewModel(viewModel: MoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetAViewModel::class)
    abstract fun bindSetAViewModel(viewModel: SetAViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetBViewModel::class)
    abstract fun bindSetBViewModel(viewModel: SetBViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetCViewModel::class)
    abstract fun bindSetCViewModel(viewModel: SetCViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel::class)
    abstract fun bindInfoViewModel(viewModel: InfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExamsViewModel::class)
    abstract fun bindExamsViewModel(viewModel: ExamsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NIDScanCameraXViewModel::class)
    abstract fun bindNIDScanCameraXViewModel(viewModel: NIDScanCameraXViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PreOnBoardingViewModel::class)
    abstract fun bindPreOnBoardingViewModel(viewModel: PreOnBoardingViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HowWorksViewModel::class)
    abstract fun bindHowWorksViewModel(viewModel: HowWorksViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TouViewModel::class)
    abstract fun bindTouViewModel(viewModel: TouViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetupViewModel::class)
    abstract fun bindSetupViewModel(viewModel: SetupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetupCompleteViewModel::class)
    abstract fun bindSetupCompleteViewModel(viewModel: SetupCompleteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfilesViewModel::class)
    abstract fun bindSetupProfilesViewModel(viewModel: ProfilesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSetupSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionsViewModel::class)
    abstract fun bindTransactionsViewModel(viewModel: TransactionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChapterListViewModel::class)
    abstract fun bindSetupChapterListViewModel(viewModel: ChapterListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoPlayViewModel::class)
    abstract fun bindSetupVideoPlayViewModel(viewModel: VideoPlayViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoadWebViewViewModel::class)
    abstract fun bindSetupLoadWebViewViewModel(viewModel: LoadWebViewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OtpSignInViewModel::class)
    abstract fun bindSetupOtpSignInViewModel(viewModel: OtpSignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PinNumberViewModel::class)
    abstract fun bindSetupPinNumberViewModel(viewModel: PinNumberViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TermsViewModel::class)
    abstract fun bindTermsViewModel(viewModel: TermsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddPaymentMethodsViewModel::class)
    abstract fun bindAddPaymentMethodsViewModel(viewModel: AddPaymentMethodsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddBankViewModel::class)
    abstract fun bindAddBankViewModel(viewModel: AddBankViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCardViewModel::class)
    abstract fun bindAddCardViewModel(viewModel: AddCardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopUpMobileViewModel::class)
    abstract fun bindTopUpMobileViewModel(viewModel: TopUpMobileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopUpAmountViewModel::class)
    abstract fun bindTopUpAmountViewModel(viewModel: TopUpAmountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopUpPinViewModel::class)
    abstract fun bindTopUpPinViewModel(viewModel: TopUpPinViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopUpBankCardViewModel::class)
    abstract fun bindTopUpBankCardViewModel(viewModel: TopUpBankCardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ARLocationViewModel::class)
    abstract fun bindARLocationViewModel(viewModel: ARLocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LiveChatViewModel::class)
    abstract fun bindLiveChatViewModel(viewModel: LiveChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddProductViewModel::class)
    abstract fun bindAddProductViewModel(viewModel: AddProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OfferViewModel::class)
    abstract fun bindOfferViewModel(viewModel: OfferViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    abstract fun bindOrderViewModel(viewModel: OrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderTrackHistoryViewModel::class)
    abstract fun bindOrderTrackHistoryViewModel(viewModel: OrderTrackHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderAsGuestDialogViewModel::class)
    abstract fun bindOrderAsGuestDialogViewModel(viewModel: OrderAsGuestDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionDetailsViewModel::class)
    abstract fun bindTransactionDetailsViewModel(viewModel: TransactionDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    abstract fun bindWalletViewModel(viewModel: WalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GiftPointHistoryViewModel::class)
    abstract fun bindGiftPointHistoryViewModel(viewModel: GiftPointHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GiftPointHistoryDetailsViewModel::class)
    abstract fun bindGiftPointHistoryDetailsViewModel(viewModel: GiftPointHistoryDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductARViewModel::class)
    abstract fun bindProductARViewModel(viewModel: ProductARViewModel): ViewModel
}