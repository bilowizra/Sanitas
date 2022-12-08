package com.ahtar1.sanitastest.model

import java.math.BigInteger
import java.util.Date

data class Patient(
    var name:String?= null,
    var surname:String?= null,
    var birthdate:Date?= null,
    var age:Int?=null,
    var gender:Gender?= null,
    var weight:Double?= null,
    var height:Double?= null,
    var bmi:Float?= null,
    var bloodType:BloodType?= null,
    var allergies:List<String>?= null,
    var spokenLanguage:String?= null,
    var phoneNumber: BigInteger?= null,
    var tc:BigInteger?= null,
    var uid:String?= null
)
