package com.feature.baseapp.firebasetest.login

import com.basedagger.common.base.BaseBindingFragment
import com.basedagger.common.extension.safeOnClikListener
import com.feature.baseapp.firebasetest.databinding.FragmentLoginBinding

class LoginFirebaseFragment : BaseBindingFragment<FragmentLoginBinding, LoginFirebaseViewModel>() {

    override fun onReadyAction() {
        binding.btnLogin.safeOnClikListener {
            viewModel.login(requireActivity())
        }
        viewModel.checkUser()
    }
}