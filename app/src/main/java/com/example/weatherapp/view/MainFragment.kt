package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFirstBinding
import com.example.weatherapp.db.Repo
import com.example.weatherapp.util.Status
import com.example.weatherapp.view.adapter.CitiesAdapter
import com.example.weatherapp.view.base.BaseFragment
import com.example.weatherapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.ext.android.inject


class MainFragment : BaseFragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private lateinit var citiesAdapter: CitiesAdapter
    private val mainViewModel by inject<MainViewModel>()
    private lateinit var citiesList: List<Repo>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        initAdapter()
        mainViewModel.getAllCitiesFromDB()


//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
        binding.ivAdd.setOnClickListener {
            openAddCityDialog()
        }
    }

    private fun openAddCityDialog() {
        val bottomSheetFragment = BottomSheetDialogForAddCity.newInstance()

        bottomSheetFragment?.setMigrateCallback(object :
            BottomSheetDialogForAddCity.MigrateCallback {
            override fun onConfirmClick(position: Int, cityName: String) {
                if (position == 0) {
                    mainViewModel.addCityToDB(cityName)
                    bottomSheetFragment.dismiss()

                } else {
                    bottomSheetFragment.dismiss()
                }


            }
        })
        bottomSheetFragment?.show(childFragmentManager, bottomSheetFragment.tag)


    }

    private fun initAdapter() {
        citiesAdapter = CitiesAdapter(
            requireContext(),
            object : RecyclerListener {
                override fun onItemClick(position: Int, direction: Int) {

                    if (direction == 0) {
                        var bundle = Bundle()
                        bundle.putSerializable("city", citiesList[position])
                        findNavController().navigate(
                            R.id.action_FirstFragment_to_SecondFragment,
                            bundle
                        )

                    }

                    if (direction == 1) {
                        var bundle = Bundle()
                        bundle.putSerializable("city", citiesList[position])
                        findNavController().navigate(
                            R.id.action_FirstFragment_to_DetailCityFragment,
                            bundle
                        )

                    }

                }


            }

        )
    }

    private fun setTerms(data: List<Repo>) {
        if (data.isNotEmpty()) {
            rv_cities.apply {
                layoutManager = LinearLayoutManager(context)
                (layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.VERTICAL
                rv_cities.layoutManager = layoutManager
                adapter = citiesAdapter
            }
            citiesAdapter.items = data.toMutableList()

        }
    }

    private fun initViewModel() {
        mainViewModel.addCity().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissLoading()
                        mainViewModel.getAllCitiesFromDB()
                    }
                    Status.ERROR -> {
                        dismissLoading()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.errorMessage),
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    }
                    Status.LOADING -> {
                        showLoading()
                    }
                }
            }
        })

        mainViewModel.getAllCities().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissLoading()
                        if (it.data!!.isEmpty()) {
                            binding.rvCities.visibility = View.GONE
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.rvCities.visibility = View.VISIBLE
                            binding.tvNoData.visibility = View.GONE
                            citiesList = it.data!!
                            setTerms(citiesList)
                        }
//                        Toast.makeText(requireContext(), "get City Success", Toast.LENGTH_SHORT).show()

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



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}