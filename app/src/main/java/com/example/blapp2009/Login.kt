package com.example.blapp2009

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.trimmedLength
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class Login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: TextView

    private lateinit var btnForgotPassword: TextView

    private lateinit var firebaseAuth: FirebaseAuth  //firebase authentication
    private lateinit var userId: String
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userStatus: String

    private lateinit var loadingProgressBarDialog: LoadingProgressBarDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loadingProgressBarDialog= LoadingProgressBarDialog(this)

        etEmail = findViewById(R.id.etLoginEmail)
        etPassword = findViewById(R.id.etLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.tv4_Login)

        btnForgotPassword=findViewById(R.id.tv5ForgotPasswordLogin)

        firebaseAuth = FirebaseAuth.getInstance()  //firebase authentication

        btnLogin.setOnClickListener {

            //checking internet connection with ConnectivityManager
            val conMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = conMgr.activeNetworkInfo
            if (netInfo == null) {   //means internet is off in mobile

                Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show()

            }else{   //means internet is on in the device, so call data from server

                login()  //method is described bottom of this activity
            }
        }

        // code for showing alertdialog & sending password reset link to given Email to reset password
        btnForgotPassword.setOnClickListener {
            val builder= AlertDialog.Builder(this)
            val view= layoutInflater.inflate(R.layout.alertdialog_forgot_password, null)
            val userEmail= view.findViewById<EditText>(R.id.etAlertDialogEmail)

            builder.setView(view)
            val dialog=builder.create()

            view.findViewById<Button>(R.id.btnSendAlertDialog).setOnClickListener {

                compareEmail(userEmail)  //method is described below
                dialog.dismiss()
            }

            view.findViewById<Button>(R.id.btnCancelAlertDialog).setOnClickListener {
                dialog.dismiss()
            }
            if (dialog.window != null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()

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
        loadingProgressBarDialog.startProgressBarLoading()  //progressbar start

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {

                //getting userId from authentication and saving in shared preference
                userId = firebaseAuth.currentUser?.uid!!

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                //saving logged in userId in shared pref for future usage in RegisterActivity
               /* val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                var editor = sharedPreference.edit()
                editor.putString("userid", userId)
                editor.commit()*/

                //method for checking user is really from 09 batch or not, initially while signUp userStatus is 'false' but if
                // I make userStatus 'true' manually from realtime database, user will see LandingAct then.Searching userStatus through userID
                checkUserApprovedOrNot(userId)

                loadingProgressBarDialog.dismissProgressBarDialog()

            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                loadingProgressBarDialog.dismissProgressBarDialog()
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

    //method for showing alertdialog & sending password reset link to given Email to reset password
    private fun compareEmail(email: EditText){
        if (email.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "Check your email inbox & spam folder", Toast.LENGTH_SHORT).show()
            }
        }
    }
}