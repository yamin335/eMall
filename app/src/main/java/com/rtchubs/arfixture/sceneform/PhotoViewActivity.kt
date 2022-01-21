package com.rtchubs.arfixture.sceneform

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.rtchubs.arfixture.R
import kotlinx.android.synthetic.main.activity_photo_view.*


class PhotoViewActivity : AppCompatActivity() {

    private val mAppUnitId: String by lazy {
        getString(R.string.ad_appId)
    }

    private val mInterstitialAdUnitId: String by lazy {
        getString(R.string.ad_interestitial_unitId)
    }

    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var imgUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view)
        window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)

        }


        val extras = intent.extras
        extras?.let {
            imgUri = Uri.parse(it.getString("imageUri"))
            iv_photo.setImageURI(imgUri)
        }

        mInterstitialAd = InterstitialAd(this)

        initializeInterstitialAd(mAppUnitId)

        loadInterstitialAd(mInterstitialAdUnitId)

        runAdEvents()


    }

    private fun initializeInterstitialAd(appUnitId: String) {

        MobileAds.initialize(this, appUnitId)

    }

    private fun loadInterstitialAd(interstitialAdUnitId: String) {

        mInterstitialAd.adUnitId = interstitialAdUnitId
        mInterstitialAd.loadAd(AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build())
    }

    private fun runAdEvents() {

        mInterstitialAd.adListener = object : AdListener() {

            // If user clicks on the ad and then presses the back, s/he is directed to DetailActivity.
            override fun onAdClicked() {
                super.onAdOpened()
//                mInterstitialAd.adListener.onAdClosed()
            }

            // If user closes the ad, s/he is directed to DetailActivity.
            override fun onAdClosed() {
//                openShareActivity()
                super.onAdClosed()

            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Log.e("PhotoViewActivity", "onAdFailedToLoad() with error code: $errorCode")
                super.onAdFailedToLoad(errorCode)
            }

            override fun onAdLoaded() {
                if (!mInterstitialAd.isLoading && mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                }
            }
        }
    }

    private fun openShareActivity() {
        if (imgUri.path != null) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/jepg"
            shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri)
            startActivity(Intent.createChooser(shareIntent, "Share image"))
        } else {
            Toast.makeText(this, "Something wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_photo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }

            R.id.share -> {
                    Log.e("PhotoViewActivity", "The interstitial ad wasn't loaded yet.")
                    openShareActivity()
            }
        }

        return super.onOptionsItemSelected(item)

    }



}
