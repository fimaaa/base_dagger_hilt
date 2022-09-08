package com.example.basedagger.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.basedagger.BR
import com.example.basedagger.R
import com.example.basedagger.data.enum.Status
import com.example.basedagger.extension.observe
import com.example.basedagger.utill.DialogUtils
import com.example.basedagger.extension.login
import javax.net.ssl.HttpsURLConnection

abstract class BaseBindingFragment<binding : ViewDataBinding, V : BaseViewModel>(
    @LayoutRes
    private val layoutID: Int
) : BaseFragment() {
    private var _binding: binding? = null
    val binding get() = _binding!!

    abstract fun getViewModels(): V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutID, container, false)

        binding.setVariable(BR.data, getViewModels())
        binding.executePendingBindings()

        return binding.root
    }

    override fun onInitialization() {
        getViewModels().navigator = this as BaseNavigator
    }

    override fun onObserveAction() {
        super.onObserveAction()
        observe(getViewModels().statusViewModel) {
            when (it.status) {
                Status.LOADING -> {
                    loadingDialog.showsDialog
                }
                Status.SUCCESS -> {
                    loadingDialog.dismiss()
                }
                else -> {
                    loadingDialog.dismiss()
                    when (it.code) {
//                        ErrorType.INTERNET_ERROR -> error.message?.let {
//                            DialogUtils.showDialogAlert(requireContext(), "Perhatian", "Network Error")
//                        }
                        HttpsURLConnection.HTTP_UNAUTHORIZED -> DialogUtils.showDialogAlert(
                            requireContext(),
                            "Perhatian",
                            "Anda telah masuk di device lain"
                        ) {
                            requireActivity().login()
                        }
                        else -> defaultError(it.message.asString(requireContext()), it.code)
                    }
                }
            }
        }
    }

    open fun defaultError(message: String?, code: Int?) {
        DialogUtils.showDialogAlert(requireContext(), "Perhatian", message ?: getString(R.string.error_default))
    }

    private fun showLoading() {
        val transaction = childFragmentManager.beginTransaction()
        val dialog = childFragmentManager.findFragmentByTag("Loading")
        if (dialog?.isAdded == true) return
        loadingDialog.isCancelable = false
        try {
            loadingDialog.show(transaction, "Loading")
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}