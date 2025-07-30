package com.h2h.medical.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DailySheetPdfModel : Serializable {
    @SerializedName("backgroundColore")
    @Expose
    var backgroundColore: Int?=null

    @SerializedName("textColore")
    @Expose
    var textColore: Int?=null

    @SerializedName("fieldName")
    @Expose
    var fieldName: String = ""

    @SerializedName("timeZone1")
    @Expose
    var timeZone1: String = ""

    @SerializedName("timeZone2")
    @Expose
    var timeZone2: String = ""

    @SerializedName("timeZone3")
    @Expose
    var timeZone3: String = ""

    @SerializedName("timeZone4")
    @Expose
    var timeZone4: String = ""

    @SerializedName("timeZone5")
    @Expose
    var timeZone5: String = ""
}