package com.ahtar1.sanitastest.model

import javax.net.ssl.SSLEngineResult.Status

data class Place(
    var name:String?= null,
    var type:PlaceType?= null,
    var status:StatusType?= null,
    var adress:String?= null
)
