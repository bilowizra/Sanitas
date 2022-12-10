package com.ahtar1.sanitastest.model

import java.util.Date

data class Medicament(
    var name: String? = null,
    var prescription: PrescriptionType? = null,
    var relatedDisease: String? = null,
    var dosage: String? = null,
    var begindate: Date? = null,
)
