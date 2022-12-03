package com.ahtar1.sanitastest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahtar1.sanitastest.R
import com.ahtar1.sanitastest.databinding.ActivityLoginBinding
import com.ahtar1.sanitastest.databinding.ActivityMainBinding
import com.ahtar1.sanitastest.viewmodel.LoginViewModel
import com.ahtar1.sanitastest.viewmodel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password:String
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[LoginViewModel::class.java]
        binding= ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener(loginlistener)
    }
    private val loginlistener= View.OnClickListener { view ->
        when (view.getId()) {
            R.id.loginButton -> {
                email= emailEditText.text.toString()
                password= passwordEditText.text.toString()
                viewModel.loginUser(email, password)
                println("login listener")
                viewModel.isSuccessful.observe(this, Observer { isSuccessful->
                    isSuccessful?.let {
                        if (isSuccessful){
                            println("successful")
                            Toast.makeText(this,"Successfully saved", Toast.LENGTH_LONG).show()
                            val intent= Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        } else{
                            println("fail")
                            Toast.makeText(this,viewModel.errorMessage.value, Toast.LENGTH_LONG).show()
                        }
                    }

                })
            }
        }
    }

}