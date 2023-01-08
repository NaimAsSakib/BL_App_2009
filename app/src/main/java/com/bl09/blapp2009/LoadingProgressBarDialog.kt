package com.bl09.blapp2009

import android.app.Activity
import android.app.AlertDialog
import com.bl09.blapp2009.R

class LoadingProgressBarDialog(private val activity: Activity) {

    private var alertDialog: AlertDialog? = null

    fun startProgressBarLoading() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.circular_progressbar_dialog, null))
        //builder.setCancelable(true)  //'true' means if user touch screen while loading, loader disappears
        builder.setCancelable(false)   //'false' means if user touch screen while loading, loader continue loading

        alertDialog = builder.create()
        alertDialog!!.show()
    }
    fun dismissProgressBarDialog() {
        alertDialog!!.dismiss()
    }

}
