package com.ahtar1.sanitastest.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.databinding.ActivityRegisterBinding
import com.ahtar1.sanitastest.viewmodel.RegisterViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var tc:String
    private lateinit var verifyPassword:String
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private var isTcExistsVal= true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        this.window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
        }

        binding= ActivityRegisterBinding.inflate(layoutInflater)
        viewModel= ViewModelProvider(this)[RegisterViewModel::class.java]

        println("girmeden önce")

        signUpRegisterButton.setOnClickListener(listener)

        loginRegisterTextView.setOnClickListener(listener)

        viewModel.isSuccessful.observe(this, Observer { isSuccessful->
            isSuccessful?.let {
                if (isSuccessful){
                    viewModel.role.observe(this, Observer {
                        sharedPreferences=this.getSharedPreferences("com.ahtar1.sanitastest",
                            Context.MODE_PRIVATE)
                        if(it.equals("Doctor")){
                            println("doctora git")
                            sharedPreferences.edit().putString("role","Doctor").apply()
                            val intent= Intent(this,DoctorActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else{
                            sharedPreferences.edit().putString("role","Patient").apply()
                            val intent= Intent(this,PatientActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    })

                } else{
                    viewModel.errorMessage.observe(this, Observer {
                        Toast.makeText(this,it, Toast.LENGTH_LONG).show()
                        println(it)
                    })


                    registerProgressBar.visibility= View.GONE
                }
            }

        })

    }

    private val listener= View.OnClickListener { view ->
        when (view.id) {
            R.id.signUpRegisterButton -> {
                registerProgressBar.visibility= View.VISIBLE
                email= emailRegisterEditText.text.toString()
                password= passwordRegisterEditText.text.toString()
                tc = tcIdEditText.text.toString()
                verifyPassword= verifyPasswordEditText.text.toString()
                isTcExists(tc)
                if (email.isEmpty()|| password.isEmpty()|| tc.isEmpty()|| verifyPassword.isEmpty()){
                    if (email.isEmpty()){
                        emailRegisterEditText.error = "Enter your email"
                    }
                    if (password.isEmpty()){
                        passwordRegisterEditText.error = "Enter your password"
                    }
                    if (tc.isEmpty()){
                        tcIdEditText.error = "Enter your TC-ID"
                    }
                    if (verifyPassword.isEmpty()){
                        verifyPasswordEditText.error = "Reenter your password"
                    }

                    registerProgressBar.visibility= View.GONE
                } else if(!email.matches(emailPattern.toRegex())){
                    registerProgressBar.visibility= View.GONE
                    emailRegisterEditText.error= "Enter valid email adress"


                } else if(password.length<6){
                    registerProgressBar.visibility= View.GONE
                    passwordRegisterEditText.error= "Enter password longer than 6 characters"


                } else if(password!= verifyPassword){
                    registerProgressBar.visibility= View.GONE
                    verifyPasswordEditText.error= "Reenter your password correctly"


                } else if(!isTCKNCorrect(tc)){
                    println(isTCKNCorrect(tc))
                    registerProgressBar.visibility= View.GONE
                    tcIdEditText.error="Enter a valid TC-ID"
                } else{

                    if(isTcExistsVal){
                        viewModel.registerNewUser(email,password,tc)
                        println("observe")
                        println("girdi")
                    } else{
                        registerProgressBar.visibility= View.GONE
                        tcIdEditText.error="TC-ID already exists"
                    }

                }

            }
            R.id.loginRegisterTextView -> {
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun isTCKNCorrect(id: String?): Boolean {
        if (id == null) return false
        if (id.length != 11) return false
        val chars = id.toCharArray()
        val a = IntArray(11)
        for (i in 0..10) {
            a[i] = chars[i] - '0'
        }
        if (a[0] == 0) return false
        if (a[10] % 2 == 1) return false
        if (((a[0] + a[2] + a[4] + a[6] + a[8]) * 7 - (a[1] + a[3] + a[5] + a[7])) % 10 != a[9]) return false
        return (a[0] + a[1] + a[2] + a[3] + a[4] + a[5] + a[6] + a[7] + a[8] + a[9]) % 10 == a[10]
    }

    private fun isTcExists(tc: String){
        CoroutineScope(Dispatchers.Main).launch {
            var querySnapshot=
                FirebaseFirestore.getInstance().collection("users").whereEqualTo("tc",tc).get().await()
            if(querySnapshot.documents.isEmpty()){
                println("if "+querySnapshot.documents.isEmpty())
                isTcExistsVal= false
            } else{
                println("else "+querySnapshot.documents.isEmpty())
                isTcExistsVal= true
            }

        }
    }

}