package com.h2h.medical.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class NursingAssessmentSheetModel : Serializable {

    var name: String = ""
    var dob: String = ""
    var date: String = ""
    var daily_notes: String = ""

    var mEtExceptionNeurological: String = ""
    var mEtExceptionPulmonary: String = ""
    var mEtO2Pulmonary: String = ""
    var mEtBowelSoundsGastrointestinal: String=""
    var mEtExceptionCardiovascular: String = ""
    var mEtHeartRhythmCardiovascular: String = ""
    var mEtEdemaCardiovascular: String = ""
    var mEtCapillaryRefillCardiovascular: String = ""
    var mEtExceptionGastrointestinal: String = ""
    var et_exception_gen_uni: String = ""
    var mEtExceptionGenitoUrinary: String = ""
    var mEtUrineOrderGenitoUrinary: String = ""
    var mEtUrineColorGenitoUrinary: String = ""
    var mEtExceptionIntegumentary: String = ""
    var mEtExceptionMusculoSkeletal: String = ""
    var mEtBracesMusculoSkeletal: String = ""
    var mEtMobilityMusculoSkeletal: String = ""
    var mEtExceptionPsychosocial: String = ""
    var mEtExceptionPain: String = ""
    var mCbWnlNeurological: Boolean =false
    var mCbExceptionNeurological: Boolean =false
    var mCbWnlPulmonary: Boolean =false
    var mCbExceptionPulmonary: Boolean =false
    var mCbTracheostomyPulmonary: Boolean =false
    var mCbO2Pulmonary: Boolean =false
    var mCbWnlCardiovascular: Boolean =false
    var mCbExceptionCardiovascular: Boolean =false
    var mCbHeartRhythmCardiovascular: Boolean =false
    var mCbEdemaCardiovascular: Boolean =false
    var mCbCapillaryRefillCardiovascular: Boolean =false
    var mCbFatigueCardiovascular: Boolean =false
    var mCbDizzinessCardiovascular: Boolean =false

    var mCbWnlGastrointestinal: Boolean =false
    var mCbExceptionGastrointestinal: Boolean =false
    var mCbGTubeGastrointestinal: Boolean =false
    var mCbIncontinentGastrointestinal: Boolean =false
    var mCbNauseaGastrointestinal: Boolean =false
    var mCbVomitingGastrointestinal: Boolean =false
    var mCbConstipationGastrointestinal: Boolean =false
    var mCbDiarrheaGastrointestinal: Boolean =false
    var mCbBowelSoundsGastrointestinal: Boolean =false

    var mCbWnlGenitoUrinary: Boolean =false
    var cb_exceptions_ch: Boolean =false
    var cb_incontinent_ch: Boolean =false
    var cb_urgency_ch: Boolean =false
    var mCbExceptionGenitoUrinary: Boolean =false

    var mCbWnlIntegumentary: Boolean =false
    var mCbBruisingIntegumentary: Boolean =false
    var mCbFragileSkinIntegumentary: Boolean =false
    var mCbRashIntegumentary: Boolean =false
    var mCbPetechiaeIntegumentary: Boolean =false
    var mCbAcneIntegumentary: Boolean =false
    var mCbAbrasionIntegumentary: Boolean =false
    var mCbExceptionIntegumentary: Boolean =false

    var mCbWnlMusculoSkeletal: Boolean =false
    var mCbExceptionMusculoSkeletal: Boolean =false
    var mCbBracesMusculoSkeletal: Boolean =false
    var mCbMobilityMusculoSkeletal: Boolean =false

    var mCbWnlPsychosocial: Boolean =false
    var mCbExceptionPsychosocial: Boolean =false

    var mCbWnlPain: Boolean =false
    var mCbExceptionPain: Boolean =false




}