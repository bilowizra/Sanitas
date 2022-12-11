package com.ahtar1.sanitastest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahtar1.sanitastest.databinding.DoctorScheduleRecyclerViewBinding
import com.ahtar1.sanitastest.model.DoctorsAppointment
import kotlinx.coroutines.NonDisposableHandle.parent
import org.w3c.dom.Text

class DoctorAppointmentAdapter(private val doctorAppointmentsList: ArrayList<DoctorsAppointment>) : RecyclerView.Adapter<DoctorAppointmentAdapter.DoctorAppointmentHolder>() {

    class DoctorAppointmentHolder(binding: DoctorScheduleRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root){
        val date: TextView = binding.appointmentDate
        val time: TextView = binding.appointmentTime
        val patient: TextView = binding.appointmentPatient
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAppointmentHolder {
        val binding = DoctorScheduleRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return DoctorAppointmentHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorAppointmentHolder, position: Int) {
        val appointment : DoctorsAppointment = doctorAppointmentsList[position]
        holder.date.text = appointment.date
        holder.time.text = appointment.time
        holder.patient.text = appointment.patientTc

    }

    override fun getItemCount(): Int {
        return doctorAppointmentsList.size
    }
}