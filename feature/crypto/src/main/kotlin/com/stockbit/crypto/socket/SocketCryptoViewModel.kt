package com.stockbit.crypto.socket

import androidx.lifecycle.viewModelScope
import com.baseapp.repository.crypto.repository.TopListRepository
import com.data.common.ViewState
import com.general.common.base.BaseViewModel
import com.general.common.extension.safeApiCollect
import com.model.crypto.crypto.ResponseListCryptoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.exceptions.WebsocketNotConnectedException
import org.java_websocket.handshake.ServerHandshake
import timber.log.Timber
import java.net.URI
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory

@HiltViewModel
class SocketCryptoViewModel @Inject constructor(
    private val webSocketURI: URI,
    private val topListRepository: TopListRepository
) : BaseViewModel() {
    private val _actionChangeCryptoValue = MutableSharedFlow<String>(replay = 1)
    val actionChangeCryptoValue: MutableSharedFlow<String>
        get() = _actionChangeCryptoValue

    private var webSocketClient: WebSocketClient? = null

    private val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory

    fun initWebSocket() {
        if (webSocketClient != null) return
        webSocketClient = object : WebSocketClient(webSocketURI) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Timber.d("onOpen")
            }

            override fun onMessage(message: String?) {
                Timber.d("onMessage MANUAL: " + message)
                if (message.isNullOrEmpty()) return
                viewModelScope.launch {
                    _actionChangeCryptoValue.emit(message)
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Timber.d("onClose")
            }

            override fun onError(ex: Exception?) {
                Timber.e("onError: " + ex?.message)
            }
        }
        webSocketClient?.setSocketFactory(socketFactory)
        connectWebSocket()
    }

    private fun connectWebSocket() {
        println("TAG CONNECTWEB")
        if (webSocketClient?.isOpen == false) {
            println("TAG CONNECT")
            webSocketClient?.connect()
        }
    }

    fun disconnectWebSocket(listCrypto: List<ResponseListCryptoInfo>) {
        if (webSocketClient?.isOpen == true) {
            unsubscribe(listCrypto)
            webSocketClient?.close()
        }
    }

    fun subscribe(listCrypto: List<ResponseListCryptoInfo>) {
        println("TAG WILL SUBSCRIBE $webSocketClient")
        try {
            if (webSocketClient == null) return
            listCrypto.forEach {
                println("TAG SUBSCRIBE ${it.coinInfo.name}")
                webSocketClient?.send(
                    "{\n" +
                            "    \"action\": \"SubRemove\",\n" +
                            "    \"subs\": [\"${"21~" + it.coinInfo.name}\"]" +
                            "}"
                )
                webSocketClient?.send(
                    "{\n" +
                            "    \"action\": \"SubAdd\",\n" +
                            "    \"subs\": [\"${"21~" + it.coinInfo.name}\"]" +
                            "}"
                )
            }
            /*
            webSocketClient?.send(
                "{\n" +
                        "    \"action\": \"SubRemove\",\n" +
                        "    \"subs\": [\"21~BTC\"]" +
                        "}"
            )
            webSocketClient?.send(
                "{\n" +
                        "    \"type\": \"unsubscribe\",\n" +
                        "    \"channels\": [\"ticker\"]\n" +
                        "}"
            )
            webSocketClient?.send(
                "{\n" +
                        "    \"type\": \"subscribe\",\n" +
                        "    \"channels\": [{ \"name\": \"ticker\", \"product_ids\": [\"BTC-EUR\"] }]\n" +
                        "}"
            )
            webSocketClient?.send(
                "{\n" +
                        "    \"action\": \"SubAdd\",\n" +
                        "    \"subs\": [\"21~BTC\"]" +
                        "}"
            )
             */
        } catch (e: WebsocketNotConnectedException) {
            e.printStackTrace()
        }
    }

    fun unsubscribe(listCrypto: List<ResponseListCryptoInfo>) {
        try {
            if (webSocketClient == null) return
            listCrypto.forEach {
                webSocketClient?.send(
                    "{\n" +
                            "    \"action\": \"SubRemove\",\n" +
                            "    \"subs\": [\"${"21~" + it.coinInfo.name}\"]" +
                            "}"
                )
            }
            /*
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
             */
        } catch (e: WebsocketNotConnectedException) {
            e.printStackTrace()
        }
    }

    private val _actionGetListCrypto =
        MutableStateFlow<ViewState<List<ResponseListCryptoInfo>>>(ViewState.EMPTY())
    val actionGetListCrypto: StateFlow<ViewState<List<ResponseListCryptoInfo>>>
        get() = _actionGetListCrypto

    fun getListCrypto() = safeApiCollect(defaultDispatcher, _actionGetListCrypto) {
        flow {
            val res = topListRepository.getListTopTier()
            emit(ViewState.SUCCESS(data = res.data))
        }
    }

    companion object {
        const val TAG = "Coinbase"
    }
}