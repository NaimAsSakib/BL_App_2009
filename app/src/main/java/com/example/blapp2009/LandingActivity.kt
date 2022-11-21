package com.example.blapp2009

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class LandingActivity : AppCompatActivity() {
    private lateinit var ivHamburger: ImageView
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var consLayoutEditProfile: ConstraintLayout
    private lateinit var consLayoutChangePassword: ConstraintLayout
    private lateinit var consLayoutSearch: ConstraintLayout
    private lateinit var consLayoutNotification: ConstraintLayout
    private lateinit var consLayoutLogout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         setContentView(R.layout.activity_landing)


        consLayoutEditProfile=findViewById(R.id.consProfile)
        consLayoutChangePassword=findViewById(R.id.consPassword)
        consLayoutSearch=findViewById(R.id.consFilter)
        consLayoutNotification=findViewById(R.id.consNotification)
        consLayoutLogout=findViewById(R.id.consLogout)


        ivHamburger=findViewById(R.id.ivHamburger)
        drawerLayout=findViewById(R.id.drawerLayout)
        //for opening drawer layout
        ivHamburger.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


        consLayoutEditProfile.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        consLayoutChangePassword.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

        }

        consLayoutSearch.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

        }

        consLayoutNotification.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

        }

        consLayoutLogout.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

        }



    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}