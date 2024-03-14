package com.feature.employee.list

import android.os.CountDownTimer
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.feature.employee.R
import com.feature.employee.adapter.EmployeePagingAdapter
import com.feature.employee.databinding.FragmentEmployeeBinding
import com.general.common.base.BaseDataBindingFragment
import com.general.common.base.BaseLoadStateAdapter
import com.general.common.extension.Toast_Default
import com.general.common.extension.gone
import com.general.common.extension.observe
import com.general.common.extension.showSnackBar
import com.general.common.extension.toThrowableCode
import com.general.common.extension.toThrowableMessage
import com.general.common.extension.visible
import java.net.HttpURLConnection
import com.general.common.R as commonR

class EmployeeFragment : BaseDataBindingFragment<FragmentEmployeeBinding, EmployeeViewModel>() {

    private var skeleton: Skeleton? = null

    private val adapter = EmployeePagingAdapter {
        requireContext().showSnackBar(
            binding.root, it.employee_name,
            Toast_Default
        )
    }.apply {
        withLoadStateHeaderAndFooter(
            header = BaseLoadStateAdapter { this.retry() },
            footer = BaseLoadStateAdapter { this.retry() }
        )
    }

    private val timerSearch = object : CountDownTimer(1000, 1000) {
        override fun onTick(millisUntilFinished: Long) = Unit

        override fun onFinish() {
            searchText()
        }
    }

    override fun onInitialization() {
        super.onInitialization()
//        parentAction.setCustomToolbar(commonR.menu.toolbar_default)
    }

    override fun onObserveAction() {
        viewModel.apply {
            observe(exampleList) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }

            adapter.addLoadStateListener { loadState ->
                binding.apply {
                    blanklayout.gone()
                    rcvExample.visible()
                    when (loadState.source.refresh) {
                        is LoadState.Loading -> {
                            if (adapter.itemCount < 1) skeleton?.showSkeleton()
                        }
                        is LoadState.Error -> {
                            if (skeleton?.isSkeleton() == true) skeleton?.showOriginal()
                            val throwable = (loadState.source.refresh as LoadState.Error).error
                            rcvExample.gone()
                            blanklayout.visible()
                            blanklayout.setType(
                                throwable.toThrowableCode(),
                                throwable.toThrowableMessage().asString(requireContext())
                            )
                            blanklayout.setOnClick(getString(commonR.string.retry)) {
                                adapter.retry()
                            }
                        }
                        is LoadState.NotLoading -> {
                            if (skeleton?.isSkeleton() == true) skeleton?.showOriginal()
                            if (loadState.source.refresh is LoadState.NotLoading &&
                                loadState.append.endOfPaginationReached &&
                                adapter.itemCount < 1
                            ) {
                                rcvExample.gone()
                                blanklayout.visible()
                                blanklayout.setType(HttpURLConnection.HTTP_NO_CONTENT)
                            }
                        }
                    }
                }
            }
            search.value = null
        }
    }

    override fun onReadyAction() {
        binding.apply {
            rcvExample.adapter = adapter
            skeleton = rcvExample.applySkeleton(R.layout.item_employee_recyclerview)
            skeleton?.showShimmer = true

            searchviewEmployee.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    timerSearch.cancel()
                    searchText()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    timerSearch.cancel()
                    timerSearch.start()
                    return true
                }
            })
            searchviewEmployee.setOnSearchClickListener {
                tvHintSearchview.gone()
            }
            searchviewEmployee.setOnCloseListener {
                viewModel.search.value = null
                tvHintSearchview.isVisible = false
                true
            }
        }
    }

    private fun searchText() {
        binding.apply {
            rcvExample.scrollToPosition(0)
            viewModel.search.value = searchviewEmployee.query.toString()
            searchviewEmployee.clearFocus()
        }
    }
}