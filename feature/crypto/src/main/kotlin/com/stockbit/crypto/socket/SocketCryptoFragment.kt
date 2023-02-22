package com.stockbit.crypto.socket

import com.basedagger.common.base.BaseBindingFragment
import com.basedagger.common.extension.observe
import com.stockbit.crypto.databinding.FragmentCryptoSocketBinding

class SocketCryptoFragment :
    BaseBindingFragment<FragmentCryptoSocketBinding, SocketCryptoViewModel>() {

    override fun onObserveAction() {
        observe(viewModel.observablePriceText) { text ->
            binding.tvPriceSocket.text = "1 BTC: $text €"
        }
    }

    override fun onReadyAction() {
        viewModel.initWebSocket()
        binding.btnOpen.setOnClickListener {
            binding.tvPriceSocket.text = "Opened Socket"
            viewModel.initWebSocket()
        }
        binding.btnClose.setOnClickListener {
            binding.tvPriceSocket.text = "Closed Socket"
            viewModel.disconnectWebSocket()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initWebSocket()
    }

    override fun onPause() {
        super.onPause()
        viewModel.disconnectWebSocket()
    }
}