package com.stockbit.crypto.socket

import com.data.common.ViewState
import com.general.common.base.BaseBindingFragment
import com.general.common.extension.safeCollect
import com.google.gson.Gson
import com.model.crypto.socket.BitcoinTicker
import com.stockbit.crypto.adapter.AdapterCrypto
import com.stockbit.crypto.databinding.FragmentCryptoSocketBinding

class SocketCryptoFragment :
    BaseBindingFragment<FragmentCryptoSocketBinding, SocketCryptoViewModel>() {
    private var adapter: AdapterCrypto? = null

    override fun onInitialization() {
        super.onInitialization()
        adapter = AdapterCrypto({ oldValue, newValue ->
            viewModel.unsubscribe(oldValue)
            viewModel.subscribe(newValue)
        }) {
        }
    }

    override fun onObserveAction() {
        safeCollect(viewModel.actionChangeCryptoValue) {
            if (adapter == null) return@safeCollect

            val bitcoin = try {
                Gson().fromJson(it, BitcoinTicker::class.java)
            } catch (e: Exception) {
                return@safeCollect
            }
            adapter?.newPriceValue(bitcoin)
        }
        safeCollect(viewModel.actionGetListCrypto) {
            if (it is ViewState.SUCCESS) {
                adapter?.setListCrypto(it.data)
            }
        }
    }

    override fun onReadyAction() {
        viewModel.initWebSocket()
        binding.rcvCrypto.adapter = adapter
        viewModel.getListCrypto()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initWebSocket()
    }

    override fun onPause() {
        super.onPause()
        viewModel.disconnectWebSocket(adapter?.listCrypto ?: listOf())
    }
}