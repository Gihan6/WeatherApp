package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.networkModels.request.CityDetailRequest
import com.example.weatherapp.data.networkModels.response.CityDetailResponse
import com.example.weatherapp.databinding.FragmentDetailCityBinding
import com.example.weatherapp.db.History
import com.example.weatherapp.db.Repo
import com.example.weatherapp.util.Status
import com.example.weatherapp.view.base.BaseFragment
import com.example.weatherapp.viewModel.CityDetailViewModel
import com.example.weatherapp.viewModel.MainViewModel
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class DetailCityFragment : BaseFragment() {


    private var _binding: FragmentDetailCityBinding? = null
    private lateinit var city: Repo
    private val cityDetailViewModel by inject<CityDetailViewModel>()

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        city = arguments?.getSerializable("city") as Repo
        initViewModel()
        cityDetailViewModel.getCityDetail(
            CityDetailRequest(
                city.cityName,
                "f5cb0b965ea1564c50c6f1b74534d823"
            )
        )
        binding.tvTitle.text = city.cityName

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

        }

    }

    private fun inflateDataOnUi(response: CityDetailResponse) {
        Glide.with(requireContext())
            .load("https://openweathermap.org/img/w/" + response!!.weather[0].icon + ".png")
            .into(binding.ivWeather)
        binding.tvDateInfo.text = "Weather information for " + city.cityName + " received on " +
                longToDateString(response.dt!!.toLong())

        binding.tvDescription.text = "Description: " + response.weather[0].description
        binding.tvTemp.text =
            "Temp: " + kelvinToCelsiuses(response.main!!.temp!!.toFloat()).toInt().toString()

        binding.tvHumidity.text = "Humidity: " + response.main!!.humidity.toString() + "%"
        binding.tvWindSpeed.text = "WindSpeed: " + response.wind!!.speed.toString() + "Kmh"

        cityDetailViewModel.addHistoryToDB(
            History(
                cityId = city.id, desc = response.weather[0].description!!,
                temp = kelvinToCelsiuses(response.main!!.temp!!.toFloat()).toInt().toString(),
                time = getCurrentTime()
            )
        )
    }

    private fun initViewModel() {


        cityDetailViewModel.cityDetail().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissLoading()
                        inflateDataOnUi(it.data!!)
                    }
                    Status.ERROR -> {
                        dismissLoading()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }
                    Status.LOADING -> {
                        showLoading()
                    }
                }
            }
        })

        cityDetailViewModel.addHistory().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {

                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.errorMessage),
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    }
                    Status.LOADING -> {
                    }
                }
            }
        })


    }

    private fun longToDateString(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("MM.dd.yyyy HH:mm")
        return format.format(date)
    }

    private fun kelvinToCelsiuses(value: Float): Float {
        return value - 273.15f
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }
}