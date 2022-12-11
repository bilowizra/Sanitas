package com.ahtar1.sanitastest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahtar1.sanitastest.databinding.DoctorsRecyclerViewRowBinding


class DoctorNamesAdapter(val doctorsList: ArrayList<String>): RecyclerView.Adapter<DoctorNamesAdapter.DoctorNameHolder>() {

    class DoctorNameHolder(val binding: DoctorsRecyclerViewRowBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorNameHolder {
        val binding= DoctorsRecyclerViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DoctorNameHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorNameHolder, position: Int) {
        holder.binding.doctorNameAddAppointmentTextView.text=doctorsList[position]
    }

    override fun getItemCount(): Int {
        println("sayisi "+doctorsList.size)
        return doctorsList.size
    }



}