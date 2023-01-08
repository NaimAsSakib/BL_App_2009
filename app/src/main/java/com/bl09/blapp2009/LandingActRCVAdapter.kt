package com.bl09.blapp2009

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bl09.blapp2009.R
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

        val userName=item.name
        val userBloodGroup=item.bloodgroup
        val location= item.location
        val number1= item.number1
        val occupation=item.occupation
        val organization=item.organization
        val userEmail= item.userEmail
        val profileImageUrl= item.imageUrl
        val userId=item.userId

        val context= holder.bloodgroup.context  //for bringing context in adapter class

        holder.layout.setOnClickListener {

            val intent=Intent(context, UserDetailsActivity::class.java)
            intent.putExtra("userNameFromLandingAdapter",userName)
            intent.putExtra("userBloodGroupFromLandingAdapter",userBloodGroup)
            intent.putExtra("userLocationFromLandingAdapter",location)
            intent.putExtra("userNumber1FromLandingAdapter",number1)
            intent.putExtra("userOccupationFromLandingAdapter",occupation)
            intent.putExtra("userOrganizationFromLandingAdapter",organization)
            intent.putExtra("userEmailFromLandingAdapter",userEmail)
            intent.putExtra("profileImageUrlFromLandingAdapter",profileImageUrl)
            intent.putExtra("userIdFromLandingAdapter",userId)

            context.startActivity(intent)
        }

        //call functionality
        holder.call.setOnClickListener {
            if (number1 != null){
                if (number1.trim().isNotEmpty()){
                    val intentCall = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number1"))
                    context.startActivity(intentCall)
                }else{
                    Toast.makeText(context,"Mobile number not given",Toast.LENGTH_SHORT).show()
                }
            }
        }



    }

    override fun getItemCount(): Int {
        return userLists.size
    }
}