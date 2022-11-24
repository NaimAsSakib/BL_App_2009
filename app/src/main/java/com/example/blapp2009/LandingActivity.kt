package com.example.blapp2009

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*

class LandingActivity : AppCompatActivity() {
    private lateinit var ivHamburger: ImageView
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var consLayoutEditProfile: ConstraintLayout
    private lateinit var consLayoutChangePassword: ConstraintLayout
    private lateinit var consLayoutSearch: ConstraintLayout
    private lateinit var consLayoutNotification: ConstraintLayout
    private lateinit var consLayoutLogout: ConstraintLayout

    //recyclerview data from firebase database
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         setContentView(R.layout.activity_landing)

        recyclerView=findViewById(R.id.rcvLandingAct)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList= arrayListOf<User>()
        getUserData()


        //Navigation Drawer purpose
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

    private fun getUserData(){
        databaseReference=FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){

                        val user=userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                        Log.e("Landing act"," name "+userArrayList.size)

                    }
                    recyclerView.adapter=LandingActRCVAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}