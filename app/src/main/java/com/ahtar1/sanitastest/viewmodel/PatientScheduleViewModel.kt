package com.ahtar1.sanitastest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahtar1.sanitastest.model.Appoinment
import com.ahtar1.sanitastest.model.DisplayAppointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PatientScheduleViewModel:ViewModel() {
    var appointmentsList:  ArrayList<DisplayAppointment> = arrayListOf()

    fun getAppointments(){
        CoroutineScope(Dispatchers.Main).launch {
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
                val displayAppointment= DisplayAppointment(date,time,doctorName)

                appointmentsList.add(displayAppointment)

            }
            println(appointmentsList.forEach {
                println(it.date)
                println(it.doctorName)
                println(it.time)
            })
        }
    }

}