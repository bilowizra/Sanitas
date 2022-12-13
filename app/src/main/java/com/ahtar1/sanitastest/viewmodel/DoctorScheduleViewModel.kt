package com.ahtar1.sanitastest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.ahtar1.sanitastest.adapter.DoctorAppointmentAdapter
import com.ahtar1.sanitastest.model.DoctorsAppointment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DoctorScheduleViewModel: ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var apps: List<DocumentSnapshot> = emptyList()

    var yearGlobal: Int = 0
    var monthGlobal: Int = 0
    var dayGlobal: Int = 0
    var tcGlobal = ""


    @Suppress("DEPRECATION")
    fun checkAppointment(year: Int, month: Int, day: Int) = CoroutineScope(Dispatchers.Main).launch{
        auth = FirebaseAuth.getInstance()
        try {
            yearGlobal = year
            monthGlobal = month
            dayGlobal = day
            tcGlobal = FirebaseFirestore.getInstance().
            collection("users").whereEqualTo("email", auth.currentUser?.email)
                .get().await().documents[0].get("tc").toString()

            var appointmentQuery = FirebaseFirestore.getInstance().
            collection("appointments").whereEqualTo("date", "${day}/${month}/${year}")
                .whereEqualTo("doctorTc", tcGlobal).get().await()

            apps = appointmentQuery.documents

        }catch (e: Exception){
            println(e.message)
        }
    }

    fun EventChangeListener(list: ArrayList<DoctorsAppointment>, adapter: DoctorAppointmentAdapter){
        list.clear()
        adapter.notifyDataSetChanged()
        db = FirebaseFirestore.getInstance()

        db.collection("appointments").whereEqualTo("date", "${dayGlobal}/${monthGlobal}/${yearGlobal}")
            .whereEqualTo("doctorTc", tcGlobal)
            .addSnapshotListener(object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null){
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            list.add(dc.document.toObject(DoctorsAppointment::class.java))
                        }
                    }

                    adapter.notifyDataSetChanged()
                }

            })

    }


}