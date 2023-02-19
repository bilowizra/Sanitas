package com.ahtar1.sanitastest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.viewmodel.MainViewModel
import com.ahtar1.sanitastest.viewmodel.PatientDatetimeSelectionViewModel
import com.ahtar1.sanitastest.viewmodel.RegisterViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_patient_datetime_selection.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class patient_datetime_selection : Fragment() {

    private lateinit var viewModel: PatientDatetimeSelectionViewModel
    var hoursList = ArrayList<String>()
    private lateinit var doctorName : String
    private lateinit var tc:String
    private lateinit var time: String
    private lateinit var date: String
    private var isTimeSelected: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[PatientDatetimeSelectionViewModel::class.java]



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val arguments= this.arguments
        doctorName = arguments?.getString("doctorName").toString()
        println(doctorName)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_datetime_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView.minDate = System.currentTimeMillis() - 1000
        calendarView.maxDate = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14



        nineCheckBox.setOnClickListener {
            tenCheckBox.isChecked = false
            elevenCheckBox.isChecked = false
            twelveCheckBox.isChecked = false
            thirteenCheckBox.isChecked = false
            fourteenCheckBox.isChecked = false
            fifteenCheckBox.isChecked = false
            sixteenCheckBox.isChecked = false

        }

        tenCheckBox.setOnClickListener{
            nineCheckBox.isChecked = false
            elevenCheckBox.isChecked = false
            twelveCheckBox.isChecked = false
            thirteenCheckBox.isChecked = false
            fourteenCheckBox.isChecked = false
            fifteenCheckBox.isChecked = false
            sixteenCheckBox.isChecked = false
        }
        elevenCheckBox.setOnClickListener{
            nineCheckBox.isChecked = false
            tenCheckBox.isChecked = false
            twelveCheckBox.isChecked = false
            thirteenCheckBox.isChecked = false
            fourteenCheckBox.isChecked = false
            fifteenCheckBox.isChecked = false
            sixteenCheckBox.isChecked = false
        }
        twelveCheckBox.setOnClickListener{
            nineCheckBox.isChecked = false
            tenCheckBox.isChecked = false
            elevenCheckBox.isChecked = false
            thirteenCheckBox.isChecked = false
            fourteenCheckBox.isChecked = false
            fifteenCheckBox.isChecked = false
            sixteenCheckBox.isChecked = false
        }
        thirteenCheckBox.setOnClickListener{
            nineCheckBox.isChecked = false
            tenCheckBox.isChecked = false
            elevenCheckBox.isChecked = false
            twelveCheckBox.isChecked = false
            fourteenCheckBox.isChecked = false
            fifteenCheckBox.isChecked = false
            sixteenCheckBox.isChecked = false
        }
        fourteenCheckBox.setOnClickListener{
            nineCheckBox.isChecked = false
            tenCheckBox.isChecked = false
            elevenCheckBox.isChecked = false
            twelveCheckBox.isChecked = false
            thirteenCheckBox.isChecked = false
            fifteenCheckBox.isChecked = false
            sixteenCheckBox.isChecked = false
        }
        fifteenCheckBox.setOnClickListener{
            nineCheckBox.isChecked = false
            tenCheckBox.isChecked = false
            elevenCheckBox.isChecked = false
            twelveCheckBox.isChecked = false
            thirteenCheckBox.isChecked = false
            fourteenCheckBox.isChecked = false
            sixteenCheckBox.isChecked = false
        }
        sixteenCheckBox.setOnClickListener{
            nineCheckBox.isChecked = false
            tenCheckBox.isChecked = false
            elevenCheckBox.isChecked = false
            twelveCheckBox.isChecked = false
            thirteenCheckBox.isChecked = false
            fourteenCheckBox.isChecked = false
            fifteenCheckBox.isChecked = false
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            lateinit var selectedDate: String

            if (month < 10 && dayOfMonth < 10) {
                selectedDate = "0$dayOfMonth/0${month + 1}/$year"
            } else if (month < 10) {
                selectedDate = "$dayOfMonth/0${month + 1}/$year"
            } else if (dayOfMonth < 10) {
                selectedDate = "0$dayOfMonth/${month + 1}/$year"
            } else {
                selectedDate = "$dayOfMonth/${month + 1}/$year"
            }


            nineCheckBox.isEnabled= true
            tenCheckBox.isEnabled= true
            elevenCheckBox.isEnabled= true
            twelveCheckBox.isEnabled= true
            thirteenCheckBox.isEnabled= true
            fourteenCheckBox.isEnabled= true
            fifteenCheckBox.isEnabled= true
            sixteenCheckBox.isEnabled= true

            CoroutineScope(Dispatchers.Main).launch {
                getHours(selectedDate, doctorName)
                println(hoursList)
            }
            date= selectedDate
            println(selectedDate)

        }

        confirmAppointmentButton.setOnClickListener {

            if(nineCheckBox.isChecked){
                time="09:00"
            }
            else if (tenCheckBox.isChecked){
                time="10:00"
            } else if(elevenCheckBox.isChecked){
                time="11:00"
            } else if(twelveCheckBox.isChecked){
                time="12:00"
            } else if(thirteenCheckBox.isChecked){
                time="13:00"
            } else if(fourteenCheckBox.isChecked){
                time="14:00"
            } else if(fifteenCheckBox.isChecked){
                time="15:00"
            } else if(sixteenCheckBox.isChecked){
                time="16:00"
            } else{
                isTimeSelected=false
                Toast.makeText(context, "Please select a time", Toast.LENGTH_SHORT).show()
            }
            if(isTimeSelected) {
                runBlocking {
                    viewModel.saveAppointment(date, time, doctorName,requireActivity())
                    val fragmentManager = parentFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout, patient_schedule())
                    fragmentTransaction.commit()
                }

            }
        }
    }

    fun getHours(date: String,name:String) {
        hoursList = arrayListOf("")

        CoroutineScope(Dispatchers.Main).launch {
            println("getHours")
            println(name)
            tc= FirebaseFirestore.getInstance().collection("users").whereEqualTo("name",name).get().await().documents[0].get("tc").toString()
            println(tc)
            println(date)
            val query= FirebaseFirestore.getInstance().collection("appointments").whereEqualTo("date",date).whereEqualTo("doctorTc",tc).get().await()

            for(document in query.documents){
                hoursList.add(document.get("time").toString())
            }

            if (hoursList.contains("09:00")) {
                nineCheckBox.isEnabled = false
            }
            if (hoursList.contains("10:00")) {
                tenCheckBox.isEnabled = false
            }
            if (hoursList.contains("11:00")) {
                elevenCheckBox.isEnabled = false
            }
            if (hoursList.contains("12:00")) {
                twelveCheckBox.isEnabled = false
            }
            if (hoursList.contains("13:00")) {
                thirteenCheckBox.isEnabled = false
            }
            if (hoursList.contains("14:00")) {
                fourteenCheckBox.isEnabled = false
            }
            if (hoursList.contains("15:00")) {
                fifteenCheckBox.isEnabled = false
            }
            if (hoursList.contains("16:00")) {
                sixteenCheckBox.isEnabled = false
            }

        }


    }
}