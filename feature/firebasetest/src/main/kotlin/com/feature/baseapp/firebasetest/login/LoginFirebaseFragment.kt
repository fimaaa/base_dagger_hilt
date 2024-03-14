package com.feature.baseapp.firebasetest.login

import com.feature.baseapp.firebasetest.databinding.FragmentFirebaseLoginBinding
import com.general.common.base.BaseBindingFragment
import com.general.common.extension.safeOnClikListener

class LoginFirebaseFragment : BaseBindingFragment<FragmentFirebaseLoginBinding, LoginFirebaseViewModel>() {
    override fun onReadyAction() {
        binding.btnLogin.safeOnClikListener {
            viewModel.login(requireActivity())
        }
        viewModel.checkUser()
    }
}