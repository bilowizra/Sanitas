package com.ahtar1.sanitastest.view

import android.R.attr.action
import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.model.AddedMedicament
import com.ahtar1.sanitastest.service.*
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
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


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
                val notifID = (0..99999).random()
                val medicament= AddedMedicament(medicamentName,varSelectedTime,notifID.toString(),FirebaseAuth.getInstance().currentUser!!.uid)
                viewModel.saveMedicament(medicament)

                alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmReceiver::class.java)
                intent.putExtra("medicamentName",medicamentName)

                pendingIntent = PendingIntent.getBroadcast(context, notifID, intent, PendingIntent.FLAG_IMMUTABLE)

                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
                println("Alarm set")
                println(calendar.get(Calendar.HOUR_OF_DAY))

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

                calendar = Calendar.getInstance()
                calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                calendar[Calendar.MINUTE] = minute
                calendar[Calendar.SECOND] = 1
                calendar[Calendar.MILLISECOND] = 0

            },hour,minute,true).show()
        }
    }


    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name: CharSequence="foxandroidReminderChannel"
            val description="Channel for alarm Manager"
            val importance =NotificationManager.IMPORTANCE_HIGH
            val channel= NotificationChannel("channelId",name,importance)
            channel.description= description
            val notificationManager= requireActivity().getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)
        }
    }

}