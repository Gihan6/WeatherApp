package com.example.weatherapp.view.base

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherapp.util.ProgressDialog

open class BaseFragment : Fragment() {
    fun showToast(context: Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    var mProgressDialog: ProgressDialog = ProgressDialog()

    fun showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog=ProgressDialog()
            if (!mProgressDialog.isVisible)
                mProgressDialog.show(
                    requireActivity().supportFragmentManager.beginTransaction(),
                    ""
                )
        } else {
            if (!mProgressDialog.isVisible)
                mProgressDialog.show(
                    requireActivity().supportFragmentManager.beginTransaction(),
                    ""
                )
        }
    }

    fun dismissLoading() {
        if (mProgressDialog != null&&mProgressDialog.isVisible)
            mProgressDialog.dismiss()
    }
}