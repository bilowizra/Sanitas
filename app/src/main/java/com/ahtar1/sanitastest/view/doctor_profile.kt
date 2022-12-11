package com.ahtar1.sanitastest.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.viewmodel.DoctorProfileViewModel
import kotlinx.android.synthetic.main.fragment_doctor_profile.*


class doctor_profile : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: DoctorProfileViewModel
    var counter=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel= ViewModelProvider(this)[DoctorProfileViewModel::class.java]
        sharedPreferences=this.requireActivity().getSharedPreferences("com.ahtar1.sanitastest",
            Context.MODE_PRIVATE)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phoneValueEdit.isEnabled = false
        specialtyValueEdit.isEnabled = false
        ageValueEdit.isEnabled = false

        viewModel.getDoctor()

        viewModel.getName.observe(viewLifecycleOwner,Observer{
            resName.setText(it)
        })
        viewModel.getTc.observe(viewLifecycleOwner,Observer{
            resTC.setText(it)
        })
        //viewModel.getEmail.observe(viewLifecycleOwner,Observer{
        //    resMail.setText(it)
        //})
        viewModel.age.observe(viewLifecycleOwner,Observer{
            ageValueEdit.setText(it.toString())
        })
        viewModel.specialty.observe(viewLifecycleOwner,Observer{
            specialtyValueEdit.setText(it)
        })
        viewModel.phone.observe(viewLifecycleOwner,Observer{
            phoneValueEdit.setText(it)
        })
        viewModel.gender.observe(viewLifecycleOwner,Observer{
            genderValueEdit.setText(it)
        })

        editDoctorProfileButton.setOnClickListener{
            counter++
            println(counter)

            if(counter%2==1){
                ageValueEdit.isEnabled = true
                phoneValueEdit.isEnabled = true
                specialtyValueEdit.isEnabled = true
                genderValueEdit.isEnabled = true

            }else{
                ageValueEdit.isEnabled = false
                phoneValueEdit.isEnabled = false
                specialtyValueEdit.isEnabled = false
                genderValueEdit.isEnabled = false

                val ageval = ageValueEdit.text.toString().toInt()
                val specialtyval = specialtyValueEdit.text.toString()
                val phoneval = phoneValueEdit.text.toString()
                val genderval = genderValueEdit.text.toString()

                viewModel.saveDoctor(ageval,genderval,specialtyval,phoneval)

                ageValueEdit.setText(ageval.toString())


            }



        }

    }



}