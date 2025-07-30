package com.h2h.medical.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DailySheetFormModel : Serializable {

    @SerializedName("temperature")
    @Expose
    var temperature: String = ""

    @SerializedName("pulse")
    @Expose
    var pulse: String = ""

    @SerializedName("spo2")
    @Expose
    var spo2: String = ""

    @SerializedName("oxygen")
    @Expose
    var oxygen: String = ""

    @SerializedName("respiration")
    @Expose
    var respiration: String = ""

    @SerializedName("lungsound")
    @Expose
    var lungsound: String = ""

    @SerializedName("workofbreathe")
    @Expose
    var workofbreathe: String = ""

    @SerializedName("tracheostomy")
    @Expose
    var tracheostomy: String = ""

    @SerializedName("interventiontime")
    @Expose
    var interventiontime: String = ""

    @SerializedName("rasscore")
    @Expose
    var rasscore: String = ""

    @SerializedName("intervention")
    @Expose
    var intervention: String = ""

    @SerializedName("quantity")
    @Expose
    var quantity: String = ""

    @SerializedName("color")
    @Expose
    var color: String = ""

    @SerializedName("consistency")
    @Expose
    var consistency: String = ""

    @SerializedName("interventionoutcome")
    @Expose
    var interventionoutcome: String = ""

    @SerializedName("gtubesite")
    @Expose
    var gtubesite: String = ""


    @SerializedName("bloodGulcose")
    @Expose
    var bloodGulcose: String = ""

    @SerializedName("nutrition")
    @Expose
    var nutrition: String = ""

    @SerializedName("input")
    @Expose
    var input: String = ""

    @SerializedName("outcome")
    @Expose
    var outcome: String = ""

    @SerializedName("position")
    @Expose
    var position: String = ""

    @SerializedName("assist")
    @Expose
    var assist: String = ""

    @SerializedName("participating")
    @Expose
    var participating: String = ""

    @SerializedName("activity")
    @Expose
    var activity: String = ""

    @SerializedName("braces")
    @Expose
    var braces: String = ""

    @SerializedName("stretched")
    @Expose
    var stretched: String = ""

    @SerializedName("medication")
    @Expose
    var medication: String = ""

    @SerializedName("monitortpn")
    @Expose
    var monitortpn: String = ""

    @SerializedName("teach")
    @Expose
    var teach: String = ""

    @SerializedName("ventdecompressabd")
    @Expose
    var ventdecompressabd: String = ""


}