package com.example.blapp2009

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.blapp2009.databinding.ActivityRegisterProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_register_profile)
        binding= ActivityRegisterProfileBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        val uid= auth.currentUser?.uid


        databaseReference=FirebaseDatabase.getInstance().getReference("")

    }
}