package com.example.blapp2009

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.blapp2009.databinding.ActivityRegisterProfileBinding
import com.example.blapp2009.databinding.ActivityUserDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding

    //for getting profile image from firebase storage
    private var firebaseAuth: FirebaseAuth?= null
    private var firebaseStorage: FirebaseStorage?=null
    private var storageReference: StorageReference?= null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_user_details)
        binding= ActivityUserDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.ivBackArrow.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish()
        }

        val intent = intent
        val name = intent.getStringExtra("userNameFromLandingAdapter").toString()
        val blood= intent.getStringExtra("userBloodGroupFromLandingAdapter").toString()
        val location= intent.getStringExtra("userLocationFromLandingAdapter").toString()
        val number1= intent.getStringExtra("userNumber1FromLandingAdapter").toString()
        val occupation= intent.getStringExtra("userOccupationFromLandingAdapter").toString()
        val organization= intent.getStringExtra("userOrganizationFromLandingAdapter").toString()

        val userEmail= intent.getStringExtra("userEmailFromLandingAdapter").toString()
        val profileImageUrl= intent.getStringExtra("profileImageUrlFromLandingAdapter").toString()
        val userId= intent.getStringExtra("userIdFromLandingAdapter").toString()
       // Log.e("image url","userid "+userId)


        binding.tvUserDetailsUserName.text=name

        if (blood.isEmpty()){
            binding.tvUserDetailsBloodGroupName.text="Not available"
        }else { binding.tvUserDetailsBloodGroupName.text=blood }
        if (location.isEmpty()){
            binding.tvUserDetailsLocationName.text="Not available"
        }else { binding.tvUserDetailsLocationName.text=location }

        if (number1.isEmpty()){
            binding.tvUserDetailsPhoneNumber.text="Not available"
        }else { binding.tvUserDetailsPhoneNumber.text=number1 }

        if (occupation.isEmpty()){
            binding.tvUserDetailsOccupationName.text="Not available"
        }else { binding.tvUserDetailsOccupationName.text=occupation }

        if (organization.isEmpty()){
            binding.tvUserDetailsOrganizationName.text="Not available"
        }else { binding.tvUserDetailsOrganizationName.text=organization }

        if (userEmail.isEmpty()){
            binding.tvUserDetailsEmailAddress.text="Not available"
        }else { binding.tvUserDetailsEmailAddress.text=userEmail }

        //condition if profileImageUrl is empty or not, empty means user didn't upload any profile photo, !empty means profile photo exist
        if(profileImageUrl.isEmpty()){
            Glide.with(this)
                .load(R.drawable.profile_image)
                .into(binding.ivProfileImageUserDetails)
            //Toast.makeText(this, "pic url empty", Toast.LENGTH_SHORT).show()

        }else{
            //load the previously saved profile photo by the reference of 'userId', from firebase Storage
            firebaseAuth= FirebaseAuth.getInstance()
            firebaseStorage= FirebaseStorage.getInstance()
            storageReference=firebaseStorage!!.reference

            storageReference!!.child("image")
                .child(userId).child(name)
                .downloadUrl.addOnSuccessListener {

                    Glide.with(this)
                        .load(it)
                        .error(R.drawable.profile_image)
                        .into(binding.ivProfileImageUserDetails)
                }

        }


        //calling mechanism
        binding.cardViewCallUserDetails.setOnClickListener {

            if (number1.trim().isNotEmpty()) {
                val intentCall = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number1"))
                startActivity(intentCall)
            } else {
                Toast.makeText(this, "Mobile number not given", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onBackPressed() {
       super.onBackPressed()
        /*val intent = Intent(this, LandingActivity::class.java)
        startActivity(intent)
        finish()*/
    }
}