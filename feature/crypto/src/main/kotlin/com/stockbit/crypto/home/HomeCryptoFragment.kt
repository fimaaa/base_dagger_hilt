package com.stockbit.crypto.home

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.basedagger.common.base.BaseBindingFragment
import com.stockbit.crypto.R
import com.baseapp.navigation.R as navR
import com.stockbit.crypto.databinding.FragmentHomeCryptoBinding

class HomeCryptoFragment : BaseBindingFragment<FragmentHomeCryptoBinding, HomeCryptoViewModel>() {
    override fun onReadyAction() = Unit

    override fun onStart() {
        super.onStart()
        val navController: NavController = requireActivity().findNavController(R.id.nav_host_crypto)
        NavigationUI.setupWithNavController(binding.bottomnavCrypto, navController)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.toolbar_home_crypto, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.listEmployee -> {
                        findNavController().navigate(navR.id.nav_graph_employee)
                        true
                    }
                    R.id.firebaseTest -> {
                        findNavController().navigate(navR.id.nav_graph_firebasetest)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}