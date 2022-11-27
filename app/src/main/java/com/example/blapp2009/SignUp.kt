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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPass: EditText
    private lateinit var etName: EditText
    private lateinit var btnApply: Button
    private lateinit var btnForLoginAct: TextView

    lateinit var firebaseAuth: FirebaseAuth  //firebase authentication
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etEmail = findViewById(R.id.etSignUpEmail)
        etPassword = findViewById(R.id.etSignUpPassword)
        etConfirmPass = findViewById(R.id.etSignUpConfirmPassword)
        etName=findViewById(R.id.etSignUpName)
        btnApply=findViewById(R.id.btnSignUp)
        btnForLoginAct=findViewById(R.id.tv3_SignUp)

        firebaseAuth= FirebaseAuth.getInstance()  //firebase authentication

        btnApply.setOnClickListener {
            signUpUser()  //method described bottom of this activity
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
        val name:String = etName.text.toString()

        //checking for blank field
        if(email.trimmedLength() == 0 || password.trimmedLength()== 0 || confirmPassword.trimmedLength() == 0 || name.isBlank()){
            Toast.makeText(this,"Email & Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        //matching password with confirm password
        if(password != confirmPassword){
            Toast.makeText(this,"Password didn't match with confirm password", Toast.LENGTH_SHORT).show()
            return
        }

        //code for registering user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){

                    val location=""
                    val bloodgroup= "N/A"
                    val occupation= ""
                    val organization= ""
                    val number1= ""
                    val number2= ""

                    val userId= firebaseAuth.currentUser?.uid!!
                    Log.e("uid sign up","user id "+userId)

                    databaseReference= FirebaseDatabase.getInstance().getReference("Users")
                    val user=User(userId, name, bloodgroup, location, occupation, organization, number1, number2)
                    databaseReference.child(userId).setValue(user).addOnSuccessListener {

                    }

                    Toast.makeText(this,"Sign Up Successful", Toast.LENGTH_SHORT).show()
                    //directing to landing activity for successful login
                    //val intent= Intent(this, LandingActivity::class.java)
                    //directing to RegisterProfile activity for successful login
                    val intent= Intent(this, Login::class.java)
                    intent.putExtra("userid",userId)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this,"Error creating user", Toast.LENGTH_SHORT).show()
                }
            }

    }
}