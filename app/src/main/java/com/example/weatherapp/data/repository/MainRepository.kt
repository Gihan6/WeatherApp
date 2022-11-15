package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.api.ApiHelper
import com.example.weatherapp.data.networkModels.request.CityDetailRequest
import com.example.weatherapp.db.DataBase
import com.example.weatherapp.db.History
import com.example.weatherapp.db.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainRepository(private val apiHelper: ApiHelper) : KoinComponent {

    private val dataBase by inject<DataBase>()

    suspend fun getCityDetail(request: CityDetailRequest) =
        apiHelper.getCityDetail(request)

    suspend fun saveCityToDB(cityName: String) {
        try {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                dataBase.repoDao().insert(Repo(cityName = cityName))
            }
        } catch (e: Exception) {
            Log.e("dataBase", e.toString())
        }
    }
    suspend fun getAllCities(): List<Repo>? {
        return try {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                dataBase.repoDao().getCities()
            }
        } catch (e: Exception) {
            Log.e("dataBase", e.toString())
            return null
        }
    }

    suspend fun saveHistoryToDB(history: History) {
        try {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                dataBase.repoDao().insertHistory(history)
            }
        } catch (e: Exception) {
            Log.e("dataBase", e.toString())
        }
    }
    suspend fun getHistory(id:Int): List<History>? {
        return try {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                dataBase.repoDao().getHistory(id)
            }
        } catch (e: Exception) {
            Log.e("dataBase", e.toString())
            return null
        }
    }
}