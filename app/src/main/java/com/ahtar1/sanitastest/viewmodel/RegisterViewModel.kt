package com.ahtar1.sanitastest.viewmodel

import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahtar1.sanitastest.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegisterViewModel: ViewModel() {
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    var isSuccessful= MutableLiveData<Boolean>()
    var role= MutableLiveData<String>()
    var errorMessage= MutableLiveData<String>()
    var tcExists= MutableLiveData<Boolean>()



    fun registerNewUser(email: String, password: String, tc: String){
        auth= FirebaseAuth.getInstance()
        database= Firebase.database("https://sanitas-8090c-default-rtdb.europe-west1.firebasedatabase.app")
        CoroutineScope(Dispatchers.Main).launch {

            val query: QuerySnapshot= FirebaseFirestore.getInstance().collection("doctors").whereEqualTo("tc",tc).get().await()
            if(query.documents.isEmpty()){
                role.postValue("Patient")
            } else{
                role.postValue("Doctor")
            }
        }



        //val doctorsDatabaseRef= database.reference.child("doctors").child("").
        //val query = doctorsDatabaseRef.orderByKey().equalTo(tc).

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                println("kullanıcı üretme başarılı")

                val databaseRef= Firebase.firestore.collection("users")
                // val databaseRef= database.reference.child("users").child(auth.currentUser!!.uid)

                val user: User= User(email, tc,role.value,auth.currentUser!!.uid)
                //etValue(user)
                databaseRef.add(user).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        println("b")
                        isSuccessful.postValue(true)
                    } else{
                        println("c")
                        isSuccessful.postValue(false)
                        auth.signOut()
                    }
                    println("d")

                }
                println("e")

            } else{
                isSuccessful.postValue(false)
                errorMessage.postValue(it.exception!!.message)
            }
        }
    }

    fun isTcExists(tc: String){
        CoroutineScope(Dispatchers.Main).launch {
            var querySnapshot=FirebaseFirestore.getInstance().collection("users").whereEqualTo("tc",tc).get().await()
            if(querySnapshot.documents.isEmpty()){
                println("if "+querySnapshot.documents.isEmpty())
                tcExists.postValue(false)
            } else{
                println("else "+querySnapshot.documents.isEmpty())
                tcExists.postValue(true)
            }
            /*
            querySnapshot.addOnCompleteListener {
                if (it.isSuccessful){
                    if(it.result.isEmpty){
                        println(it.result.isEmpty)
                        tcExists.postValue(false)
                    }else{
                        println(it.result.isEmpty)
                        tcExists.postValue(true)
                    }
                } else{
                    println(it.exception?.message)
                }


            }

             */
        }
    }

}