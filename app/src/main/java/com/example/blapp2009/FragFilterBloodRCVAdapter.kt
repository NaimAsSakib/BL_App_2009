package com.example.blapp2009

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FragFilterBloodRCVAdapter(private val bloodGroupArrayList: ArrayList<String>, private val onClickListener: ItemOnClickListener):
RecyclerView.Adapter<FragFilterBloodRCVAdapter.MyViewHolder>() {


    private var hashMap = HashMap<Int, String>() //for selecting & unselecting mechanism


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


        //condition for single selection & dis-selection of the bloodGroup from, here to the end of  holder.tvBloodGroup.setOnClickListener
        if (hashMap.containsKey(position)) {
            holder.tvBloodGroup.setBackgroundResource(R.drawable.circle_shape_blood_red)
            holder.tvBloodGroup.setTextColor(Color.WHITE)
        } else {
            holder.tvBloodGroup.setBackgroundResource(R.drawable.circle_shape_blood_normal)
            holder.tvBloodGroup.setTextColor(Color.parseColor("#536DFE"))
        }

        holder.tvBloodGroup.setOnClickListener {
            //when user select a textview
            if (!hashMap.containsKey(position)) {
                hashMap.clear()
                hashMap.put(position,holder.tvBloodGroup.toString())

                var selectedValue= holder.tvBloodGroup.text.toString()
                onClickListener.onItemClicked(selectedValue, "selectedBloodGroup")

            } else { //when user unselect a radiobutton
                hashMap.remove(position)
                onClickListener.onItemClicked("","unselectedBloodGroup")
            }
            notifyDataSetChanged()

        }

    }

    override fun getItemCount(): Int {
        return bloodGroupArrayList.size
    }


}