package com.bl09.blapp2009

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bl09.blapp2009.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_filter)
        binding= ActivityFilterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.ivBackFilterAct.setOnClickListener {

            onBackPressed()
        }
        //opening fragments
        binding.cardView1.setOnClickListener { openFragment(FragFilterName()) }

        binding.cardView2.setOnClickListener { openFragment(FragFilterBlood()) }

        binding.cardView3.setOnClickListener { openFragment(FragFilterLocation()) }


    }

    //method for opening fragments
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null).commit()
    }
}