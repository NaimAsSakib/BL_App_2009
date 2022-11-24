package com.example.blapp2009

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.trimmedLength
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginIntro : AppCompatActivity() {

    private lateinit var btnGetStarted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        btnGetStarted=findViewById(R.id.btnGetStarted)

        //If user logging in for the first time
        btnGetStarted.setOnClickListener {
            val intent= Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        //Checking with firebase built in feature if user is already logged in or not!! , condition working like shared pref
        if (auth.currentUser!=null){
            Toast.makeText(this,"You are already logged in! ", Toast.LENGTH_SHORT).show()
            //directing to landing activity as user is already logged in
            val intent= Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}