package com.mallzhub.customer.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.mallzhub.customer.ui.add_product.PERMISSION_REQUEST_CODE
import com.mallzhub.customer.ui.common.CommonAlertDialog

object PermissionUtils {
    fun isCameraAndGalleryPermissionGranted(fragmentActivity: FragmentActivity): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            when (PackageManager.PERMISSION_GRANTED) {
                fragmentActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                -> {
                    true
                }
                fragmentActivity.checkSelfPermission(Manifest.permission.CAMERA)
                -> {
                    true
                }
                else -> {
                    ActivityCompat.requestPermissions(
                        fragmentActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                        PERMISSION_REQUEST_CODE
                    )
                    false
                }
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            true
        }
    }

    fun isGalleryPermission(fragmentActivity: FragmentActivity): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (fragmentActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    fragmentActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            true
        }
    }

    fun isCameraPermission(fragmentActivity: FragmentActivity): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (fragmentActivity.checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    fragmentActivity,
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_CODE
                )
                false
            }
        } else {
            true
        }
    }

    fun checkPermission(activity: AppCompatActivity, permissions: Array<String>): Boolean = permissions.all { permission ->
        activity.checkSelfPermissionCompat(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionRationale(activity: AppCompatActivity, permissions: Array<String>): Boolean = permissions.all { permission ->
        activity.shouldShowRequestPermissionRationaleCompat(permission)
    }

    fun requestPermission(activity: AppCompatActivity, permissionsArray: Array<String>,
                          fragmentManager: FragmentManager,
                          permissionRequestCode: Int) {
        val shouldProvidePermissionRationale = checkPermissionRationale(activity, permissionsArray)

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvidePermissionRationale) {
            val explanationDialog = CommonAlertDialog(object :  CommonAlertDialog.YesCallback{
                override fun onYes() {
                    activity.requestPermissionsCompat(permissionsArray, permissionRequestCode)
                }
            }, "Allow Permissions", "Allow location and camera permissions to use this feature.\n\nDo you want to allow permission?")
            explanationDialog.show(fragmentManager, "#call_permission_dialog")
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            activity.requestPermissionsCompat(permissionsArray, permissionRequestCode)
        }
    }

    fun goToSettings(context: Context, packageName: String) {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { intent ->
            context.startActivity(intent)
        }
    }
}