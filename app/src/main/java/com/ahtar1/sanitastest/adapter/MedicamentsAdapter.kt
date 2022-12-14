package com.ahtar1.sanitastest.adapter

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.ahtar1.sanitastest.databinding.MedicamentRecyclerViewRowBinding
import com.ahtar1.sanitastest.databinding.RecyclerViewRowBinding
import com.ahtar1.sanitastest.model.DisplayAppointment
import com.ahtar1.sanitastest.model.Medicament
import com.ahtar1.sanitastest.service.AlarmReceiver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MedicamentsAdapter(val medicamentList: ArrayList<Medicament>,val context: Context): RecyclerView.Adapter<MedicamentsAdapter.MedicamentHolder>() {

    class MedicamentHolder(val binding: MedicamentRecyclerViewRowBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicamentHolder {
        val binding= MedicamentRecyclerViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MedicamentHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicamentHolder, position: Int) {
        holder.binding.medicamentNameValueTextView.text=medicamentList[position].name
        println(medicamentList[position].name)
        holder.binding.medicamentTimeValueTextView.text=medicamentList[position].time
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        holder.binding.deleteMedicamentButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val medicamentQuery= FirebaseFirestore.getInstance().collection("medicaments").whereEqualTo("name",medicamentList[position].name).whereEqualTo("uid",uid).get().await()

                medicamentQuery.documents[0].reference.delete()


                medicamentList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,medicamentList.size)
            }

            val alarmManager= context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(context, 0, Intent(context, AlarmReceiver::class.java), PendingIntent.FLAG_IMMUTABLE)
            alarmManager.cancel(pendingIntent)

        }
    }


    override fun getItemCount(): Int {
        println("sayisi "+medicamentList.size)
        return medicamentList.size
    }


}