package com.example.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.MainRepository
import com.example.weatherapp.db.History
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.launch

class HistoryViewModel(private val mainRepository: MainRepository):ViewModel() {
    private val _getHistory= MutableLiveData<Resource<List<History>>>()
    fun getHistory(): LiveData<Resource<List<History>>> {
        return _getHistory
    }

    fun getHistoryFromDB(id: Int) {
        viewModelScope.launch {
            try {
                val response = mainRepository.getHistory(id)
                if (response.isNullOrEmpty()) {

                    _getHistory.postValue(
                        Resource.error(
                            data = null,
                            message = "Error in database"
                        )
                    )
                } else
                    _getHistory.postValue(Resource.success(response))


            } catch (exception: Exception) {
                _getHistory.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!$exception"
                    )
                )
            }
        }
    }
}