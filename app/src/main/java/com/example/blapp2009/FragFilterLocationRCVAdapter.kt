package com.example.blapp2009

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView

class FragFilterLocationRCVAdapter(private val userLists: ArrayList<String>) :
    RecyclerView.Adapter<FragFilterLocationRCVAdapter.MyViewHolder>() {

    private var rcvRowIndex = -1 //For changing CardView color when clicked & for selecting one at a time

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val radBtn: RadioButton = itemView.findViewById(R.id.radBtnLocationName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_rcv_frag_filter_location, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        //val item = userLists[position]
        //holder.radBtn.text = item.location
        holder.radBtn.text=userLists[position]


        holder.radBtn.setOnClickListener {
            //for making radioButton single clickable
            rcvRowIndex = position
            notifyDataSetChanged()
        }
        //for making radioButton single clickable
        if (rcvRowIndex == position) {
            holder.radBtn.isChecked
        } else {
            holder.radBtn.isChecked=false
        }

    }

    override fun getItemCount(): Int {
        return userLists.size
    }

}