package com.stockbit.crypto.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baseapp.repository.repository.TopListRepository
import com.basedagger.common.base.BaseViewModel
import com.basedagger.common.extension.safeApiCall
import com.google.gson.Gson
import com.model.crypto.crypto.ResponseListCryptoInfo
import com.model.crypto.socket.BitcoinTicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.exceptions.WebsocketNotConnectedException
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory

@HiltViewModel
class ListCryptoViewModel @Inject constructor(
    private val webSocketURI: URI,
    private val topListRepository: TopListRepository
): BaseViewModel() {

//    @Inject
//    lateinit var  webSocketURI: URI

    private lateinit var webSocketClient: WebSocketClient

    private val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory

    fun initWebSocket() {
        webSocketClient = object : WebSocketClient(webSocketURI) {
            override fun onOpen(handshakedata: ServerHandshake?) {
//                Log.d(SocketCryptoViewModel.TAG, "onOpen WEBSOCKET")
                reSubscribe()
            }

            override fun onMessage(message: String?) {
//                Log.d(SocketCryptoViewModel.TAG, "onMessage MANUAL: $message")
                message?.let {
                    val bitcoin = Gson().fromJson(it, BitcoinTicker::class.java)
                    newMessage.postValue(bitcoin)
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
//                Log.d(SocketCryptoViewModel.TAG, "onClose")
                unsubscribe()
            }

            override fun onError(ex: Exception?) {
//                Log.e(SocketCryptoViewModel.TAG, "onError: ${ex?.message}")
            }
        }
        webSocketClient.setSocketFactory(socketFactory)
        connectWebSocket()
    }

    val newMessage = MutableLiveData<BitcoinTicker>()
    val refreshList = MutableLiveData<Boolean>()
    val refresh = MutableLiveData<Boolean>()
    val listCrypto: LiveData<PagingData<ResponseListCryptoInfo>> = refreshList.switchMap {
        topListRepository.getPagingTopTier(webSocketClient).cachedIn(viewModelScope)
    }



    suspend fun isDataExist(): Boolean = topListRepository.checkDaoExist()

    fun refresh() {
        viewModelScope.launch {
            topListRepository.clearAllData()
            refreshList.postValue(!(refreshList.value?:false))
            refresh.postValue(!(refresh.value?:false))
        }
    }

    private fun connectWebSocket() {
        if(!webSocketClient.isOpen) {
            webSocketClient.connect()
        }
    }

    fun reConnectingWebSocket() {
        if(webSocketClient.isClosed) {
            webSocketClient.connect()
        }
    }

    fun disconnectWebSocket() {
        if(webSocketClient.isOpen) {
            webSocketClient.close()
        }
    }

    private fun reSubscribe() {
        viewModelScope.launch {
            val list = arrayListOf<String>()
            topListRepository.getListTopTierLocal().forEach {
                list.add("\"21~"+it.coinInfo.name+"\"")
            }
            try{
                webSocketClient.send(
                    "{\n" +
                            "    \"action\": \"SubAdd\",\n" +
                            "    \"subs\": $list" +
                            "}"
                )
            }catch (e:WebsocketNotConnectedException) {
                e.printStackTrace()
            }

        }
    }

    private fun unsubscribe() {
        safeApiCall {
            val list = arrayListOf<String>()
            topListRepository.getListTopTierLocal().forEach {
                list.add("\"21~"+it.coinInfo.name+"\"")
            }
            try{
                webSocketClient.send(
                    "{\n" +
                            "    \"action\": \"SubRemove\",\n" +
                            "    \"subs\": $list" +
                            "}"
                )
            }catch (e:WebsocketNotConnectedException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val TAG = "Coinbase"
    }
}