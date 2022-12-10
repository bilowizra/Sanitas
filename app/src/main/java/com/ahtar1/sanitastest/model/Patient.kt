package com.ahtar1.sanitastest.model

import java.math.BigInteger
import java.util.Date

data class Patient(
    var name:String?= null,
    var birthdate:String?= null,
    var age:Int?=null,
    var gender:String?= null,
    var weight:Int?= null,
    var height:Int?= null,
    var bmi:Float?= null,
    var bloodType:String?= null,
    var allergies:String?= null,
    var spokenLanguage:String?= null,
    var phoneNumber: Int?= null,
    var tc:String?= null,
    var uid:String?= null
)
