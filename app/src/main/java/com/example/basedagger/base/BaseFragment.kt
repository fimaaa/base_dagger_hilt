package com.example.basedagger.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.basedagger.R
import com.example.basedagger.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
abstract class BaseFragment(
    private val toolbarShow: Boolean = false,
    private val statusBarShow: Boolean = true
) : Fragment() {
    var loadingDialog = LoadingDialog()

    private lateinit var job: Job
    private lateinit var mainView: MainView

    open fun onInitialization() = Unit

    abstract fun onReadyAction()

    open fun onObserveAction() = Unit

    open fun onFragmentDestroyed() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        setHasOptionsMenu(true)
        job = Job()
        onInitialization()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView.toolbarVisibility(toolbarShow)
        activity.hideKeyboard(view)
        onObserveAction()
        onReadyAction()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onFragmentDestroyed()
        mainView.resetToolbar()
        mainView.resetOnBackPressed()
        mainView.resetOnTouchEvent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainView = activity as MainView
    }

    @Suppress("DEPRECATION")
    private fun setStatusBar() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            activity?.window?.setDecorFitsSystemWindows(statusBarShow)
        } else {
            if (statusBarShow) {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                activity?.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }
    }

    fun changeOrientation(orientation: Int) {
        activity?.requestedOrientation = orientation
    }

    fun setCustomToolbar(hasHome: Boolean, title: String, menuToolbar: Int) {
        mainView.setCustomToolbar(hasHome, title, menuToolbar)
    }

    fun setOnBackPressed(listener: () -> Unit) {
        mainView.setCustomOnBackPressed(listener)
    }

    fun setDispatchEvent(listener: (MotionEvent) -> Boolean?) {
        mainView.setDispatchTouchEvent(listener)
    }

    fun changeLanguage(language: String) {
//        prefs.prefLocale = language
        mainView.changeLanguage(language)
    }

    class LoadingDialog : DialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.basedialog_loading, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}