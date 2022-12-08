package com.ahtar1.sanitastest.model

import android.companion.AssociationInfo

data class Doctor(
    var name:String?= null,
    var surname:String?= null,
    var age:Int?= null,
    var gender: Gender?= null,
    var specialty:String?= null,
    var association:Place?= null,
    var uid:String?= null
)
