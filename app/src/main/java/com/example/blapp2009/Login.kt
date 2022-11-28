package com.example.blapp2009

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.trimmedLength
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: TextView

    private lateinit var firebaseAuth: FirebaseAuth  //firebase authentication
    private lateinit var userId: String
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userStatus: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etLoginEmail)
        etPassword = findViewById(R.id.etLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.tv4_Login)

        firebaseAuth = FirebaseAuth.getInstance()  //firebase authentication

        btnLogin.setOnClickListener {
            login()  //method is described bottom of this activity
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        val email: String = etEmail.text.toString()
        val password: String = etPassword.text.toString()

        if (email.trimmedLength() == 0 || password.isBlank()) {
            Toast.makeText(this, "Email/ Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        //code for logging user in
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {

                //getting userId from authentication and saving in shared preference
                userId = firebaseAuth.currentUser?.uid!!
                Log.e("uid login", "user id " + userId)

                val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                var editor = sharedPreference.edit()
                editor.putString("userid", userId)
                editor.commit()

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                //method for checking user is really from 09 batch or not, initially while signUp userStatus is 'false' but if
                // I make userStatus 'true' manually from realtime database, user will see LandingAct then.Searching userStatus through userID
                checkUserApprovedOrNot(userId)

            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //method for checking user is really from 09 batch or not, initially while signUp userStatus is 'false' but if
    // I make userStatus 'true' manually from realtime database, user will see LandingAct then. Searching userStatus through userID
    private fun checkUserApprovedOrNot(currentUserId: String) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.child(currentUserId).get().addOnSuccessListener {
            if (it.exists()) {
                userStatus = it.child("userStatus").value.toString()
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