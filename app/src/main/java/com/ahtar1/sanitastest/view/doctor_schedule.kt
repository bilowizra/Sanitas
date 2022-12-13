package com.ahtar1.sanitastest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.adapter.DoctorAppointmentAdapter
import com.ahtar1.sanitastest.model.DoctorsAppointment
import com.ahtar1.sanitastest.viewmodel.DoctorScheduleViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_doctor_schedule.*
import kotlinx.android.synthetic.main.fragment_patient_schedule.*
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.Calendar
import java.util.Date


class doctor_schedule : Fragment() {

    private lateinit var viewModel: DoctorScheduleViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var appArrayList: ArrayList<DoctorsAppointment>
    private lateinit var doctorAppointmentAdapter: DoctorAppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DoctorScheduleViewModel::class.java]

        recyclerView = recyclerViewDoctorSchedule
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        appArrayList = arrayListOf()

        doctorAppointmentAdapter = DoctorAppointmentAdapter(appArrayList)

        recyclerView.adapter = doctorAppointmentAdapter


        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            when(viewModel.apps.size){
                0 -> textView.hint = "You have no appointments in ${dayOfMonth}/${month + 1}/${year}"
                1 -> textView.hint = "You have an appointment in ${dayOfMonth}/${month + 1}/${year}"
                else -> textView.hint = "You have ${viewModel.apps.size} appointments in ${dayOfMonth}/${month + 1}/${year}"
            }

            viewModel.checkAppointment(year, month + 1, dayOfMonth)
            viewModel.EventChangeListener(appArrayList, doctorAppointmentAdapter)

        }
    }

}