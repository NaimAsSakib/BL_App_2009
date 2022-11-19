package com.example.blapp2009

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.trimmedLength
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: TextView

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etLoginEmail)
        etPassword = findViewById(R.id.etLoginPassword)
        btnLogin=findViewById(R.id.btnLogin)
        btnSignUp=findViewById(R.id.tv4_Login)

        firebaseAuth= FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }

        btnSignUp.setOnClickListener {
            val intent= Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(){
        val email:String = etEmail.text.toString()
        val password:String = etPassword.text.toString()

        if(email.trimmedLength() == 0 || password.trimmedLength()== 0 ){
            Toast.makeText(this,"Email/ Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        //code register user
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){

                    Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show()
                    //directing to landing activity for successful login
                    val intent= Intent(this, LandingActivity::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this,"Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }

    }
}