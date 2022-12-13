package com.ahtar1.sanitastest.view

import android.app.*
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.model.AddedMedicament
import com.ahtar1.sanitastest.service.*
import com.ahtar1.sanitastest.service.Notification
import com.ahtar1.sanitastest.viewmodel.PatientMedicamentsViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_patient_medicaments.*
import kotlinx.android.synthetic.main.recycler_view_row.*
import java.util.*


class patient_medicaments : Fragment() {
    var hour2: Long? = null
    var minute2: Long? = null
    private lateinit var viewModel: PatientMedicamentsViewModel
    var varSelectedTime= "girilmedi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[PatientMedicamentsViewModel::class.java]
        createNotificationChannel()

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
        cancelAlarmBtn.setOnClickListener {
            val fragment= patient_view_medicaments()
            val transaction= activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, fragment)
            transaction?.commit()
        }

        setAlarmBtn.setOnClickListener {

            if (varSelectedTime=="girilmedi"){
                Toast.makeText(context,"Please select Time",Toast.LENGTH_SHORT).show()
            }
            else{

                val medicamentName= medicineNameValueTextView.text.toString()

                val medicament= AddedMedicament(medicamentName,varSelectedTime,FirebaseAuth.getInstance().currentUser!!.uid)
                viewModel.saveMedicament(medicament)



                val fragment= patient_view_medicaments()
                val transaction= activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_layout, fragment)
                transaction?.commit()
                Toast.makeText(context,"Alarm is set",Toast.LENGTH_SHORT).show()
            }


        }

         selectTimeBtn.setOnClickListener {
            val currentTime= Calendar.getInstance()
            val hour=currentTime.get(Calendar.HOUR_OF_DAY)
            val minute=currentTime.get(Calendar.MINUTE)

            TimePickerDialog(context,{ view, hourOfDay, minute ->
                var time= "$hourOfDay:$minute"
                if (hourOfDay<10 && minute<10){
                    time= "0$hourOfDay:0$minute"
                }
                else if (hourOfDay<10){
                    time= "0$hourOfDay:$minute"
                }
                else if (minute<10){
                    time= "$hourOfDay:0$minute"
                }
                else{
                    time= "$hourOfDay:$minute"
                }
                selectedTime.text=time
                println(time)
                varSelectedTime=time
                hour2= hourOfDay.toLong()
                minute2= minute.toLong()
            },hour,minute,true).show()
        }
    }

    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(requireContext(), NotificationManager::class.java)
        notificationManager!!.createNotificationChannel(channel)
    }

    private fun scheduleNotification()
    {
        val intent = Intent(context, Notification::class.java)
        val title = medicineNameValueTextView.text.toString()
        val message = "Time to take your medicine"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(requireContext(), AlarmManager::class.java)
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }
    private fun getTime(): Long
    {
        val minute = minute2
        val hour = hour2


        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = hour!!.toInt()
        calendar[Calendar.MINUTE] = minute!!.toInt()
        calendar[Calendar.SECOND] = 0
        if (calendar.time.compareTo(Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1)

        return calendar.timeInMillis
    }
}