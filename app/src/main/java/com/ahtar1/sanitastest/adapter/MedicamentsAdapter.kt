package com.ahtar1.sanitastest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahtar1.sanitastest.databinding.MedicamentRecyclerViewRowBinding
import com.ahtar1.sanitastest.databinding.RecyclerViewRowBinding
import com.ahtar1.sanitastest.model.DisplayAppointment
import com.ahtar1.sanitastest.model.Medicament
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MedicamentsAdapter(val medicamentList: ArrayList<Medicament>): RecyclerView.Adapter<MedicamentsAdapter.MedicamentHolder>() {

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

        }
    }


    override fun getItemCount(): Int {
        println("sayisi "+medicamentList.size)
        return medicamentList.size
    }


}