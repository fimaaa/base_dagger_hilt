package com.basedagger.common.helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.basedagger.common.utill.DialogUtils
import com.data.common.UIText

object PermissionHelper {
    fun checkPermission(
        mContext: Context,
        listPermission: Array<String>
    ): Boolean {
        var isGranted = true
        listPermission.forEach {
            if (ContextCompat.checkSelfPermission(
                    mContext,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) isGranted = false
        }
        return isGranted
    }

    private fun checkDeniedPermission(
        mContext: Context,
        listPermission: Array<String>
    ): Boolean {
        var isDenied = true
        listPermission.forEach {
            if (ContextCompat.checkSelfPermission(
                    mContext,
                    it
                ) != PackageManager.PERMISSION_DENIED
            ) isDenied = false
        }
        return isDenied
    }

    fun checkRationPermission(
        mContext: Activity,
        listPermission: Array<String>
    ): Boolean {
        var isGranted = true
        listPermission.forEach {
            isGranted = shouldShowRequestPermissionRationale(
                mContext,
                it
            )
        }
        return isGranted
    }

    fun AppCompatActivity.getPermission(
        requestPermissionLauncher: ActivityResultLauncher<Array<String>>,
        listPermission: Array<String>,
        isShowDialog: Boolean = false,
        deniedDesc: UIText = UIText.DynamicString("You cannot use apps because the permission is denied \n You need to allow the permission manually"),
        onGranted: () -> Unit
    ) {

        when {
            checkPermission(
                this,
                listPermission
            ) -> onGranted.invoke()
            checkRationPermission(this, listPermission) -> DialogUtils.showAlertDialog(
                this,
                UIText.DynamicString("You Need allow this permission, so the apps can use all the feature")
                    .asString(this),
                "Alert!"
            ) {
                requestPermissionLauncher.launch(listPermission)
            }
            (checkDeniedPermission(
                this,
                listPermission
            ) && isShowDialog) -> DialogUtils.showAlertDialog(
                this,
                deniedDesc.asString(this),
                "Alert!"
            ) {
                requestPermissionLauncher.launch(listPermission)
            }
            else -> requestPermissionLauncher.launch(listPermission)
        }
    }
}