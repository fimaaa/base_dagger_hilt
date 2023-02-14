package com.basedagger.common.extension

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.basedagger.common.R

fun Activity.navigateTo(to: Class<*>) {
    startActivity(Intent(this, to))
    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
}

fun Activity.navigateTo(intent: Intent) {
    startActivity(intent)
    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
}

fun Activity.navigateForResultTo(requestCode: Int, to: Class<*>) {
    startActivityForResult(Intent(this, to), requestCode)
    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
}

fun Activity.navigateForResultTo(requestCode: Int, intent: Intent) {
    startActivityForResult(intent, requestCode)
    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
}

fun Activity.newNavigateForResultTo(launcher: ActivityResultLauncher<Intent>, to: Class<*>) {
    launcher.launch(Intent(this, to))
    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
}

fun Activity.newNavigateForResultTo(launcher: ActivityResultLauncher<Intent>, intent: Intent) {
    launcher.launch(intent)
    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
}