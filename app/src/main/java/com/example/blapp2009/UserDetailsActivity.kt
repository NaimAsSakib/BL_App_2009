package com.example.blapp2009

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.blapp2009.databinding.ActivityRegisterProfileBinding
import com.example.blapp2009.databinding.ActivityUserDetailsBinding
import com.google.firebase.auth.FirebaseAuth

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding


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