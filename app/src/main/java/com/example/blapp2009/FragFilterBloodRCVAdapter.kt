package com.example.blapp2009

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class FragFilterBloodRCVAdapter(private val bloodGroupArrayList: ArrayList<String> ):
RecyclerView.Adapter<FragFilterBloodRCVAdapter.MyViewHolder>() {

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBloodGroup: TextView =itemView.findViewById(R.id.tvBloodGroupRCVFragFilter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_rcv_frag_filter_bloodgroup, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvBloodGroup.text=  bloodGroupArrayList[position]
        holder.tvBloodGroup.setOnClickListener {
            holder.tvBloodGroup.text=  bloodGroupArrayList[position]
        }
    }

    override fun getItemCount(): Int {
        return bloodGroupArrayList.size
    }


}