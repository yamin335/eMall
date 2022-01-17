package com.rtchubs.arfixture

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.Nullable
import com.rtchubs.arfixture.util.CommonUtils
import com.rtchubs.arfixture.util.NetworkUtils
import timber.log.Timber


class SyncOnConnectivityReceiver : BroadcastReceiver() {
    override fun onReceive(@Nullable context: Context?, intent: Intent) {
        Timber.d("triggering on connectivity change")
        if (context != null) {
            if (!NetworkUtils.isNetworkConnected(context)) {
                CommonUtils.fireErrorMessageEvent(
                        error = context.getString(R.string.no_internet_error),
                        showInAlert = false
                )
            }
        }
    }
}
