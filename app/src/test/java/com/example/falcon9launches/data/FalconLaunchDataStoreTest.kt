package com.example.falcon9launches.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.falcon9launches.TestCoroutineRule
import com.example.falcon9launches.api.response.FalconResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Retrofit
import org.junit.Assert.assertTrue


@ExperimentalCoroutinesApi
class FalconLaunchDataStoreTest {

    private lateinit var sut: FalconLaunchDataStore
    private val retrofit: Retrofit = mockk(relaxed = true)

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        sut = FalconLaunchDataStore(retrofit)
    }

    @Test
    fun `check that getFalconLaunches returns list of falcon launches`()  {

        var  falconList: List<FalconResponse>? = null

        coEvery {
            when( val response = sut.getFalconLaunches()){
                is ResultWrapper.Success -> {
                    falconList = response.value
                }
            }
        }

        falconList?.let { assertTrue(it.isNotEmpty()) }
    }

    @Test
    fun `check that getFalconLaunches returns error when there is a network error`()  {

        var  errorMessage: String? = null

        coEvery {
            when( val response = sut.getFalconLaunches()){
                is ResultWrapper.GenericError -> {
                    errorMessage = response.error?.errorDescription.toString()
                }
            }
        }

        assertTrue(errorMessage.isNullOrEmpty().not())
    }

    fun tearDown() {
        Dispatchers.resetMain()
    }
}