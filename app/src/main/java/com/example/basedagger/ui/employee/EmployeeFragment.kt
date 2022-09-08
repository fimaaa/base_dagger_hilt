package com.example.basedagger.ui.employee

import android.os.CountDownTimer
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.example.basedagger.R
import com.example.basedagger.base.BaseBindingFragment
import com.example.basedagger.base.BaseLoadStateAdapter
import com.example.basedagger.databinding.FragmentEmployeeBinding
import com.example.basedagger.extension.*
import com.example.basedagger.ui.adapter.employee.EmployeePagingAdapter
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.appcompat.v7.coroutines.onClose
import java.net.HttpURLConnection

@AndroidEntryPoint
class EmployeeFragment : BaseBindingFragment<FragmentEmployeeBinding, EmployeeViewModel>(R.layout.fragment_employee), EmployeeNavigator {

    private var skeleton: Skeleton? = null

    private val viewModel: EmployeeViewModel by viewModels()

    override fun getViewModels() = viewModel

    private val adapter = EmployeePagingAdapter {
        requireContext().showSnackBar(binding.root, it.employee_name, Toast_Default)
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
                            blanklayout.setOnClick(getString(R.string.retry)) {
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
            searchviewEmployee.onClose {
                viewModel.search.value = null
                tvHintSearchview.gone()
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