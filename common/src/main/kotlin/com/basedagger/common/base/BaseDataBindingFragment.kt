package com.basedagger.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import com.basedagger.common.extension.safeCollect
import java.lang.reflect.ParameterizedType

abstract class BaseDataBindingFragment<VB : ViewDataBinding, VM : BaseViewModel> : BaseFragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    private val parentViewModel: BaseViewModel by activityViewModels()
    lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vbType = (javaClass.genericSuperclass as ParameterizedType)
        val vbClass = vbType.actualTypeArguments[0] as Class<VB>
        val vmClass = vbType.actualTypeArguments[1] as Class<VM>
        val method = vbClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        _binding = method.invoke(null, layoutInflater, container, false) as VB
        viewModel = createViewModelLazy(vmClass.kotlin, {viewModelStore}).value
        binding.lifecycleOwner = this@BaseDataBindingFragment
        binding.executePendingBindings()
        return binding.root
    }

    override fun onObserveAction() {
        safeCollect(parentViewModel.isConnected) { isConnected ->
            viewModel.setStatusConnection(isConnected)
        }
        safeCollect(viewModel.statusViewModel) { status ->
            parentViewModel.setStatusViewModel(status)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        parentAction.resetToolbar()
        _binding = null
    }
}