package com.ahtar1.sanitastest.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.databinding.ActivityLoginBinding
import com.ahtar1.sanitastest.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences=this.getSharedPreferences("com.ahtar1.sanitastest",
            Context.MODE_PRIVATE)
        viewModel= ViewModelProvider(this)[LoginViewModel::class.java]

        binding= ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_login)

        loginLoginButton.setOnClickListener(loginListener)
        signUpLoginTextView.setOnClickListener(loginListener)
        viewModel.isSuccessful.observe(this, Observer { isSuccessful->
            isSuccessful?.let {
                if (isSuccessful){
                    println("successful")
                    viewModel.isDoctor.observe(this, Observer {
                        onContentChanged()
                        if(it){
                            println("doktor")
                            println(viewModel.isDoctor.toString())
                            sharedPreferences.edit().putString("role","Doctor").apply()
                            println(sharedPreferences.getString("role","a"))
                            val intent= Intent(this,DoctorActivity::class.java)
                            startActivity(intent)
                        } else{
                            println("a "+ viewModel.isDoctor)
                            println("patient")
                            sharedPreferences.edit().putString("role","Patient").apply()
                            println(sharedPreferences.getString("role","a"))

                            val intent= Intent(this,PatientActivity::class.java)
                            startActivity(intent)
                        }
                    })


                } else{
                    println("fail")
                    Toast.makeText(this,"Email or password is wrong",Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    private val loginListener= View.OnClickListener { view ->
        when(view.id){
            R.id.loginLoginButton-> {
                loginProgressBar.visibility= View.VISIBLE
                val email= emailEditText.text.toString()
                val password= passwordLoginEditText.text.toString()

                if (email.isEmpty()|| password.isEmpty()){
                    if (email.isEmpty()){
                        emailEditText.error= "Enter your email"
                    }
                    if(password.isEmpty()){
                        passwordLoginEditText.error= "Enter your password"
                    }
                    loginProgressBar.visibility= View.GONE
                } else if(!email.matches(emailPattern.toRegex())){
                    loginProgressBar.visibility= View.GONE
                    emailEditText.error= "Enter a valid email"
                } else if(password.length<6){
                    loginProgressBar.visibility= View.GONE
                    passwordLoginEditText.error= "Enter password longer than 6 characters"
                } else{
                    viewModel.loginUser(email, password)
                    loginProgressBar.visibility= View.GONE

                }

            }

            R.id.signUpLoginTextView-> {
                val intent= Intent(this,RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }




}