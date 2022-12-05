package com.example.blapp2009

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragFilterBlood : Fragment() {
    //recyclerview data from firebase database
    private lateinit var recyclerView: RecyclerView
    private lateinit var bloodNameArrayList: ArrayList<String>
    private lateinit var gridLayoutManager: GridLayoutManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_frag_filter_blood, container, false)

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
        recyclerView.adapter=FragFilterBloodRCVAdapter(bloodNameArrayList)

        return view
    }

}