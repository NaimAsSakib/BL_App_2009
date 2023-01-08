package com.bl09.blapp2009

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.bl09.blapp2009.R

class FragFilterLocationRCVAdapter(private val userLocationLists: ArrayList<String>, private val onClickListener: ItemOnClickListener) :
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

        holder.radBtn.text=userLocationLists[position]


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
        if (holder.radBtn.isChecked){
            onClickListener.onItemClicked(holder.radBtn.text.toString(),"selectedLocation")
        }else{
            onClickListener.onItemClicked("","unSelectedLocation")

        }

    }

    override fun getItemCount(): Int {
        return userLocationLists.size
    }

}