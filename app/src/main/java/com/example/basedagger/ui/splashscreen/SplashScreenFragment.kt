package com.example.basedagger.ui.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.basedagger.base.BaseFragment
import com.example.basedagger.databinding.FragmentSplashscreenBinding

class SplashScreenFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashscreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onInitialization() {
    }

    override fun onReadyAction() {
        findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToExampleFragment())
    }
}