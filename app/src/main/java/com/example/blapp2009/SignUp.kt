package com.example.blapp2009

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.trimmedLength
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPass: EditText
    private lateinit var btnApply: Button
    private lateinit var btnForLoginAct: TextView

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etEmail = findViewById(R.id.etSignUpEmail)
        etPassword = findViewById(R.id.etSignUpPassword)
        etConfirmPass = findViewById(R.id.etSignUpConfirmPassword)
        btnApply=findViewById(R.id.btnSignUp)
        btnForLoginAct=findViewById(R.id.tv3_SignUp)

        firebaseAuth= FirebaseAuth.getInstance()

        btnApply.setOnClickListener {
            signUpUser()
        }

        btnForLoginAct.setOnClickListener {
            val intent= Intent(this, Login::class.java)
            startActivity(intent)
        }

    }

    private fun signUpUser(){
        val email:String = etEmail.text.toString()
        val password:String = etPassword.text.toString()
        val confirmPassword:String = etConfirmPass.text.toString()

        if(email.trimmedLength() == 0 || password.trimmedLength()== 0 || confirmPassword.trimmedLength() == 0){
            Toast.makeText(this,"Email & Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if(password != confirmPassword){
            Toast.makeText(this,"Password didn't match with confirm password", Toast.LENGTH_SHORT).show()
            return
        }

        //code register user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show()
                    //directing to landing activity for successful login
                    //val intent= Intent(this, LandingActivity::class.java)
                    val intent= Intent(this, RegisterProfileActivity::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this,"Error creating user", Toast.LENGTH_SHORT).show()
                }
            }

    }
}