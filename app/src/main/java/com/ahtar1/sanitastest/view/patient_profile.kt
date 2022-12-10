package com.ahtar1.sanitastest.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.viewmodel.PatientProfileViewModel
import com.ahtar1.sanitastest.viewmodel.RegisterViewModel
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



        editProfileButton.setOnClickListener {
            counter++
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
                editProfileButton.text = "Save"

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
                editProfileButton.text = "Edit"

                val birthdate = birthdateValueTextView.text.toString()
                val age = ageValueTextView.text.toString().toInt()
                val gender= genderValueTextView.selectedItem.toString()
                val bloodType = bloodTypeValueTextView.selectedItem.toString()
                val height = heightValueTextView.text.toString().toInt()
                val weight = weightValueTextView.text.toString().toInt()
                val allergies = allergiesValueTextView.text.toString()
                val phone = phoneValueTextView6.text.toString().toInt()
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