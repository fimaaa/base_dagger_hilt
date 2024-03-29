package com.baseapp.daggerhilt

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.baseapp.daggerhilt.databinding.ActivityMainBinding
import com.baseapp.daggerhilt.databinding.NavHeaderMainBinding
import com.baseapp.navigation.MainNavDirections
import com.baseapp.navigation.NavigationCommand
import com.general.common.base.BaseBindingActivity
import com.general.common.extension.changeStatusBarColor
import com.general.common.extension.gone
import com.general.common.extension.hideStatusBar
import com.general.common.extension.showStatusBar
import com.general.common.extension.toViewState
import com.general.common.extension.visible
import com.general.common.helper.PermissionHelper.getPermission
import com.general.model.common.user.Member
import com.general.testdanamon.main.MainViewModel
import java.util.Locale
import com.feature.baseapp.firebasetest.R as testFirebaseR
import com.stockbit.crypto.R as cryptoR
import com.baseapp.navigation.R.navigation as navR

class ActivityMain : BaseBindingActivity<ActivityMainBinding>() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var parentController: NavController

    private val mAppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                com.general.testdanamon.main.R.id.dashboardFragment,
                testFirebaseR.id.loginFirebaseFragment,
                testFirebaseR.id.homeFirebaseFragment,
                cryptoR.id.homeCryptoFragment
            ), // Masukin ID Fragment yang Navigation Icon is Burger Menu,
            binding.drawerLayout
        )
    }

    override fun navigateModule(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.To -> findNavController(R.id.nav_host_fragment).navigate(
                command.directions
            )

            is NavigationCommand.Back -> findNavController(R.id.nav_host_fragment).navigateUp()
            is NavigationCommand.ManualTo -> findNavController(R.id.nav_host_fragment).navigate(
                command.directionId,
                command.bundle
            )
        }
    }

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

    private fun setConfigUpdateApp() {
        // TODO IF HAVE FIREBASE
//        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
//        val remoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
//            .setMinimumFetchIntervalInSeconds(0) // Set to 0 for developer mode
//            .build()
//        firebaseRemoteConfig.setConfigSettingsAsync(remoteConfigSettings)
//        firebaseRemoteConfig.fetchAndActivate() // fetch every minutes
//            .addOnCompleteListener { task ->
//                Timber.d("TAG COMPLETE LIST")
//                if (task.isSuccessful) {
//                    Timber.d("remote config is fetched.")
//                    firebaseRemoteConfig.fetchAndActivate()
//                }
//            }
//            .addOnFailureListener {
//                Timber.e("TAG ERROR $it")
//            }
    }

    fun getCurrentLocale(context: Context): Locale? {
        val resources = context.resources
        val configuration = resources.configuration
        return configuration.locale
    }

    override fun onInitialization() {
        hideStatusBar()
        onUnAuthorized = {
            findNavController(R.id.nav_host_fragment).navigate(
                MainNavDirections.navigateToAuth()
            )
        }

        changeStatusBarColor(com.general.common.R.color.color_primary)

        askNotificationPermission()
    }

    private fun customActionFragment(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ): Boolean {
        return when (destination.id) {
            testFirebaseR.id.loginFirebaseFragment,
            testFirebaseR.id.homeFirebaseFragment,
            cryptoR.id.homeCryptoFragment,
            com.general.testdanamon.main.R.id.dashboardFragment -> {
                binding.topAppBar.apply {
                    title = ""
//                    setNavigationIcon(commonR.drawable.ic_menu)
                    setNavigationOnClickListener {
                        binding.drawerLayout.openDrawer(GravityCompat.START)
                    }
                }
                binding.topAppBarLogo.apply {
                    setImageResource(com.general.common.R.drawable.logo_app_general)
                    visible()
                }
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                true
            }

            com.general.splashscreen.R.id.splashScreenFragment -> {
                toolbarVisibility(false)
                true
            }

            else -> false
        }
    }

    private fun grantedAllPermission() {
        setTheme(R.style.Theme_BaseDagger)
        showStatusBar()
        super.onInitialization()
        settingUpFragment()
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

    private fun settingUpFragment() {
        binding.layoutNavFooter.tvLogout.setOnClickListener {
            navigateModule(
                NavigationCommand.To(
                    MainNavDirections.navigateToAuth()
                )
            )
        }
        findNavController(R.id.nav_host_fragment).setGraph(navR.main_nav)
        setSupportActionBar(binding.topAppBar)
//        val navController: NavController =
//            Navigation.findNavController(this, R.id.nav_host_fragment)
        parentController = findNavController(R.id.nav_host_fragment).apply {
            // Set up ActionBar
            setupActionBarWithNavController(this, binding.drawerLayout)
            // Set up NavigationView
            binding.navView.setupWithNavController(this)
        }
        NavigationUI.setupActionBarWithNavController(
            this, parentController, mAppBarConfiguration
        )
        NavigationUI.setupWithNavController(binding.navView, parentController)
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { controller, destination, argument ->
            if (destination == null) {
                // Navigate to a default/fallback destination
                findNavController(R.id.nav_host_fragment).navigate(com.general.splashscreen.R.id.splashScreenFragment)
            }
            // Buat CUstom tombol dikiri / mau custom toolbar
            if (!customActionFragment(controller, destination, argument)) {
                binding.topAppBar.visible()
                binding.topAppBarLogo.gone()
                binding.topAppBar.setNavigationOnClickListener {
                    parentController.navigateUp()
                }
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    override fun changeDataMember(member: Member) {
        val navHeaderMainBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        viewModel

        navHeaderMainBinding.tvNameMember.text = member.fullname
        navHeaderMainBinding.tvIdMember.text = member.id
        navHeaderMainBinding.tvPhoneMember.text = member.phoneNumber.ifEmpty { "-" }
    }

    override fun handleIntentUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            baseViewModel.setStatusViewModel(e.toViewState())
        }
    }

    override fun onStart() {
        super.onStart()
        setConfigUpdateApp()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(
            navController, mAppBarConfiguration
        ) || super.onSupportNavigateUp()
    }
}