package com.example.falcon9launches.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.falcon9launches.data.FalconLaunchDataStore
import com.example.falcon9launches.data.ResultWrapper
import com.example.falcon9launches.models.FalconLaunchModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FalconLaunchesViewModel @Inject constructor(private val falconLaunchDataStore: FalconLaunchDataStore):ViewModel() {

    private val _falconLaunchList = MutableLiveData<List<FalconLaunchModel>>()
    val falconLaunchList = _falconLaunchList
    val uiState = MutableLiveData<InitialState>()
    val errorMessage = MutableLiveData<String>()

    private fun getFalconLaunches() {
        uiState.value = InitialState.Loading
        viewModelScope.launch {
            when (val response = falconLaunchDataStore.getFalconLaunches()){
                is ResultWrapper.Success ->{
                    val falconList = mutableListOf<FalconLaunchModel>()
                    uiState.value = InitialState.Success
                    response.value.let {
                        it.map { falconResponse ->
                            falconList.add(
                                FalconLaunchModel(
                                    badge_image = falconResponse.links?.patch?.small,
                                    id = falconResponse.id,
                                    dateUtc = falconResponse.dateUtc,
                                    success = falconResponse.success,
                                    name = falconResponse.name
                                )
                            )
                        }
                        _falconLaunchList.value = falconList
                    }
                }

                is ResultWrapper.GenericError -> {
                    errorMessage.value =" Unable to retrieve data at the moment"
                    uiState.value = InitialState.Error

                }

                is ResultWrapper.NetworkError -> {
                    errorMessage.value = " No internet connection"
                    uiState.value = InitialState.Error

                }
            }
        }

    }

    fun getData() = getFalconLaunches()

    init {
        getData()
    }
}

enum class InitialState {
    Normal,
    Loading,
    Error,
    Success
}