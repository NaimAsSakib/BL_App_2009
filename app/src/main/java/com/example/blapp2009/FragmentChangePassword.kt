package com.example.blapp2009

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class FragmentChangePassword : Fragment() {
    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnApply: Button
    private lateinit var btnCancel: Button

    private lateinit var auth:FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_change_password, container, false)

        etCurrentPassword=view.findViewById(R.id.etChangePassCurrentPass)
        etNewPassword=view.findViewById(R.id.etChangePassNewPass)
        etConfirmPassword=view.findViewById(R.id.etChangePassConfirmPass)
        btnApply=view.findViewById(R.id.btnChangePassApply)
        btnCancel=view.findViewById(R.id.btnChangePassCancel)

        auth= FirebaseAuth.getInstance()

        btnApply.setOnClickListener {
            changePassword()
        }

        btnCancel.setOnClickListener {
            activity?.onBackPressed()
        }


        return view
    }

    //method for resetting/changing new password

    private fun changePassword(){
        if (etCurrentPassword.text.isNotEmpty() && etNewPassword.text.isNotEmpty() && etConfirmPassword.text.isNotEmpty()){

            if (etNewPassword.text.toString() == etConfirmPassword.text.toString()){

                val user: FirebaseUser? =auth.currentUser
                if (user!=null && user.email !=null){
                    val credential: AuthCredential = EmailAuthProvider
                        .getCredential(user.email!!, etCurrentPassword.text.toString())

                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(context,"re authentication successful", Toast.LENGTH_SHORT).show()

                                user.updatePassword(etNewPassword.text.toString())
                                    .addOnCompleteListener {
                                        if(it.isSuccessful){
                                            Toast.makeText(context,"Password changed successfully", Toast.LENGTH_SHORT).show()

                                            auth.signOut()

                                            val intent = Intent(context, Login::class.java)
                                            startActivity(intent)

                                            activity?.finish()
                                        }
                                    }

                            }else{
                                Toast.makeText(context,"re authentication failed", Toast.LENGTH_SHORT).show()
                            }
                        }

                }else{
                    val intent = Intent(context, Login::class.java)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(context,"Password didn't match", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context,"Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }

}