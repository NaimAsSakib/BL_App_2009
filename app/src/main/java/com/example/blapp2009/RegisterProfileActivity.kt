package com.example.blapp2009

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.text.trimmedLength
import androidx.core.widget.doOnTextChanged
import com.example.blapp2009.databinding.ActivityRegisterProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterProfileBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var name: String

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

        binding.btnUpdate.setOnClickListener {
            val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            val userId= sharedPreference.getString("userid","defaultName")
            Log.e(" passed userid", "userid "+userId)

            name= binding.etRegisterName.text.toString()
            val location= binding.etRegisterLocation.text.toString()
            val bloodgroup:String = binding.etRegisterBloodGroup.text.toString()
            val occupation= binding.etRegisterOccupation.text.toString()
            val organization= binding.etRegisterOrganization.text.toString()
            val number1= binding.etRegisterMobile1.text.toString()
            val number2= binding.etRegisterMobile2.text.toString()

            databaseReference=FirebaseDatabase.getInstance().getReference("Users")
            val user=User(userId, name, bloodgroup, location, occupation, organization, number1, number2)
            if (userId != null) {
                databaseReference.child(userId).setValue(user).addOnSuccessListener {

                    binding.etRegisterName.text.clear()
                    binding.etRegisterBloodGroup.text.clear()
                    binding.etRegisterLocation.text.clear()
                    binding.etRegisterOccupation.text.clear()
                    binding.etRegisterOrganization.text.clear()
                    binding.etRegisterMobile1.text.clear()
                    binding.etRegisterMobile2.text.clear()

                    Toast.makeText(this, "Successfully saved to database", Toast.LENGTH_SHORT).show()

                    val intent= Intent(this, LandingActivity::class.java)
                    startActivity(intent)
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to save data to database", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}