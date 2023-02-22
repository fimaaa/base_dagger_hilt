package com.basedagger.common.extension

import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.Color
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.basedagger.common.R
import com.google.android.material.snackbar.Snackbar
import java.util.*

const val Toast_Error = -1
const val Toast_Default = 0

fun Context.showSnackBar(
    view: View,
    message: CharSequence,
    typeToast: Int = Toast_Error,
    isLong: Boolean = true
) {
    val snackBar = Snackbar.make(
        view,
        message,
        if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
    )
    val snackBarView = snackBar.view
    snackBarView.background = ContextCompat.getDrawable(
        this,
        if (typeToast == Toast_Default) R.drawable.bg_toast else R.drawable.bg_toast_error
    )
    val textView =
        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setTextColor(Color.WHITE)
    textView.textSize = 13f

    val params = snackBarView.layoutParams as CoordinatorLayout.LayoutParams
    params.gravity = Gravity.TOP
    params.setMargins(16, 50, 16, 0)
    snackBarView.layoutParams = params
    snackBar.view.translationZ = 1000F
    snackBar.show()
}

fun Context.showDateDialog(
    selectedDate: (date: Calendar) -> Unit
) {
    val mCalendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        this, R.style.DialogTheme,
        { _, year, monthOfYear, dayOfMonth ->
            mCalendar.set(Calendar.YEAR, year)
            mCalendar.set(Calendar.MONTH, monthOfYear)
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            selectedDate(mCalendar)
        },
        mCalendar.get(Calendar.YEAR),
        mCalendar.get(Calendar.MONTH),
        mCalendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    datePickerDialog.show()
}

fun Context.openLinkApps(url: String) {
    val packageName = url.substringAfter("=")
    var intent = packageManager.getLaunchIntentForPackage(packageName)
    intent?.let {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } ?: let {
        intent = Intent(Intent.ACTION_VIEW)
        intent?.data = Uri.parse(url)
        startActivity(intent)
    }
}

fun Context.convertTODP(size: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (size * scale + 0.5f).toInt()
}

fun Context.openDialer(number: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    startActivity(intent)
}

fun Context.versionAppName(): String {
    var versionAppName = ""
    versionAppName = try {
        packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        ""
    }
    return versionAppName
}

fun Context.isPackageInstalled(packageName: String): Boolean {
    val pm = packageManager
    return try {
        pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: NameNotFoundException) {
        false
    }
}

fun Context.copyToClipboard(
    value: String,
    label: String = "BaseApp_Dagger_Clipboard"
) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, value)
    clipboard.setPrimaryClip(clip)
}

fun Context.toast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(messageId: Int) {
    Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.longToast(messageId: Int) {
    Toast.makeText(this, messageId, Toast.LENGTH_LONG).show()
}