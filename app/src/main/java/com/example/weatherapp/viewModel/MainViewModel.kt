package com.example.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.networkModels.request.CityDetailRequest
import com.example.weatherapp.data.networkModels.response.CityDetailResponse
import com.example.weatherapp.data.repository.MainRepository
import com.example.weatherapp.db.History
import com.example.weatherapp.db.Repo
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _addCity = MutableLiveData<Resource<Boolean>>()
    fun addCity(): LiveData<Resource<Boolean>> {
        return _addCity
    }

    fun addCityToDB(cityName: String) {
        viewModelScope.launch {
            try {
                val response = mainRepository.saveCityToDB(cityName)

                _addCity.postValue(Resource.success(true))
            } catch (exception: Exception) {
                _addCity.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!$exception"
                    )
                )
            }
        }
    }


    private val _getAllCities = MutableLiveData<Resource<List<Repo>>>()
    fun getAllCities(): LiveData<Resource<List<Repo>>> {
        return _getAllCities
    }

    fun getAllCitiesFromDB() {
        viewModelScope.launch {
            try {
                val response = mainRepository.getAllCities()
                if (response.isNullOrEmpty()) {

                    _getAllCities.postValue(
                        Resource.error(
                            data = null,
                            message = "Error in database"
                        )
                    )
                } else
                    _getAllCities.postValue(Resource.success(response))


            } catch (exception: Exception) {
                _getAllCities.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!$exception"
                    )
                )
            }
        }
    }





}