package com.baseapp.daggerhilt

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.baseapp.daggerhilt.databinding.ActivityMainBinding
import com.basedagger.common.base.BaseBindingActivity
import com.basedagger.common.extension.hideStatusBar
import com.basedagger.common.extension.showStatusBar
import com.basedagger.common.helper.PermissionHelper.getPermission
import com.feature.baseapp.firebasetest.R as testFirebaseR
import com.stockbit.crypto.R as cryptoR

class ActivityMain : BaseBindingActivity<ActivityMainBinding>() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        var isGranted = true
        result.values.forEach {
            if (!it) isGranted = false
        }
        if (isGranted) {
            grantedAllPermission()
        } else {
            askNotificationPermission(true)
        }
    }

    override fun onInitialization() {
        hideStatusBar()
        askNotificationPermission()
    }

    private fun grantedAllPermission() {
        setTheme(R.style.Theme_BaseDagger)
        showStatusBar()
        super.onInitialization()
        findNavController(R.id.nav_host_fragment).setGraph(com.baseapp.navigation.R.navigation.main_nav)
        setSupportActionBar(binding.topAppBar)

        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.nav_host_fragment),
            AppBarConfiguration(
                setOf(
                    testFirebaseR.id.loginFirebaseFragment,
                    testFirebaseR.id.homeFirebaseFragment,
                    cryptoR.id.homeCryptoFragment
                )
            )
        )
    }

    private fun askNotificationPermission(
        isShowDialog: Boolean = false
    ) {
        val listPermission = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listPermission.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        getPermission(
            requestPermissionLauncher,
            listPermission = listPermission.toTypedArray(),
            isShowDialog = isShowDialog,
            onGranted = {
                grantedAllPermission()
            }
        )
    }
}