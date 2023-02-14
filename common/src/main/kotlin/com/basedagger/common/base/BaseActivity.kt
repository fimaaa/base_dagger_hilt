package com.basedagger.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.basedagger.common.R
import com.data.common.enum.Status
import com.basedagger.common.databinding.BasedialogLoadingBinding
import com.basedagger.common.extension.observe
import com.basedagger.common.extension.safeCollect
import com.basedagger.common.utill.DialogUtils
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
abstract class BaseActivity : LocaleAwareCompatActivity(), BaseActivityView {
    private val baseViewModel: BaseActivityViewModel by viewModels()
    private val connectionLiveData: ConnectionLiveData by lazy { ConnectionLiveData(this) }

    private val loadingDialog: AlertDialog by lazy {
        val layoutInflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bindingDialog =
            BasedialogLoadingBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(bindingDialog.root)
        builder.create().apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setCanceledOnTouchOutside(false)
        }
    }

    private var menuToolbar = 0
    private var registeredHandler: (() -> Boolean)? = null

    open fun onInitialization() = Unit

    open fun onReadyAction() = Unit

    open fun onObserveAction() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this) {
            if (registeredHandler != null) {
                if(registeredHandler?.invoke() == true) {
                    return@addCallback
                }
            }
            isEnabled = false
            remove()
            onBackPressedDispatcher.onBackPressed()
        }
        onInitialization()
        observeActivity()
        onObserveAction()
        onReadyAction()
    }

    private fun observeActivity() {
        observe(connectionLiveData) { isConnected ->
            baseViewModel.setStatusConnection(isConnected)
        }
        safeCollect(baseViewModel.statusViewModel) {
            when(it.status) {
                Status.LOADING -> {
                    loadingDialog.show()
                }
                Status.SUCCESS -> {
                    loadingDialog.dismiss()
                }
                else -> {
                    loadingDialog.dismiss()
                    DialogUtils.showAlertDialog(this, it.message.asString(this))
                }
            }
        }
    }

    override fun toolbarVisibility(visible: Boolean) {
        if (visible) supportActionBar?.show() else supportActionBar?.hide()
    }

    override fun resetToolbar() {
        menuToolbar = R.menu.toolbar_default
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""
    }

    override fun setCustomToolbar(menuToolbar: Int, title: String?) {
        this.menuToolbar = menuToolbar

        if(title != null) {
            supportActionBar?.title = title
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (menuToolbar != 0) {
            menuInflater.inflate(menuToolbar, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun setCustomOnBackPressed(listner: () -> Boolean) {
        registeredHandler = listner
    }

    override fun resetOnBackPressed() {
        registeredHandler = null
    }

    override fun changeLanguage(language: String) {
        updateLocale(Locale(language))
    }


}