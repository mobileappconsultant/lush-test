package com.example.falcon9launches.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.example.falcon9launches.TestCoroutineRule
import com.example.falcon9launches.api.response.FalconResponse
import com.example.falcon9launches.api.response.Links
import com.example.falcon9launches.api.response.Patch
import com.example.falcon9launches.data.FalconLaunchDataStore
import com.example.falcon9launches.data.ResultWrapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


@ExperimentalCoroutinesApi
class FalconLaunchesViewModelTest {

    private val falconLaunchDataStore: FalconLaunchDataStore = mockk(relaxed = true)
    private lateinit var sut: FalconLaunchesViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        sut = FalconLaunchesViewModel(falconLaunchDataStore)
    }

    @Test
    fun `when getFalconLaunches is called it returns list of falcon launches`() {
        val response = listOf(
            FalconResponse(
                Links(
                    Patch("small", "large")
                ), "123", "23-09-2021", true, "name"
            )
        )

        coEvery {
            falconLaunchDataStore.getFalconLaunches()
        } returns ResultWrapper.Success(response)


        sut.getData()

        assertEquals(response.first().name, sut.falconLaunchList.value?.first()?.name)
    }

    @Test
    fun `when getFalconLaunches is called it returns an error`() {
        coEvery {
            falconLaunchDataStore.getFalconLaunches()
        } returns ResultWrapper.GenericError()

        sut.getData()

        assertEquals(InitialState.Error, sut.uiState.value)
    }

    fun tearDown() {
        Dispatchers.resetMain()
    }
}