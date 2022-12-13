package com.example.blapp2009

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragFilterName : Fragment() {

    private lateinit var etName: EditText
    private lateinit var btnSearch: Button
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_frag_filter_name, container, false)

        etName = view.findViewById(R.id.etFragFilterName)
        btnSearch = view.findViewById(R.id.btnFragName)


        btnSearch.setOnClickListener {

            name = etName.text.toString()

            //checking whether name field is empty or not
            if (name.trim().isNotEmpty()) {
                val intent = Intent(context, FilteredOutputActivity::class.java)
                intent.putExtra("inputNameFromFragFilterName", name)
                startActivity(intent)
            } else {
                Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show()
            }

        }


        return view
    }

}