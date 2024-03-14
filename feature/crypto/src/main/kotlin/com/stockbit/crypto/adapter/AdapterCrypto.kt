package com.stockbit.crypto.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.model.crypto.crypto.ResponseListCryptoInfo
import com.model.crypto.socket.BitcoinTicker
import com.stockbit.crypto.databinding.ItemCryptoBinding

class AdapterCrypto(
    private val listenerNewList: ((List<ResponseListCryptoInfo>, List<ResponseListCryptoInfo>) -> Unit)? = null,
    private val listener: (ResponseListCryptoInfo) -> Unit
) : RecyclerView.Adapter<AdapterCrypto.ViewHolder>() {
    val listCrypto = mutableListOf<ResponseListCryptoInfo>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListCrypto(listCrypto: List<ResponseListCryptoInfo>) {
        listenerNewList?.invoke(this.listCrypto, listCrypto)
        this.listCrypto.clear()
        this.listCrypto.addAll(listCrypto)
        notifyDataSetChanged()
    }

    fun newPriceValue(bitcoin: BitcoinTicker) {
        var selectedCrypto: ResponseListCryptoInfo? = null
        var selectedPosition: Int = -1
        run breaking@{
            listCrypto.forEachIndexed { index, currentItem ->
                if (currentItem.coinInfo.name != bitcoin.symbol) return@forEachIndexed
                selectedCrypto = currentItem
                selectedPosition = index
                // break the loop
                return@breaking
            }
        }
        if (selectedCrypto != null && selectedPosition >= 0) {
            listCrypto[selectedPosition] = selectedCrypto!!.copy(
                moneyData = selectedCrypto!!.moneyData.copy(
                    coinValue = selectedCrypto!!.moneyData.coinValue.copy(
                        price = bitcoin.topTierVolume.toString()
                    )
                )
            )
            notifyItemChanged(selectedPosition)
        }
    }

//    private fun subscribeSocekt() {
//        if(webSocketClient)
//        listCrypto.forEach {
//                webSocketClient.send(
//                    "{\n" +
//                            "    \"action\": \"SubRemove\",\n" +
//                            "    \"subs\": [\"${"21~" + it.coinInfo.name}\"]" +
//                            "}"
//                )
//                webSocketClient.send(
//                    "{\n" +
//                            "    \"action\": \"SubAdd\",\n" +
//                            "    \"subs\": [\"${"21~" + it.coinInfo.name}\"]" +
//                            "}"
//                )
//            }
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCrypto[position])
    }

    override fun getItemCount(): Int = listCrypto.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCryptoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    inner class ViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exampleData: ResponseListCryptoInfo) {
            binding.data = exampleData
            binding.root.setOnClickListener {
                listener.invoke(exampleData)
            }
        }
    }
}