package com.h2h.medical.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeeklySheetFormModel : Serializable {

    @SerializedName("date")
    @Expose
    var date: String = ""

    @SerializedName("time_in")
    @Expose
    var timein: String = ""

    @SerializedName("time_out")
    @Expose
    var timeout: String = ""

    @SerializedName("lunch")
    @Expose
    var lunch: String = ""

    @SerializedName("class_room")
    @Expose
    var classroom: String = ""

    @SerializedName("day_total")
    @Expose
    var daytotal: String = ""

    @SerializedName("comments")
    @Expose
    var comments: String = ""
}