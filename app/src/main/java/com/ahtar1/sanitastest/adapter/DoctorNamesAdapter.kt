package com.ahtar1.sanitastest.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.databinding.DoctorsRecyclerViewRowBinding
import com.ahtar1.sanitastest.view.patient_datetime_selection
import kotlinx.android.synthetic.main.doctors_recycler_view_row.*
import kotlin.coroutines.coroutineContext


class DoctorNamesAdapter(val doctorsList: ArrayList<String>,val context: Context): RecyclerView.Adapter<DoctorNamesAdapter.DoctorNameHolder>() {

    class DoctorNameHolder(val binding: DoctorsRecyclerViewRowBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorNameHolder {
        val binding= DoctorsRecyclerViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DoctorNameHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorNameHolder, position: Int) {
        holder.binding.doctorNameAddAppointmentTextView.text=doctorsList[position]

        holder.binding.doctorNameAddAppointmentTextView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("doctorName", holder.binding.doctorNameAddAppointmentTextView.text.toString())
            val manager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val fragment= patient_datetime_selection()
            fragment.arguments = bundle
            manager?.beginTransaction()?.replace(R.id.frame_layout, fragment)?.commit()
        }
    }

    override fun getItemCount(): Int {
        println("sayisi "+doctorsList.size)
        return doctorsList.size
    }



}