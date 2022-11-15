package com.example.weatherapp.di


import com.example.weatherapp.data.api.ApiHelper
import com.example.weatherapp.data.api.RetrofitBuilder
import com.example.weatherapp.data.repository.MainRepository
import com.example.weatherapp.db.DataBase
import com.example.weatherapp.viewModel.CityDetailViewModel
import com.example.weatherapp.viewModel.HistoryViewModel
import com.example.weatherapp.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { DataBase.getInstance(get()) }
    factory { RetrofitBuilder.apiService }
    factory { ApiHelper(get()) }
    single { MainRepository(get()) }

}
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { CityDetailViewModel(get()) }

}
