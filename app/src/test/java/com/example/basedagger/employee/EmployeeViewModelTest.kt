package com.example.basedagger.ui.employee

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.basedagger.data.repository.EmployeeRepository
import com.example.basedagger.repositories.FakeEmployeeRepositories
import com.example.basedagger.utill.MainCoroutineRule
import com.example.basedagger.utill.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotSame
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EmployeeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var employeeRepository: EmployeeRepository

    lateinit var viewModel: EmployeeViewModel

    @Before
    fun setUp() {
        employeeRepository = FakeEmployeeRepositories()
        viewModel = EmployeeViewModel(employeeRepository)
    }

    @Test
    fun testSearchChanged() {
        viewModel.search.value = ""
        val listBefore = viewModel.exampleList.getOrAwaitValueTest(20)
        viewModel.search.value = "TEst"
        val listNext = viewModel.exampleList.getOrAwaitValueTest(20)
        assertNotSame(listBefore, listNext)
    }
}