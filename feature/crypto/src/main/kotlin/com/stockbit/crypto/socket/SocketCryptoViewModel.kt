package com.stockbit.crypto.socket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.basedagger.common.base.BaseViewModel
import com.google.gson.Gson
import com.model.crypto.socket.BitcoinTicker
import dagger.hilt.android.lifecycle.HiltViewModel
import org.java_websocket.client.WebSocketClient
import org.java_websocket.exceptions.WebsocketNotConnectedException
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory

@HiltViewModel
class SocketCryptoViewModel  @Inject constructor(
    private val webSocketURI: URI
): BaseViewModel() {

    private var webSocketClient: WebSocketClient? = null

    private val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory

    fun initWebSocket() {
        webSocketClient = object : WebSocketClient(webSocketURI) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d(TAG, "onOpen")
                subscribe()
            }

            override fun onMessage(message: String?) {
                Log.d(TAG, "onMessage MANUAL: $message")
                message?.let {
                    val bitcoin = Gson().fromJson(it, BitcoinTicker::class.java)
                    _observablePriceText.postValue(bitcoin?.topTierVolume.toString())
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d(TAG, "onClose")
                _observablePriceText.postValue("Closed Socket")
            }

            override fun onError(ex: Exception?) {
                Log.e(TAG, "onError: ${ex?.message}")
            }
        }
        webSocketClient?.setSocketFactory(socketFactory)
        connectWebSocket()
    }

    private val _observablePriceText = MutableLiveData<String>()
    val observablePriceText: LiveData<String>
        get() = _observablePriceText

    fun connectWebSocket() {
        if(webSocketClient?.isOpen == false) {
            webSocketClient?.connect()
        }

    }

    fun disconnectWebSocket() {
        if(webSocketClient?.isOpen == true) {
            unsubscribe()
        }
    }

    private fun subscribe() {
        try{
            webSocketClient?.send(
                "{\n" +
                        "    \"type\": \"subscribe\",\n" +
                        "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BTC-EUR\"] }]\n" +
                        "}"
            )
            webSocketClient?.send(
                "{\n" +
                        "    \"action\": \"SubAdd\",\n" +
                        "    \"subs\": [\"21~BTC\", \"21~ETH\"]" +
                        "}"
            )
        }catch (e: WebsocketNotConnectedException) {
            e.printStackTrace()
        }
    }

    private fun unsubscribe() {
        try{
            webSocketClient?.send(
                "{\n" +
                        "    \"type\": \"unsubscribe\",\n" +
                        "    \"channels\": [\"ticker\"]\n" +
                        "}"
            )
            webSocketClient?.send(
                "{\n" +
                        "    \"action\": \"SubRemove\",\n" +
                        "    \"subs\": [\"21~BTC\", \"21~ETH\"]" +
                        "}"
            )
            webSocketClient?.close()
        }catch (e: WebsocketNotConnectedException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val TAG = "Coinbase"
    }

}