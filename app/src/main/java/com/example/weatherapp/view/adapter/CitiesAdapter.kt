package com.example.weatherapp.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.db.Repo
import com.example.weatherapp.view.RecyclerListener
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import kotlinx.android.synthetic.main.city_item.view.*

class CitiesAdapter(context: Context, listener: RecyclerListener) :
    GenericRecyclerViewAdapter<Repo, RecyclerListener, CitiesAdapter.BenefitsViewHolder>(context, listener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BenefitsViewHolder {
        return BenefitsViewHolder(inflate(R.layout.city_item, parent), listener)
    }


    inner class BenefitsViewHolder(itemView: View, private val listener: RecyclerListener) :
        BaseViewHolder<Repo, RecyclerListener>(itemView, listener), View.OnClickListener {

        init {
            itemView.setOnClickListener(View.OnClickListener {
                listener.onItemClick(adapterPosition, 1)


            })
            itemView.iv_info.setOnClickListener(View.OnClickListener {
                listener.onItemClick(adapterPosition, 0)

            })
        }

        override fun onBind(item: Repo) {

            itemView.tv_cityName.text = item.cityName


        }

        override fun onClick(view: View) {
        }
    }
}
