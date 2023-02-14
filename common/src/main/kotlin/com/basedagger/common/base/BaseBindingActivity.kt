package com.basedagger.common.base

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.basedagger.common.R
import com.basedagger.common.extension.changeStatusBarColor
import java.lang.reflect.ParameterizedType

abstract class BaseBindingActivity<VB : ViewBinding>: BaseActivity() {

    private var _binding: VB? = null
    protected val binding:VB
        get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    override fun onInitialization() {
        val vbType = (javaClass.genericSuperclass as ParameterizedType)
        val vbClass = vbType.actualTypeArguments[0] as Class<VB>
        val method = vbClass.getMethod("inflate", LayoutInflater::class.java)
        _binding = method.invoke(null, layoutInflater) as VB
        setContentView(binding.root)

        changeStatusBarColor(R.color.colorPrimary)
        resetToolbar()
    }
}