package com.example.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.networkModels.request.CityDetailRequest
import com.example.weatherapp.data.networkModels.response.CityDetailResponse
import com.example.weatherapp.data.repository.MainRepository
import com.example.weatherapp.db.History
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.launch

class CityDetailViewModel(private val mainRepository: MainRepository):ViewModel() {


    private val _cityDetail = MutableLiveData<Resource<CityDetailResponse>>()
    fun cityDetail(): LiveData<Resource<CityDetailResponse>> {
        return _cityDetail
    }

    fun getCityDetail(request: CityDetailRequest) {
        viewModelScope.launch {
            _cityDetail.postValue(Resource.loading(data = null))
            try {
                val response = mainRepository.getCityDetail(request)

                _cityDetail.postValue(Resource.success(data = response))
            } catch (exception: Exception) {
                _cityDetail.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!$exception"
                    )
                )
            }
        }
    }

    private val _addHistory = MutableLiveData<Resource<Boolean>>()
    fun addHistory(): LiveData<Resource<Boolean>> {
        return _addHistory
    }

    fun addHistoryToDB(history: History) {
        viewModelScope.launch {
            try {
                val response = mainRepository.saveHistoryToDB(history)

                _addHistory.postValue(Resource.success(true))
            } catch (exception: Exception) {
                _addHistory.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!$exception"
                    )
                )
            }
        }
    }
}