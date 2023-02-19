package com.ahtar1.sanitastest.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahtar1.sanitastest.model.Appoinment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PatientDatetimeSelectionViewModel: ViewModel() {

    var isSuccessful= false
    var hoursList = ArrayList<String>()


    suspend fun saveAppointment(date: String, time:String,doctorName:String,context: Context){
        val uid= FirebaseAuth.getInstance().currentUser!!.uid
        CoroutineScope(Dispatchers.Main).launch {
            val patientTcQuery= FirebaseFirestore.getInstance().collection("patients").whereEqualTo("uid",uid).get().await()
            val tc= patientTcQuery.documents[0].get("tc").toString()

            val doctorTcQuery= FirebaseFirestore.getInstance().collection("doctors").whereEqualTo("name",doctorName).get().await()
            val doctorTc= doctorTcQuery.documents[0].get("tc").toString()

            val appointment= Appoinment(date,time,tc,doctorTc)
            FirebaseFirestore.getInstance().collection("appointments").add(appointment).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(context,"Appointment saved successfully",Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(context,"Appointment could not be saved",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}