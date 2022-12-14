package com.bl09.blapp2009

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FilteredOutputActivity : AppCompatActivity() {
    private lateinit var nameFromFragFilterName: String
    private lateinit var bloodNameFromFragFilBlood: String
    private lateinit var locationFromFragFilterLocation: String

    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>

    private lateinit var tvUserNotFound:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_output)

        tvUserNotFound=findViewById(R.id.tvFilteredOutputAct)

        //getting value from FragFilterName
        val intent = intent
         nameFromFragFilterName = intent.getStringExtra("inputNameFromFragFilterName").toString()
         bloodNameFromFragFilBlood= intent.getStringExtra("passedBloodNameFromFragFilterBlood").toString()
         locationFromFragFilterLocation= intent.getStringExtra("passedLocationFromFragFilterLocation").toString()
        Log.e("blood", "passed value  "+nameFromFragFilterName)
        Log.e("blood", "passed value  "+bloodNameFromFragFilBlood)
        Log.e("blood", "passed value  "+locationFromFragFilterLocation)

        //recyclerView purpose
        recyclerView=findViewById(R.id.rcvFilteredOutputAct)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        //Fetching registered user data from firebase realtime database
        userArrayList= arrayListOf<User>()
        getFilteredUserData()  //method described bottom of the activity

    }

    //method for fetching registered user data from firebase realtime database
    private fun getFilteredUserData(){
        databaseReference= FirebaseDatabase.getInstance().getReference("Users")


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){

                        val user=userSnapshot.getValue(User::class.java)

                        if (user != null) {
                            when {
                                user.name?.contains(nameFromFragFilterName) == true -> {
                                    userArrayList.add(user!!)

                                }
                                user.bloodgroup?.contains(bloodNameFromFragFilBlood) == true -> {
                                    userArrayList.add(user!!)

                                }
                                user.location?.contains(locationFromFragFilterLocation) == true -> {
                                    userArrayList.add(user!!)

                                }
                            }

                        }
                }
                    //checking if recyclerview is empty or not through userArrayList & setting adapter or showing Toast accordingly
                    if (userArrayList.isNotEmpty()){
                        recyclerView.adapter=LandingActRCVAdapter(userArrayList) //using the same LandingActRCVAdapter again here

                    }else{
                        //when user is not found
                        recyclerView.visibility=View.GONE
                        tvUserNotFound.visibility=View.VISIBLE
                    }

                    //Log.e("Landing act"," arrayList size "+userArrayList.size)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}