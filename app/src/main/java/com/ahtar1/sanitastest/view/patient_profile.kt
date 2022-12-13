package com.ahtar1.sanitastest.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.viewmodel.PatientProfileViewModel
import kotlinx.android.synthetic.main.fragment_patient_profile.*


class patient_profile : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: PatientProfileViewModel
    val genders = arrayOf("Male","Female","Other")
    var counter=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel= ViewModelProvider(this)[PatientProfileViewModel::class.java]
        sharedPreferences=this.requireActivity().getSharedPreferences("com.ahtar1.sanitastest",
            Context.MODE_PRIVATE)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        birthdateValueTextView.isEnabled = false
        ageValueTextView.isEnabled = false
        genderValueTextView.isEnabled = false
        bloodTypeValueTextView.isEnabled = false
        heightValueTextView.isEnabled = false
        weightValueTextView.isEnabled = false
        bmiValueTextView.isEnabled = false
        allergiesValueTextView.isEnabled = false
        phoneValueTextView6.isEnabled = false
        languageValueTextView.isEnabled = false
        viewModel.getPatient()
        viewModel.getTc.observe(viewLifecycleOwner, Observer {
            tcValueTextView6.setText(it)
        })
        viewModel.getName.observe(viewLifecycleOwner, Observer {
            nameTextView.setText(it)
        })
        viewModel.birthdate.observe(viewLifecycleOwner, Observer {
            birthdateValueTextView.setText(it)
        })
        viewModel.age.observe(viewLifecycleOwner, Observer {
            ageValueTextView.setText(it.toString())
        })
        viewModel.gender.observe(viewLifecycleOwner, Observer {
            if(viewModel.gender.equals("Male")){
                genderValueTextView.setSelection(0)
            } else if(viewModel.gender.equals("Female")){
                genderValueTextView.setSelection(1)
            } else if(viewModel.gender.equals("Other")){
                genderValueTextView.setSelection(2)
            }
        })
        viewModel.weight.observe(viewLifecycleOwner, Observer {
            weightValueTextView.setText(it.toString())
        })
        viewModel.height.observe(viewLifecycleOwner, Observer {
            heightValueTextView.setText(it.toString())
        })
        viewModel.bmi.observe(viewLifecycleOwner, Observer {
            bmiValueTextView.setText(it.toString())
        })
        viewModel.bloodType.observe(viewLifecycleOwner, Observer {
            if (viewModel.bloodType.equals("A+")){
                bloodTypeValueTextView.setSelection(0)
            } else if (viewModel.bloodType.equals("A-")){
                bloodTypeValueTextView.setSelection(1)
            } else if (viewModel.bloodType.equals("B+")){
                bloodTypeValueTextView.setSelection(2)
            } else if (viewModel.bloodType.equals("B-")){
                bloodTypeValueTextView.setSelection(3)
            } else if (viewModel.bloodType.equals("AB+")){
                bloodTypeValueTextView.setSelection(4)
            } else if (viewModel.bloodType.equals("AB-")){
                bloodTypeValueTextView.setSelection(5)
            } else if (viewModel.bloodType.equals("O+")){
                bloodTypeValueTextView.setSelection(6)
            } else if (viewModel.bloodType.equals("O-")){
                bloodTypeValueTextView.setSelection(7)
            }
        })
        viewModel.allergies.observe(viewLifecycleOwner, Observer {
            allergiesValueTextView.setText(it)
        })
        viewModel.language.observe(viewLifecycleOwner, Observer {
            languageValueTextView.setText(it)
        })
        viewModel.phone.observe(viewLifecycleOwner, Observer {
            phoneValueTextView6.setText(it)
        })


        editDoctorProfileButton.setOnClickListener {
            counter++
            println(counter)
            if (counter%2==1){
                birthdateValueTextView.isEnabled = true
                ageValueTextView.isEnabled = true
                genderValueTextView.isEnabled = true
                bloodTypeValueTextView.isEnabled = true
                heightValueTextView.isEnabled = true
                weightValueTextView.isEnabled = true
                bmiValueTextView.isEnabled = true
                allergiesValueTextView.isEnabled = true
                phoneValueTextView6.isEnabled = true
                languageValueTextView.isEnabled = true
                editDoctorProfileButton.text = "Save"

            }
            else{
                birthdateValueTextView.isEnabled = false
                ageValueTextView.isEnabled = false
                genderValueTextView.isEnabled = false
                bloodTypeValueTextView.isEnabled = false
                heightValueTextView.isEnabled = false
                weightValueTextView.isEnabled = false
                bmiValueTextView.isEnabled = false
                allergiesValueTextView.isEnabled = false
                phoneValueTextView6.isEnabled = false
                languageValueTextView.isEnabled = false
                editDoctorProfileButton.text = "Edit"



                val birthdate = birthdateValueTextView.text.toString()
                val age = ageValueTextView.text.toString().toInt()
                val gender= genderValueTextView.selectedItem.toString()
                val bloodType = bloodTypeValueTextView.selectedItem.toString()
                val height = heightValueTextView.text.toString().toInt()
                val weight = weightValueTextView.text.toString().toInt()
                val allergies = allergiesValueTextView.text.toString()
                val phone = phoneValueTextView6.text.toString()
                val language = languageValueTextView.text.toString()
                val bmi = (weight.toString().toDouble()/(height.toString().toDouble()*height.toString().toDouble())).toFloat()*10000
                val name=sharedPreferences.getString("name","N/A")

                viewModel.savePatient(birthdate, age, gender, bloodType, height, weight, bmi, allergies, phone, language)

                nameTextView.setText(name)
                birthdateValueTextView.setText(birthdate)
                ageValueTextView.setText(age.toString())
                heightValueTextView.setText(height.toString())
                weightValueTextView.setText(weight.toString())
                bmiValueTextView.setText(bmi.toString())
                allergiesValueTextView.setText(allergies)
                phoneValueTextView6.setText(phone.toString())
                languageValueTextView.setText(language)



               // val birthdate= birthdateValueTextView.text.toString()
                //val age= ageValueTextView.text.get()

            }


        }


    }


}