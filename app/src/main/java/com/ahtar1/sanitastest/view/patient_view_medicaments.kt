package com.ahtar1.sanitastest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.adapter.MedicamentsAdapter
import com.ahtar1.sanitastest.model.Medicament
import com.ahtar1.sanitastest.viewmodel.PatientMedicamentsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_patient_view_medicaments.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class patient_view_medicaments : Fragment() {

    private lateinit var viewModel: PatientMedicamentsViewModel


    var medicamentsList: ArrayList<Medicament> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[PatientMedicamentsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_view_medicaments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        medicamentRecyclerView.layoutManager= LinearLayoutManager(context)
        getMedicaments()

        addMedicamentButton.setOnClickListener {
            val fragment= patient_medicaments()
            val transaction= activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_layout, fragment)
            transaction?.commit()
        }

    }

    private fun getMedicaments(){

        lifecycleScope.launch {
            val uid= FirebaseAuth.getInstance().currentUser!!.uid

            val medicamentsQuery= FirebaseFirestore.getInstance().collection("medicaments").whereEqualTo("uid",uid).get().await()

            for (document in medicamentsQuery.documents){
                val medicament=Medicament(document.get("name").toString(),document.get("time").toString())
                println(medicament.name + " " + medicament.time)
                medicamentsList.add(medicament)

            }

            val adapter= MedicamentsAdapter(medicamentsList,requireActivity())
            println("list size "+medicamentsList.size)
            medicamentRecyclerView.adapter=adapter

        }



    }
}