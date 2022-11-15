package com.example.weatherapp.view

import com.leodroidcoder.genericadapter.BaseRecyclerListener
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener

interface RecyclerListener : BaseRecyclerListener {
    fun onItemClick(position: Int, direction: Int)

}