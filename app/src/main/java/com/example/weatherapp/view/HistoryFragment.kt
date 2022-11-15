package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSecondBinding
import com.example.weatherapp.db.History
import com.example.weatherapp.db.Repo
import com.example.weatherapp.util.Status
import com.example.weatherapp.view.adapter.HistoryAdapter
import com.example.weatherapp.view.base.BaseFragment
import com.example.weatherapp.viewModel.HistoryViewModel
import com.example.weatherapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_second.*
import org.koin.android.ext.android.inject


class HistoryFragment : BaseFragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var city: Repo
    private val historyViewModel by inject<HistoryViewModel>()
    private val binding get() = _binding!!
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        city = arguments?.getSerializable("city") as Repo

        binding.tvTitle.text = city.cityName + getString(R.string.hostrical)

        initViewModel()
        initAdapter()
        historyViewModel.getHistoryFromDB(city.id)

//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    private fun initAdapter() {
        historyAdapter = HistoryAdapter(
            requireContext(),
            object : RecyclerListener {
                override fun onItemClick(position: Int, direction: Int) {

                    var bundle = Bundle()
                    bundle.putSerializable("city", city)
                    findNavController().navigate(
                        R.id.action_SecondFragment_to_DetailCityFragment,
                        bundle
                    )


                }


            }

        )
    }

    private fun setHistory(data: List<History>) {
        if (data.isNotEmpty()) {
            rv_history.apply {
                layoutManager = LinearLayoutManager(context)
                (layoutManager as LinearLayoutManager).orientation = LinearLayoutManager.VERTICAL
                rv_history.layoutManager = layoutManager
                adapter = historyAdapter
            }
            historyAdapter.items = data.toMutableList()

        }
    }

    private fun initViewModel() {


        historyViewModel.getHistory().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        dismissLoading()
                        setHistory(it.data!!)

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