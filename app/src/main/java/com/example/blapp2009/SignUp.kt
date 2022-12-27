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
import java.util.*

class SignUp : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPass: EditText
    private lateinit var etName: EditText
    private lateinit var etSection: EditText
    private lateinit var btnApply: Button
    private lateinit var btnForLoginAct: TextView

    lateinit var firebaseAuth: FirebaseAuth  //firebase authentication
    private lateinit var databaseReference: DatabaseReference
    private lateinit var name:String //global variable
    private lateinit var section:String //global variable
    private lateinit var userEmail: String //global variable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etEmail = findViewById(R.id.etSignUpEmail)
        etPassword = findViewById(R.id.etSignUpPassword)
        etConfirmPass = findViewById(R.id.etSignUpConfirmPassword)
        etName=findViewById(R.id.etSignUpName)
        etSection=findViewById(R.id.etSignUpSection)
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
       // val email:String = etEmail.text.toString()
        userEmail = etEmail.text.toString()

        val intent=Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("userEmailFromSignUp",userEmail)

        val password:String = etPassword.text.toString()
        val confirmPassword:String = etConfirmPass.text.toString()
        name = etName.text.toString()
        section = etSection.text.toString()

        //code for making every 1st letter of every words uppercase present in name for uploading in database
        val words = name.split(" ").toMutableList()
        name = ""
        for(word in words){
            name += word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } +" "
        }
        name = name.trim()


        //checking for blank field
        if(userEmail.trimmedLength() == 0 || password.trimmedLength()== 0 || confirmPassword.trimmedLength() == 0 || name.isBlank()|| section.isBlank()){
            Toast.makeText(this," please fill up all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        //matching password with confirm password
        if(password != confirmPassword){
            Toast.makeText(this,"Password didn't match with confirm password", Toast.LENGTH_SHORT).show()
            return
        }

        //code for registering user
        firebaseAuth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){

                    //method for uploading creating a user in realtime database with a reference of userID got from authentication
                    uploadDataToRealtimeDB()

                    Toast.makeText(this,"Sign Up Successful", Toast.LENGTH_SHORT).show()
                    //directing to Login activity for successful login
                    val intent= Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this,"Email already exists or failed to connect", Toast.LENGTH_SHORT).show()
                }
            }

    }

    //method for uploading creating a user in realtime database with a reference of userID got from authentication
    private fun uploadDataToRealtimeDB(){
        val location=""
        val bloodgroup= "N/A"
        val occupation= ""
        val organization= ""
        val number1= ""
        val userStatus="false"  //initially it is false, pending for verification

        val userId= firebaseAuth.currentUser?.uid!! //getting userId from firebase authentication
        Log.e("uid sign up","user id "+userId)

        databaseReference= FirebaseDatabase.getInstance().getReference("Users")

        val user=User(userId, userEmail, name, bloodgroup, location, occupation, organization, number1, userStatus, section,"")

        //userID er reference a shob details save hobe
        databaseReference.child(userId).setValue(user).addOnSuccessListener {
            Toast.makeText(this, "user data uploaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {

        }
    }
}