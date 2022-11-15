package com.example.weatherapp.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.db.History
import com.example.weatherapp.view.RecyclerListener
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import kotlinx.android.synthetic.main.city_item.view.*
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryAdapter(context: Context, listener: RecyclerListener) :
    GenericRecyclerViewAdapter<History, RecyclerListener, HistoryAdapter.BenefitsViewHolder>(
        context,
        listener
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BenefitsViewHolder {
        return BenefitsViewHolder(inflate(R.layout.history_item, parent), listener)
    }


    inner class BenefitsViewHolder(itemView: View, private val listener: RecyclerListener) :
        BaseViewHolder<History, RecyclerListener>(itemView, listener), View.OnClickListener {

        init {

            itemView.iv_back.setOnClickListener(View.OnClickListener {
                listener.onItemClick(adapterPosition, 0)

            })
        }

        override fun onBind(item: History) {

            itemView.tv_time.text = item.time
            itemView.tv_weatherData.text = item.desc + " , " + item.temp


        }

        override fun onClick(view: View) {
        }
    }
}
