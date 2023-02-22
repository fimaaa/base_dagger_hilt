package com.feature.baseapp.firebasetest.home

import com.basedagger.common.base.BaseBindingFragment
import com.basedagger.common.extension.*
import com.feature.baseapp.firebasetest.databinding.FragmentHomeBinding

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