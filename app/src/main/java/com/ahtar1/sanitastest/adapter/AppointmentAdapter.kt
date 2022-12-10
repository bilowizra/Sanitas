package com.ahtar1.sanitastest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahtar1.sanitastest.databinding.RecyclerViewRowBinding
import com.ahtar1.sanitastest.model.DisplayAppointment

class AppointmentAdapter(private val appointmentList: ArrayList<DisplayAppointment>): RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder>() {

    class AppointmentHolder(val binding: RecyclerViewRowBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        val binding= RecyclerViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AppointmentHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        holder.binding.doctorNameValueTextView.text=appointmentList[position].doctorName
        holder.binding.dateValueTextView.text=appointmentList[position].date
        holder.binding.timeValueTextView.text=appointmentList[position].time
    }

    override fun getItemCount(): Int {
        println("sayisi "+appointmentList.size)
        return appointmentList.size
    }


}