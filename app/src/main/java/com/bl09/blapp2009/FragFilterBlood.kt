package com.bl09.blapp2009

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragFilterBlood : Fragment() , ItemOnClickListener {
    //recyclerview data from firebase database
    private lateinit var recyclerView: RecyclerView
    private lateinit var bloodNameArrayList: ArrayList<String>
    private lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var passedBloodGroupForFilter: String
    private lateinit var btnSearch : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_frag_filter_blood, container, false)

        btnSearch=view.findViewById(R.id.btnFragFilterBlood)

        recyclerView=view.findViewById(R.id.rcvFragmentFilterBloodGroup)
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        bloodNameArrayList= arrayListOf<String>()
        bloodNameArrayList.add("A+")
        bloodNameArrayList.add("A-")
        bloodNameArrayList.add("B+")
        bloodNameArrayList.add("B-")
        bloodNameArrayList.add("AB+")
        bloodNameArrayList.add("AB-")
        bloodNameArrayList.add("O+")
        bloodNameArrayList.add("O-")

        gridLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter=FragFilterBloodRCVAdapter( bloodNameArrayList, this)



        return view
    }

    override fun onItemClicked(value: String?, name: String?) {
        //If there is value, button will appear, else it will disappear
        if (name.equals("selectedBloodGroup")){
            if (value != null) {
                passedBloodGroupForFilter=value
                //Log.e("selected value"," value "+passedBloodGroupForFilter)
                btnSearch.visibility=View.VISIBLE

                btnSearch.setOnClickListener {
                    val intent = Intent(context, FilteredOutputActivity::class.java)
                    intent.putExtra("passedBloodNameFromFragFilterBlood", passedBloodGroupForFilter)
                    startActivity(intent)
                }
            }


        }else if (name.equals("unselectedBloodGroup")){
            btnSearch.visibility=View.INVISIBLE
        }
    }

}