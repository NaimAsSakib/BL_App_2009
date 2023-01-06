package com.example.blapp2009

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CheckUserStatusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_user_status)

    }

    override fun onBackPressed() {
        val intent1 = Intent(this, Login::class.java)
        startActivity(intent1)
        finish()
    }
}