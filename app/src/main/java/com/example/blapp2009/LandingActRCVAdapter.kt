package com.example.blapp2009

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class LandingActRCVAdapter(private val userLists: ArrayList<User>) :
    RecyclerView.Adapter<LandingActRCVAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.tvNameContactListRCV)
        val bloodgroup: TextView = itemView.findViewById(R.id.tv_BloodGroup_ContactListRCV)
        val layout: LinearLayout = itemView.findViewById(R.id.linearLayoutContactListRCV)
        val call: CardView = itemView.findViewById(R.id.cardView_CallRCV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_rcv_bloodgroup_contactlist, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item= userLists[position]
        holder.name.text=item.name
        holder.bloodgroup.text=item.bloodgroup
        Log.e("adapter","name "+item.name)
        Log.e("adapter","blood " +item.bloodgroup)
    }

    override fun getItemCount(): Int {
        return userLists.size
    }
}