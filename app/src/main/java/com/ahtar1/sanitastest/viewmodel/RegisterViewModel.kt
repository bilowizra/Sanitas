package com.ahtar1.sanitastest.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegisterViewModel: ViewModel() {

    private lateinit var auth: FirebaseAuth
    var isSuccessful= MutableLiveData<Boolean>()
    var errorMessage= MutableLiveData<String>()

    fun registerUser(email: String, password: String){
        println("register user")
        auth= FirebaseAuth.getInstance()

        if(email.isNotEmpty() && password.isNotEmpty()){
            println(email+password)

            CoroutineScope(Dispatchers.IO).launch {
                try{
                    println("try")
                    auth.createUserWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main){
                        isSuccessful.postValue(true)
                    }



                } catch (e: Exception){
                    println("Exception")
                    withContext(Dispatchers.Main){
                        isSuccessful.postValue(false)
                        errorMessage.postValue(e.message)
                    }

                }
                println("coroutine")
            }
        }
        println("boş")
    }

}