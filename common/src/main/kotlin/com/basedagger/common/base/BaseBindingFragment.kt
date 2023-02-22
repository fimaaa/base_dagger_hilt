package com.basedagger.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.baseapp.navigation.NavigationCommand
import com.basedagger.common.extension.safeCollect
import java.lang.reflect.ParameterizedType

abstract class BaseBindingFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    private val parentViewModel: BaseActivityViewModel by activityViewModels()
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
        viewModel = createViewModelLazy(vmClass.kotlin, { viewModelStore }).value
        _binding = method.invoke(null, layoutInflater, container, false) as VB
        return binding.root
    }

    override fun onObserveAction() {
        safeCollect(parentViewModel.isConnected) { isConnected ->
            viewModel.setStatusConnection(isConnected)
        }
        safeCollect(viewModel.statusViewModel) { status ->
            parentViewModel.setStatusViewModel(status)
        }
        safeCollect(viewModel.actionNavigate) { command ->
            when (command) {
                is NavigationCommand.To -> findNavController().navigate(
                    command.directions,
                    getExtras()
                )
                is NavigationCommand.Back -> findNavController().navigateUp()
            }
        }
    }

    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}