package com.example.blapp2009

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.*

class LandingActivity : AppCompatActivity() {
    private lateinit var ivHamburger: ImageView
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var userNameDrawerLayout:TextView  //for showing userName in navigation view

    private lateinit var consLayoutEditProfile: ConstraintLayout
    private lateinit var consLayoutChangePassword: ConstraintLayout
    private lateinit var consLayoutSearch: ConstraintLayout
    private lateinit var consLayoutNotification: ConstraintLayout
    private lateinit var consLayoutLogout: ConstraintLayout

    //recyclerview data from firebase database
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>

    private lateinit var search: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         setContentView(R.layout.activity_landing)

        //directing to filter activity
        search=findViewById(R.id.ivFilter)
        search.setOnClickListener {
            val intentFilter= Intent(this, FilterActivity::class.java)
            startActivity(intentFilter)
        }

        //recyclerView purpose
        recyclerView=findViewById(R.id.rcvLandingAct)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        //Fetching registered user data from firebase realtime database
        userArrayList= arrayListOf<User>()
        getUserData()  //method described bottom of the activity


        //Navigation Drawer purpose
        userNameDrawerLayout=findViewById(R.id.tvUserNameNavigationView)
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

        //getting passed userName from LoginActivity or RegisterProfileActivity to show in navigation view
        val intent=intent
        val passedUserNameFromLoginAct=intent.getStringExtra("userNameFromSignUpAct")
        val passedUserNameFromRegisterAct=intent.getStringExtra("EditedUserNameFromRegisterAct")

        //condition for checking normal userName from LoginAct or edited userName from RegisterProfileAct
        if (!passedUserNameFromLoginAct.isNullOrEmpty()){
            userNameDrawerLayout.text= passedUserNameFromLoginAct

        }else{
            userNameDrawerLayout.text= passedUserNameFromRegisterAct
        }

        consLayoutEditProfile.setOnClickListener {

            val intent= Intent(this, RegisterProfileActivity::class.java)
            startActivity(intent)
            finish()

            drawerLayout.closeDrawer(GravityCompat.START)
        }

        consLayoutChangePassword.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            openFragment(FragmentChangePassword())   //opening change password fragment

        }

        consLayoutSearch.setOnClickListener {
            val intentFilterAct= Intent(this, FilterActivity::class.java)
            startActivity(intentFilterAct)
            //finish()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        consLayoutNotification.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        consLayoutLogout.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            Firebase.auth.signOut()
            //clearing all saved shared pref value
            val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.clear()
            val intent= Intent(this, LoginIntro::class.java)
            startActivity(intent)
            finish()
        }

    }


    //method for fetching registered user data from firebase realtime database
    private fun getUserData(){
        databaseReference=FirebaseDatabase.getInstance().getReference("Users")


        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){

                        val user=userSnapshot.getValue(User::class.java)

                        if (user!= null){
                            //if condition to show only approved users, whose userStatus is 'true'
                            if (user.userStatus?.contains("true") == true){
                                userArrayList.add(user!!)
                            }
                        }


                    }
                    recyclerView.adapter=LandingActRCVAdapter(userArrayList)

                    //Log.e("Landing act"," arrayList size "+userArrayList.size)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onBackPressed() {
        //Navigation drawer open & close purpose
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    //method for opening fragments
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerLandingAct, fragment)
            .addToBackStack(null).commit()
    }
}