package com.example.blapp2009

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.trimmedLength
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class LoginIntro : AppCompatActivity() {

    private lateinit var btnGetStarted: Button
    private lateinit var databaseReference: DatabaseReference

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

            //method to check if user is approved or not abd sending user to different activity accordingly, details is given below
            checkUserApprovedOrNot(auth.currentUser?.uid!!)

            Toast.makeText(this,"You are already logged in! ", Toast.LENGTH_SHORT).show()

            //directing to landing activity as user is already logged in
          /*  val intent= Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish()*/
        }
    }
    //method for checking user is really from 09 batch or not, initially while signUp userStatus is 'false' but if
    // I make userStatus 'true' manually from realtime database, user will see LandingAct then. Searching userStatus through userID
    private fun checkUserApprovedOrNot(currentUserId: String) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.child(currentUserId).get().addOnSuccessListener {
            if (it.exists()) {
               val userStatus = it.child("userStatus").value.toString()

                if (userStatus == "true") {

                    val intent = Intent(this, LandingActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent1 = Intent(this, CheckUserStatusActivity::class.java)
                    startActivity(intent1)
                    finish()
                }

            } else {
                Toast.makeText(this, "Failed to get user", Toast.LENGTH_SHORT).show()
            }
        }
    }
}