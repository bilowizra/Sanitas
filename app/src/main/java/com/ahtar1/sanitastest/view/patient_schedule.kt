package com.ahtar1.sanitastest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.adapter.AppointmentAdapter
import com.ahtar1.sanitastest.model.Appoinment
import com.ahtar1.sanitastest.model.DisplayAppointment
import com.ahtar1.sanitastest.viewmodel.PatientProfileViewModel
import com.ahtar1.sanitastest.viewmodel.PatientScheduleViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_patient_schedule.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class patient_schedule : Fragment() {
    private lateinit var viewModel: PatientScheduleViewModel
    var appointmentsList:  ArrayList<DisplayAppointment> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[PatientScheduleViewModel::class.java]



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_patient_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appointmentsRecyclerView.layoutManager= LinearLayoutManager(context)
        getAppointments()
        println("getAppointments dan sonra")

        addAppointmentButton.setOnClickListener {
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frame_layout, patient_add_appointment())
            fragmentTransaction?.commit()
        }

    }

    private fun getAppointments(){
        CoroutineScope(Dispatchers.Main).launch {
            addAppointmentButton.isEnabled= false
            progressBar2.visibility= View.VISIBLE
            val auth= FirebaseAuth.getInstance()
            val uid = auth.currentUser!!.uid
            println(uid)
            val uidQuery: QuerySnapshot = FirebaseFirestore.getInstance().collection("patients").whereEqualTo("uid",uid).get().await()
            val tc = uidQuery.documents[0].get("tc").toString()
            println(tc)
            val database= Firebase.database("https://sanitas-8090c-default-rtdb.europe-west1.firebasedatabase.app")
            val query: QuerySnapshot = FirebaseFirestore.getInstance().collection("appointments").whereEqualTo("patientTc",tc).get().await()
            println(query.documents.size)
            for (document in query.documents){
                println("girdi")
                val date = document.get("date").toString()
                val time = document.get("time").toString()
                val doctor = document.get("doctorTc").toString()
                println("doctor name: "+doctor)
                //get patient name from patient collection using patient tc
                val query2: QuerySnapshot = FirebaseFirestore.getInstance().collection("doctors").whereEqualTo("tc",doctor).get().await()

                val doctorName= query2.documents[0].get("name").toString()

                println(date)
                println(time)
                println(doctorName)
                val appointmentDate=LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                val currentDate=LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), DateTimeFormatter.ofPattern("dd/MM/yyyy"))

                System.out.println(" C DATE is  "+currentDate)

                if(appointmentDate.isAfter(currentDate) || appointmentDate.isEqual(currentDate)){
                    val displayAppointment= DisplayAppointment(doctorName,date,time)
                    appointmentsList.add(displayAppointment)

                }



            }

            println("a ")
            val adapter= AppointmentAdapter(appointmentsList)
            appointmentsRecyclerView.adapter=adapter
            println(appointmentsList.size)
            println(appointmentsList.forEach {
                println(it.date)
                println(it.doctorName)
                println(it.time)
            })

            addAppointmentButton.isEnabled= true
            progressBar2.visibility= View.GONE
        }
    }
}