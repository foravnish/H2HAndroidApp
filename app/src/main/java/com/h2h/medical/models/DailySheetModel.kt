package com.h2h.medical.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DailySheetModel : Serializable {

    @SerializedName("name")
    @Expose
    var name: String = ""

    @SerializedName("dob")
    @Expose
    var dob: String = ""

    @SerializedName("date")
    @Expose
    var date: String = ""

    @SerializedName("report_received_from")
    @Expose
    var report_received_from: String = ""

    @SerializedName("daily_notes")
    @Expose
    var daily_notes: String = ""
    @SerializedName("stime")
    @Expose
    var stime: String = ""

    @SerializedName("etime")
    @Expose
    var etime: String = ""

    @SerializedName("duration")
    @Expose
    var duration: String = ""
}