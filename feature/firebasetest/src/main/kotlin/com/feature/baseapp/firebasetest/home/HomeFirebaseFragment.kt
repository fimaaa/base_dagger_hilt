package com.feature.baseapp.firebasetest.home

import com.feature.baseapp.firebasetest.databinding.FragmentHomeBinding
import com.general.common.base.BaseBindingFragment
import com.general.common.extension.Toast_Default
import com.general.common.extension.copyToClipboard
import com.general.common.extension.isGPSReady
import com.general.common.extension.safeCollect
import com.general.common.extension.safeOnClikListener
import com.general.common.extension.showSnackBar

class HomeFirebaseFragment : BaseBindingFragment<FragmentHomeBinding, HomeFirebaseViewModel>() {

    override fun onObserveAction() {
        super.onObserveAction()
        safeCollect(viewModel.actionUser) {
            if (it != null) {
                requireActivity().isGPSReady {
                    viewModel.saveUserToDatabase(it)
                }
            }
            binding.tvUser.text = it?.displayName?.ifEmpty { it.email?.ifEmpty { it.uid } }
        }
        safeCollect(viewModel.tokenFirebase) {
            binding.tvToken.text = it
        }
    }

    override fun onReadyAction() {
        binding.btnLogout.safeOnClikListener {
            viewModel.signOut()
        }
        binding.tvToken.setOnClickListener {
            requireContext().copyToClipboard(binding.tvToken.text.toString())
            requireContext().showSnackBar(binding.root, "Token Copied", Toast_Default)
        }
    }
}