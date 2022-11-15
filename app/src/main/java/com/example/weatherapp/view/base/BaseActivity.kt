package com.example.weatherapp.view.base

import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.util.ProgressDialog

open class BaseActivity : AppCompatActivity() {
    var mProgressDialog: ProgressDialog =  ProgressDialog()

    fun showLoading(){
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog()
            mProgressDialog.show(supportFragmentManager,"")
        }else{
            mProgressDialog.show(supportFragmentManager,"")
        }
    }
    fun dismissLoading(){
        if (mProgressDialog != null && mProgressDialog.isVisible)
            mProgressDialog.dismiss()
    }


}