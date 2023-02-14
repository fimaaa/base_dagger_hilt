package com.baseapp.daggerhilt

import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.baseapp.daggerhilt.databinding.ActivityMainBinding
import com.basedagger.common.base.BaseBindingActivity

class ActivityMain : BaseBindingActivity<ActivityMainBinding>() {

    override fun onInitialization() {
        setTheme(R.style.Theme_BaseDagger)
        super.onInitialization()
        setSupportActionBar(binding.topAppBar)
        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.nav_host_fragment)
        )
//        loadingDialog.show()
//        lifecycleScope.launch {
//            delay(10000L)
//            loadingDialog.dismiss()
//        }
    }
}