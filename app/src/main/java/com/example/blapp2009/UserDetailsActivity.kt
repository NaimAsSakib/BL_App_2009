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

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_user_details)
        binding= ActivityUserDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val intent = intent
        val name = intent.getStringExtra("userNameFromLandingAdapter").toString()
        val blood= intent.getStringExtra("userBloodGroupFromLandingAdapter").toString()
        val location= intent.getStringExtra("userLocationFromLandingAdapter").toString()
        val number1= intent.getStringExtra("userNumber1FromLandingAdapter").toString()
        val number2= intent.getStringExtra("userNumber2FromLandingAdapter").toString()
        val occupation= intent.getStringExtra("userOccupationFromLandingAdapter").toString()
        val organization= intent.getStringExtra("userOrganizationFromLandingAdapter").toString()

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

        binding.cardViewCallUserDetails.setOnClickListener {

            if (number1.trim().isNotEmpty()) {
                val intentCall = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number1"))
                startActivity(intentCall)
            } else {
                Toast.makeText(this, "Mobile number not given", Toast.LENGTH_SHORT).show()
            }
        }

    }
}