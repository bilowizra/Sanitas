package com.ahtar1.sanitastest.model

import java.util.Date

data class Appoinment(
    var date: Date? = null,
    var patient: Patient? = null,
    var doctor: Doctor? = null,
    var location: Place? = null
)
