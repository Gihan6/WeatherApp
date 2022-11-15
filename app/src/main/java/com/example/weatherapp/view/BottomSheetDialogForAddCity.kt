package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_add_city.*

class BottomSheetDialogForAddCity : BottomSheetDialogFragment() {

    private var fragmentView: View? = null
    private lateinit var migrateCallback: MigrateCallback


    companion object {
        fun newInstance(): BottomSheetDialogForAddCity? {
            val args = Bundle()
            val fragment = BottomSheetDialogForAddCity()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.bottom_sheet_add_city, container, false)
        return fragmentView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_add.setOnClickListener(View.OnClickListener {
            if (et_cityName.text.toString().isNotEmpty())
                migrateCallback.onConfirmClick(0, et_cityName.text.toString())
        })
//        btn_cancel.setOnClickListener(View.OnClickListener {
//            migrateCallback.onConfirmClick(1)
//        })
//
//        iv_askPermissionClose.setOnClickListener(View.OnClickListener {
//            migrateCallback.onConfirmClick(1)
//        })

    }

    fun setMigrateCallback(migrateCallback: MigrateCallback) {
        this@BottomSheetDialogForAddCity.migrateCallback = migrateCallback
    }


    interface MigrateCallback {
        fun onConfirmClick(position: Int, cityName: String)
    }
}

