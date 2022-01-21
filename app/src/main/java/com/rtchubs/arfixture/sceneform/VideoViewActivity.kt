package com.rtchubs.arfixture.sceneform

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.rtchubs.arfixture.R
import java.io.File


class VideoViewActivity : AppCompatActivity() {
    private lateinit var eyebrowsVideoView: EyebrowsVideoView
    private var videoUri:Uri? = null

    private lateinit var mInterstitialAd: InterstitialAd

    private val mAppUnitId: String by lazy {
        getString(R.string.ad_appId)
    }

    private val mInterstitialAdUnitId: String by lazy {
        getString(R.string.ad_interestitial_unitId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)

        window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)

        }

        eyebrowsVideoView = findViewById(R.id.videoView);


        val extras = intent.extras
        extras?.let {
            videoUri = Uri.parse(it.getString("videoPath"))
            eyebrowsVideoView.setDataSource(this , videoUri!!)
            eyebrowsVideoView.setLooping(true)
            eyebrowsVideoView.prepareAsync(MediaPlayer.OnPreparedListener {
                eyebrowsVideoView.start()
            })
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
                super.onAdClosed()
//                openShareActivity()
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
        videoUri?.let {
            val sharingIntent = Intent(Intent.ACTION_SEND);
            sharingIntent.setType("video/*"); //If it is a 3gp video use ("video/3gp")
            val media = File(it.path!!)
            val uri = Uri.fromFile(media)
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Checkout this video from plant a christmas tree app!");
            startActivity(Intent.createChooser(sharingIntent, "Share Video"));
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_photo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                onBackPressed()
            }

            R.id.share ->{
                openShareActivity()
            }
        }

        return super.onOptionsItemSelected(item)

    }
}
