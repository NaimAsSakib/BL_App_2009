package com.example.blapp2009

import android.app.Activity
import android.app.AlertDialog

class LoadingProgressBarDialog(private val activity: Activity) {

    // private val activity: Activity? = null
    private var alertDialog: AlertDialog? = null

    fun startProgressBarLoading() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.circular_progressbar_dialog, null))
        builder.setCancelable(true)

        alertDialog = builder.create()
        alertDialog!!.show()
    }
    fun dismissProgressBarDialog() {
        alertDialog!!.dismiss()
    }

}