package com.example.blapp2009

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.text.trimmedLength
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.blapp2009.databinding.ActivityRegisterProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class RegisterProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterProfileBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var name: String
    private lateinit var userId: String
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewBinding() functionality
       // setContentView(R.layout.activity_register_profile) //muted this for viewBinding() functionality
        binding= ActivityRegisterProfileBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

       /* //Checking whether user entered an name or not & controlling button visibility accordingly
        binding.etRegisterName.doOnTextChanged { text, start, before, count ->
            if (text.toString().trimmedLength()>0){
                binding.btnUpdate.visibility=View.VISIBLE
                name= binding.etRegisterName.text.toString()
            }else{
                binding.btnUpdate.visibility=View.INVISIBLE
            }
        }*/

        //getting userId from shared preference saved in Login act
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        userId= sharedPreference.getString("userid","defaultName").toString()
        Log.e(" passed userid", "userid "+userId)

        //method for fetching profile details with userId from realtime database
        loadSavedProfileDetails()

        binding.btnUpdate.setOnClickListener {

            uploadProfileDetails()
        }

    }

    //method for updating/uploading user profile details to realtime database
    private fun uploadProfileDetails(){

        //code for making every 1st letter of every words uppercase present in name for uploading in database
        val strName=binding.etRegisterName.text.toString()
        val words = strName.split(" ").toMutableList()
         name = ""
        for(word in words){
            name += word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } +" "
        }
        name = name.trim()


        val locationStr= binding.etRegisterLocation.text.toString()
        val wordsLocation = locationStr.split(" ").toMutableList()
        var location = ""
        for(word in wordsLocation){
            location += word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } +" "
        }
        location = location.trim()

        val occupationStr= binding.etRegisterOccupation.text.toString()
        val wordsOccupation = occupationStr.split(" ").toMutableList()
        var occupation = ""
        for(word in wordsOccupation){
            occupation += word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } +" "
        }
        occupation = occupation.trim()

        val organization= binding.etRegisterOrganization.text.toString()


        val bloodgroup:String = binding.etRegisterBloodGroup.text.toString().uppercase()
        val number1= binding.etRegisterMobile1.text.toString()
        val userStatus="true"
        val section="Not needed now"

        /*val intent = intent
        val userEmail= intent.getStringExtra("userEmailFromSignUp").toString()
        */

        databaseReference=FirebaseDatabase.getInstance().getReference("Users")

        //overriding those values in this particular userId got from shared pref from Login act
        val user=User(userId, userEmail, name, bloodgroup, location, occupation, organization, number1,userStatus,section)

        if (userId != null) {
            databaseReference.child(userId).setValue(user).addOnSuccessListener {

                //clearing all editText fields after update
                binding.etRegisterName.text.clear()
                binding.etRegisterBloodGroup.text.clear()
                binding.etRegisterLocation.text.clear()
                binding.etRegisterOccupation.text.clear()
                binding.etRegisterOrganization.text.clear()
                binding.etRegisterMobile1.text.clear()

                Toast.makeText(this, "Successfully saved to database", Toast.LENGTH_SHORT).show()

                val intent= Intent(this, LandingActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this, "Failed to save data to database", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //method for fetching profile details with userId from realtime database
    private fun loadSavedProfileDetails(){
        databaseReference= FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.child(userId).get().addOnSuccessListener {
            if (it.exists()){
                var savedName= it.child("name").value.toString()
                var savedLocation= it.child("location").value.toString()
                var savedBloodgroup= it.child("bloodgroup").value.toString()
                var savedOccupation= it.child("occupation").value.toString()
                var savedOrganization= it.child("organization").value.toString()
                var savedNumber1= it.child("number1").value.toString()
                userEmail= it.child("userEmail").value.toString()


                binding.etRegisterName.setText(savedName)
                binding.etRegisterBloodGroup.setText(savedBloodgroup)
                binding.etRegisterLocation.setText(savedLocation)
                binding.etRegisterOccupation.setText(savedOccupation)
                binding.etRegisterOrganization.setText(savedOrganization)
                binding.etRegisterMobile1.setText(savedNumber1)

            }
        }
    }
}