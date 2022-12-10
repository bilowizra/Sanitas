package com.ahtar1.sanitastest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.model.Appoinment
import com.ahtar1.sanitastest.viewmodel.PatientProfileViewModel
import com.ahtar1.sanitastest.viewmodel.PatientScheduleViewModel


class patient_schedule : Fragment() {
    private lateinit var viewModel: PatientScheduleViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[PatientScheduleViewModel::class.java]
        viewModel.getAppointments()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_schedule, container, false)
    }


}