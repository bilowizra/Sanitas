package com.ahtar1.sanitastest.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ahtar1.sanitastest.model.AddedMedicament
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PatientMedicamentsViewModel: ViewModel() {

    fun saveMedicament(medicament: AddedMedicament){
        val uid= FirebaseAuth.getInstance().currentUser!!.uid

        FirebaseFirestore.getInstance().collection("medicaments").add(medicament).addOnSuccessListener {
            println("Medicament added")
        }.addOnFailureListener {
            println("Medicament not added")
        }
    }
}