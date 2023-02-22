package com.stockbit.crypto.list

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.basedagger.common.base.BaseBindingFragment
import com.basedagger.common.base.BaseLoadStateAdapter
import com.basedagger.common.extension.observe
import com.basedagger.common.extension.showSnackbar
import com.basedagger.common.extension.toThrowableCode
import com.basedagger.common.extension.toThrowableMessage
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.snackbar.Snackbar
import com.stockbit.crypto.R
import com.basedagger.common.R as CommonR
import com.stockbit.crypto.adapter.AdapterCrypto
import com.stockbit.crypto.databinding.FragmentListcryptoBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ListCryptoFragment : BaseBindingFragment<FragmentListcryptoBinding, ListCryptoViewModel>() {
    private var skeleton: Skeleton? = null
    private var adapter: AdapterCrypto = AdapterCrypto {
        showSnackbar(it.coinInfo.fullName, Snackbar.LENGTH_LONG)
    }.apply {
        addLoadStateListener { loadState ->
            binding.apply {
                blanklayout.isVisible = false
                rcvCrypto.isVisible = true
                when {
                    loadState.refresh is LoadState.Loading -> {
                        lifecycleScope.launch {
                            if (itemCount < 1 && !viewModel.isDataExist()) skeleton?.showSkeleton()
                        }
                    }
                    loadState.refresh is LoadState.NotLoading -> {
                        if (skeleton?.isSkeleton() == true) skeleton?.showOriginal()
                        if (loadState.append.endOfPaginationReached &&
                            itemCount < 1
                        ) {
                            blanklayout.isVisible = true
                            blanklayout.setType(java.net.HttpURLConnection.HTTP_NO_CONTENT)
                        }
                    }
                    loadState.mediator?.refresh is LoadState.Error -> {
                        if (skeleton?.isSkeleton() == true) skeleton?.showOriginal()
                        val throwable = (loadState.refresh as LoadState.Error).error
                        blanklayout.isVisible = itemCount < 1
                        blanklayout.setType(
                            throwable.toThrowableCode(),
                            throwable.toThrowableMessage().asString(requireContext())
                        )
                        blanklayout.setOnClick(getString(CommonR.string.retry)) {
                            retry()
                        }
                    }
                }
            }
        }
    }

    override fun onInitialization() {
    }

    override fun onObserveAction() {
        observe(viewModel.listCrypto) {
            adapter?.submitData(lifecycle, it)
        }
        observe(viewModel.refresh) {
            adapter?.refresh()
            binding.swiperefreshCrypto.isRefreshing = false
        }
        observe(viewModel.newMessage) {
            adapter?.snapshot()?.items?.forEachIndexed { position, item ->
                if (item.coinInfo.name == it.symbol) {
                    item.moneyData.coinValue.change = it.topTierVolume ?: 0.0
                    adapter?.notifyItemChanged(position)
                }
            }
        }
    }

    override fun onReadyAction() {
//        parentAction.setCustomToolbar()
//        setToolbar(
//            leftImage = R.drawable.ic_menu,
//            centerImage = R.drawable.ic_logo_stockbit,
//            rightImage = R.drawable.ic_file_save
//        )
        viewModel.initWebSocket()
        viewModel.refresh()
        println("TAG ONREADY LISTCRYPTO $adapter")
        binding.rcvCrypto.adapter = adapter?.withLoadStateHeaderAndFooter(
            header = BaseLoadStateAdapter { adapter.retry() },
            footer = BaseLoadStateAdapter { adapter.retry() }
        )
        skeleton = binding.rcvCrypto.applySkeleton(R.layout.item_crypto)
        skeleton?.showShimmer = true
        binding.swiperefreshCrypto.setOnRefreshListener {
            viewModel.refresh()
        }
        viewModel.refreshList.apply {
            value = !(value ?: false)
        }
        binding.tvTitleCrypto.setOnClickListener {
            val data = adapter.snapshot().firstOrNull { info ->
                info?.coinInfo?.name == "BTC"
            }
            data?.coinInfo?.name = "BERUBAH"
            adapter.snapshot().items.forEachIndexed { position, item ->
                if (item.coinInfo.name == "BTC") {
                    item.coinInfo.name = "CHANGED"
                    adapter.notifyItemChanged(position)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initWebSocket()
        binding.swiperefreshCrypto.isEnabled = true
    }

    override fun onPause() {
        super.onPause()
        viewModel.disconnectWebSocket()
        binding.swiperefreshCrypto.isEnabled = false
    }

    override fun onFragmentDestroyed() {
        viewModel.refresh.removeObservers(viewLifecycleOwner)
        lifecycleScope.cancel()
        skeleton = null
//        adapter = null
    }
}