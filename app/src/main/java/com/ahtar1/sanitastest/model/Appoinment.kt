package com.ahtar1.sanitastest.model

import java.sql.Timestamp
import java.util.Date

data class Appoinment(
    var date: String? = null,
    var time: String? = null,
    var patientTc: String? = null,
    var doctorTc: String? = null,
)
