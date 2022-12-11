package com.ahtar1.sanitastest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.adapter.AppointmentAdapter
import com.ahtar1.sanitastest.adapter.DoctorNamesAdapter
import com.ahtar1.sanitastest.model.DisplayAppointment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.doctors_recycler_view_row.*
import kotlinx.android.synthetic.main.fragment_patient_add_appointment.*
import kotlinx.android.synthetic.main.fragment_patient_schedule.*
import kotlinx.android.synthetic.main.recycler_view_row.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class patient_add_appointment : Fragment() {

    var doctorsList:  ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_add_appointment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDoctorNames()

        //Burası çalışmıyor

        doctorNameAddAppointmentTextView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("doctorName", doctorNameAddAppointmentTextView.text.toString())
            val fragment= patient_datetime_selection()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.frame_layout, fragment)?.commit()
        }

    }

    private fun getDoctorNames(){
        CoroutineScope(Dispatchers.Main).launch{
            val query: QuerySnapshot = FirebaseFirestore.getInstance().collection("doctors").get().await()
            for(document in query.documents){
                val doctorName = document.get("name").toString()
                doctorsList.add(doctorName)
                println("doktor ismi:"+doctorName)
            }
            doctorsRecyclerView.layoutManager= LinearLayoutManager(context)
            println("a ")
            val adapter= DoctorNamesAdapter(doctorsList)
            doctorsRecyclerView.adapter=adapter

        }

    }
}