package com.ahtar1.sanitastest.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.databinding.ActivityMainBinding
import com.ahtar1.sanitastest.databinding.ActivityRegisterBinding
import com.ahtar1.sanitastest.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailEditText
import kotlinx.android.synthetic.main.activity_login.loginButton
import kotlinx.android.synthetic.main.activity_login.passwordEditText
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password:String
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        viewModel= ViewModelProvider(this)[RegisterViewModel::class.java]

        println("girmeden önce")

        registerProfileButton.setOnClickListener(listener)


        loginRegisterButton.setOnClickListener(loginlistener)

    }

    private val listener= View.OnClickListener { view ->
        when (view.id) {
            R.id.registerProfileButton -> {
                email= emailEditText.text.toString()
                password= passwordEditText.text.toString()
                viewModel.registerUser(email,password)
                println("girdi")

                viewModel.isSuccessful.observe(this, Observer { isSuccessful->
                    isSuccessful?.let {
                        if (isSuccessful){
                            Toast.makeText(this,"Successfully saved", Toast.LENGTH_LONG).show()
                            val intent= Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            println("gone")
                        } else{
                            Toast.makeText(this,viewModel.errorMessage.value, Toast.LENGTH_LONG).show()
                            println(viewModel.errorMessage)
                        }
                    }

                })
            }
        }
    }
    val loginlistener= View.OnClickListener { view ->
        when (view.getId()) {
            R.id.loginRegisterButton -> {
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    /*
    fun registerOnClick(view: View){
        setEmailAndPassword()
        println("$email $password")
        viewModel.registerUser(email,password)
        println("girdi")

        viewModel.isSuccessful.observe(this@RegisterActivity, Observer { isSuccessful->
            println("observe")
            isSuccessful?.let {
                if (isSuccessful){
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    println("gone")
                } else{
                    Toast.makeText(this,viewModel.errorMessage.value, Toast.LENGTH_LONG).show()
                    println(viewModel.errorMessage)
                }
            }

        })
    }

    private fun setEmailAndPassword(){
        email= binding.emailEditText.text.toString()
        println(email +"a")
        password= binding.emailEditText.text.toString()
        println(password)
    }

    fun loginOnClick(view: View){
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }*/
}