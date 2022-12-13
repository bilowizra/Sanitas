package com.ahtar1.sanitastest.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ahtar1.sanitastest.view.PatientActivity
import com.ahtar1.sanitastest.view.patient_medicaments

class AlarmReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val i= Intent(context, patient_medicaments::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent= PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_IMMUTABLE)

        val builder= NotificationCompat.Builder(context!!, "channelId")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(intent.getStringExtra("medicamentName"))
            .setContentText("You have to take your medicament: ${intent.getStringExtra("medicamentName")}")
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager=NotificationManagerCompat.from(context)
        notificationManager.notify(123,builder.build())
    }


}