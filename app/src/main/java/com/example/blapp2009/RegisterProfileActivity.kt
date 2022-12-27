package com.example.blapp2009

import android.app.Activity
import android.app.VoiceInteractor
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.trimmedLength
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import com.bumptech.glide.Glide
import com.example.blapp2009.databinding.ActivityRegisterProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*

class RegisterProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterProfileBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var name: String
    private lateinit var userId: String
    private lateinit var userEmail: String
    private lateinit var imageUrl: String

    private lateinit var firebaseAuth: FirebaseAuth  //firebase authentication for getting current userId

    //below 4 lines are for profile image upload purpose
    private var firebaseStorage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var PICK_IMAGE_REQUEST = 12
    private var imagePath: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewBinding() functionality
        // setContentView(R.layout.activity_register_profile) //muted this for viewBinding() functionality
        binding = ActivityRegisterProfileBinding.inflate(LayoutInflater.from(this))
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

        /*  //getting userId from shared preference saved in Login act
          val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
          userId= sharedPreference.getString("userid","defaultName").toString()*/

        //getting current user Id from firebase authentication to load data through this userId
        firebaseAuth = FirebaseAuth.getInstance()
        userId = firebaseAuth.currentUser?.uid!!
        Log.e(" passed userid", "userid " + userId)

        //method for fetching profile details with userId from realtime database
        loadSavedProfileDetails()

        //for uploading image to firebase storage
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        binding.ivProfile.setOnClickListener {

            fileChooser()
        }

        binding.btnUpdate.setOnClickListener {

            uploadProfileDetails()
        }

    }

    //method for updating/uploading user profile details to realtime database
    private fun uploadProfileDetails() {

        //code for making every 1st letter of every words uppercase present in 'name' for uploading in database
        val strName = binding.etRegisterName.text.toString()
        val words = strName.split(" ").toMutableList()
        name = ""
        for (word in words) {
            name += word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + " "
        }
        name = name.trim()

        //code for making every 1st letter of every words uppercase present in 'location' for uploading in database
        val locationStr = binding.etRegisterLocation.text.toString()
        val wordsLocation = locationStr.split(" ").toMutableList()
        var location = ""
        for (word in wordsLocation) {
            location += word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + " "
        }
        location = location.trim()

        //code for making every 1st letter of every words uppercase present in 'occupation' for uploading in database
        val occupationStr = binding.etRegisterOccupation.text.toString()
        val wordsOccupation = occupationStr.split(" ").toMutableList()
        var occupation = ""
        for (word in wordsOccupation) {
            occupation += word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + " "
        }
        occupation = occupation.trim()

        //code for making every 1st letter of every words uppercase present in 'organization' for uploading in database
        val organization = binding.etRegisterOrganization.text.toString()

        val bloodgroup: String = binding.etRegisterBloodGroup.text.toString().uppercase()  //making bloodgroup uppercase
        val number1 = binding.etRegisterMobile1.text.toString()
        val userStatus = "true"
        val section = "Not needed now"

        //method to upload profile image to firebase storage, described below
        uploadImageToFirebaseStorage()

        //uploading all data & profile image url in realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        //overriding those values for current userId got from firebase
        val user = User(userId, userEmail, name, bloodgroup, location, occupation, organization, number1, userStatus, section, imageUrl )

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

                val intent = Intent(this, LandingActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this, "Failed to save data to database", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //method for fetching profile details with userId from realtime database
    private fun loadSavedProfileDetails() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.child(userId).get().addOnSuccessListener {
            if (it.exists()) {
                var savedName = it.child("name").value.toString()
                var savedLocation = it.child("location").value.toString()
                var savedBloodgroup = it.child("bloodgroup").value.toString()
                var savedOccupation = it.child("occupation").value.toString()
                var savedOrganization = it.child("organization").value.toString()
                var savedNumber1 = it.child("number1").value.toString()
                userEmail = it.child("userEmail").value.toString()


                binding.etRegisterName.setText(savedName)
                binding.etRegisterBloodGroup.setText(savedBloodgroup)
                binding.etRegisterLocation.setText(savedLocation)
                binding.etRegisterOccupation.setText(savedOccupation)
                binding.etRegisterOrganization.setText(savedOrganization)
                binding.etRegisterMobile1.setText(savedNumber1)

            }
        }
    }

    //method to choose image from user device, to select profile picture
    private fun fileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    //method for uploading selected profile image to firebase storage
    private fun uploadImageToFirebaseStorage() {
        if (imagePath != null) {
            val imageRef = storageReference!!.child("image")
                .child(firebaseAuth!!.uid!!).child(name)
            val uploadImage = imageRef.putFile(imagePath!!)  //problem here with imagePath

            uploadImage.addOnFailureListener {
                Toast.makeText(this, "Error occurred!!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No profile image selected", Toast.LENGTH_SHORT)
                .show()   //for handling null pointer error
        }

        //used if condition coz, when user doesn't select any image to upload, imagePath is null, so imageUrl will be null
        if (imagePath == null) {
            imageUrl = ""
        } else {
            imageUrl = imagePath.toString()
        }
    }

    //this method is also overridden for profile image selection & upload purpose
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imagePath = data.data

            Glide.with(this)
                .load(imagePath)
                .error(R.drawable.profile_image)
                .into(binding.ivProfile)
        }
    }
}