package com.example.blapp2009

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.ArrayList


class FragFilterLocation : Fragment() {
    //recyclerview data from firebase database
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    //private lateinit var userArrayList: ArrayList<User>
    private lateinit var locationArrayList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_frag_filter_location, container, false)

        recyclerView=view.findViewById(R.id.rcvFragmentLocation)
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        //Fetching registered user data from firebase realtime database
        //userArrayList= arrayListOf<User>()

        locationArrayList= arrayListOf<String>()
        getUserLocation()   //method described below

        return view
    }

    //method for fetching registered user Location from firebase realtime database
    //mechanism:- Some users can have same location, I want to show the district name once. So, I kept all the locations in 'key' value of Hashmap
    //because hashmap allows one null key & doesn't allow repeated key :). Then I will add all hashmap keys to an empty ArrayList<>
    //& set that arrayList to adapter class
    private fun getUserLocation(){

        val hashMap = HashMap<String, Int>()  //hashmap

        databaseReference= FirebaseDatabase.getInstance().getReference("Users")


        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){

                        val user=userSnapshot.getValue(User::class.java)
                        //userArrayList.add(user!!)

                        //Keeping all locations from database in hashmap keys. 1 is random value
                            hashMap.put(user!!.location.toString(),1)

                    }
                   // recyclerView.adapter=FragFilterLocationRCVAdapter(userArrayList)

                    //Adding all hashmap keys into arraylist
                    locationArrayList.addAll(hashMap.keys)

                    //condition for eliminating "" or empty location from arrayList
                    if (hashMap.containsKey("")){
                        locationArrayList.remove("")
                    }

                    recyclerView.adapter=FragFilterLocationRCVAdapter(locationArrayList)

                    //Log.e("Landing act"," arrayList size "+userArrayList.size)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}