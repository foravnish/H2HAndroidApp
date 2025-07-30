package com.h2h.medical.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class WeeklySheetModel : Serializable {

    @SerializedName("representative")
    @Expose
    var representative: String = ""

    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("client")
    @Expose
    var client: String = ""

    @SerializedName("pay_period")
    @Expose
    var payperiod: String = ""
}