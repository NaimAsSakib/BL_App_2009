package com.example.blapp2009

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.blapp2009.databinding.ActivityLargeProfilePhotoBinding
import com.example.blapp2009.databinding.ActivityUserDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.net.URI

class LargeProfilePhotoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLargeProfilePhotoBinding

    private var firebaseAuth: FirebaseAuth?= null
    private var firebaseStorage: FirebaseStorage?=null
    private var storageReference: StorageReference?= null

    private lateinit var progressBarDialog: LoadingProgressBarDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_large_profile_photo)
        binding= ActivityLargeProfilePhotoBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        progressBarDialog= LoadingProgressBarDialog(this)

        val intent= intent
        val passedUserId= intent.getStringExtra("userIdFromUserDetailsAct").toString()

        //load the previously saved profile photo by the reference of 'userId', from firebase Storage
        progressBarDialog.startProgressBarLoading()

        firebaseAuth= FirebaseAuth.getInstance()
        firebaseStorage= FirebaseStorage.getInstance()
        storageReference=firebaseStorage!!.reference


        storageReference!!.child("image")
            .child(passedUserId)
            .downloadUrl.addOnSuccessListener {

                Glide.with(this)
                    .load(it)
                    .error(R.drawable.profile_image)
                    .into(binding.ivLargeImage)

                progressBarDialog.dismissProgressBarDialog()
            }
    }
}