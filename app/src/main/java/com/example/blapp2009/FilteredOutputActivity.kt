package com.example.blapp2009

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class FilteredOutputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_output)

        //getting value from FragFilterName
        val intent = intent
        val nameFromFragFilterName = intent.getStringExtra("inputNameFromFragFilterName")
        val bloodNameFromFragFilBlood= intent.getStringExtra("passedBloodNameFromFragFilterBlood")
        Log.e("blood", "passed value  "+bloodNameFromFragFilBlood)
    }
}