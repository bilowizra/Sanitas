package com.ahtar1.sanitastest.view

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil.setContentView
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.api.Context
import kotlinx.android.synthetic.main.fragment_patient_medicaments.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class patient_medicaments : Fragment() {

    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmReceiver: AlarmReceiver



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()


    }

    private fun showTimePicker() {

        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()
        picker.show(parentFragmentManager, "Sanitas")

        picker.addOnPositiveButtonClickListener{

            if(picker.hour > 12) {

                selectedTime.text = String.format("%02d", picker.hour - 12) + ":" + String.format("%02d", picker.minute) + " PM"

            }else{

                selectedTime.text = String.format("%02d", picker.hour) + ":" + String.format("%02d", picker.minute) + " AM"

            }

            calendar = Calendar.getInstance()
            calendar.[Calendar.HOUR_OF_DAY] = picker.hour
            calendar.[Calendar.MINUTE] = picker.minute
            calendar.[Calendar.SECOND] = 0
            calendar.[Calendar.MILLISECOND] = 0



        }

    }




    private fun createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Sanitas"
            val descriptionText = "Sanitas"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Sanitas", name, importance).apply {
                description = descriptionText
            }
            val notificationManager= requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_medicaments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectTimeBtn.setOnClickListener {
            showTimePicker()
        }

        cancelAlarmBtn.setOnClickListener {
            cancelAlarm()
        }

        setAlarmBtn.setOnClickListener {

            setAlarm()

        }


    }

    private fun cancelAlarm() {

        var alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        var pendingIntent =  PendingIntent.getBroadcast(requireContext() /*önceden this yazmıştım*/, 0, intent, 0)

        alarmManager.cancel(PendingIntent)


    }


    private fun setAlarm() {
        var alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        var pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)

        alarmManager.setRepeating(

            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )
        Toast.makeText(baseContext, "Service Cancelled", Toast.LENGTH_LONG).show()


    }


}