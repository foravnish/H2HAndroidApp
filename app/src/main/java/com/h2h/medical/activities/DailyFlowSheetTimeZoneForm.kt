package com.h2h.medical.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.h2h.medical.R
import com.h2h.medical.models.DailySheetFormModel
import com.h2h.medical.utils.AppPreferences
import com.h2h.medical.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_daily_flow_form.*
import kotlinx.android.synthetic.main.activity_daily_flow_sheet_time_zone_form.*
import java.text.SimpleDateFormat
import java.util.*

class DailyFlowSheetTimeZoneForm : BaseActivity() {

    private var timeZoneList = ArrayList<String>()
    private lateinit var mAppPreferences: AppPreferences

    private lateinit var mTvTitle: TextView
    private lateinit var mEtTemp: EditText
    private lateinit var mEtPulse: EditText
    private lateinit var mEtSp02: EditText
    private lateinit var mEtOxygen: EditText
    private lateinit var mEtRespiration: EditText
    private lateinit var mEtLungSound: EditText
    private lateinit var mEtWorkOfBreathe: EditText
    private lateinit var mEtTracheostomy: EditText
    private lateinit var mEtInterventionTime: EditText
    private lateinit var mEtRasScore: EditText
    private lateinit var mEtIntervention: EditText
    private lateinit var mEtQuantity: EditText
    private lateinit var mEtColor: EditText
    private lateinit var mEtConsistency: EditText
    private lateinit var mEtInterventionOutcome: EditText
    private lateinit var et_blood_glucose: EditText
    private lateinit var mEtGTubeSite: EditText
    private lateinit var mEtNutrition: EditText
    private lateinit var mEtInput: EditText
    private lateinit var mEtOutcome: EditText
    private lateinit var mEtPosition: EditText
    private lateinit var mEtAssist: RadioGroup
    private lateinit var mEtParticipating: CheckBox
    private lateinit var mEtActivity: EditText
    private lateinit var mEtBraces: RadioGroup
    private lateinit var mEtStretched: CheckBox
    private lateinit var mEtMedication: EditText
    private lateinit var mEtReportGivenTo: EditText
    private lateinit var mBtnSave: Button
    private lateinit var mBtnChangeTimeZone: Button
    private lateinit var et_vent_decompress_abd: CheckBox
    private lateinit var et_teach: CheckBox
    private lateinit var radioAssist: RadioButton
    private lateinit var radioBraces: RadioButton
    private lateinit var txtOption: TextView

    private var myCalendar: Calendar = Calendar.getInstance()
    private var dailyFlowSheetFormModel = DailySheetFormModel()
    private  var prefrenceKey: String="daily_flow_first_time_zone_data1"
    private lateinit var temperatureDialog: Dialog
    private lateinit var respirationDialog: Dialog
    private lateinit var spo2Dialog: Dialog
    private lateinit var rasDialog: Dialog
    private lateinit var pulseDialog: Dialog

    private val timePickerDialog =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            myCalendar.set(Calendar.MINUTE, minute)

            val dateFormat = "HH:mm"
            val sdf = SimpleDateFormat(dateFormat, Locale.US)
            mEtInterventionTime.setText(sdf.format(myCalendar.time))
        }


    companion object {
        fun open(myActivity: Activity, position: Int,value:Int) {
            val intent = Intent(myActivity, DailyFlowSheetTimeZoneForm::class.java)
            intent.putExtra("position", position)
            intent.putExtra("value", value)
            myActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_flow_sheet_time_zone_form)
        val value=intent.getIntExtra("value",0)
        val p=intent.getIntExtra("position",0)
        prefrenceKey="daily_flow_first_time_zone_data$value$p".trim();
        if(value!=0)
        {
//            prefrenceKey="daily_flow_first_time_zone_data2"
        }
        initViews()
        listeners()

        showLoader(this, "Loading...")
        setData()
    }

    private fun listeners() {


        mBtnChangeTimeZone.setOnClickListener {

        }


        mBtnSave.setOnClickListener {

            dailyFlowSheetFormModel.temperature = mEtTemp.text.toString().trim()
            dailyFlowSheetFormModel.pulse = mEtPulse.text.toString().trim()
            dailyFlowSheetFormModel.spo2 = mEtSp02.text.toString().trim()
            dailyFlowSheetFormModel.oxygen = mEtOxygen.text.toString().trim()
            dailyFlowSheetFormModel.respiration = mEtRespiration.text.toString().trim()
            dailyFlowSheetFormModel.lungsound = mEtLungSound.text.toString().trim()
            dailyFlowSheetFormModel.workofbreathe = mEtWorkOfBreathe.text.toString().trim()
            dailyFlowSheetFormModel.tracheostomy = mEtTracheostomy.text.toString().trim()
            dailyFlowSheetFormModel.interventiontime = mEtInterventionTime.text.toString().trim()
            dailyFlowSheetFormModel.rasscore = mEtRasScore.text.toString().trim()
            dailyFlowSheetFormModel.intervention = mEtIntervention.text.toString().trim()
            dailyFlowSheetFormModel.quantity = mEtQuantity.text.toString().trim()
            dailyFlowSheetFormModel.bloodGulcose = et_blood_glucose.text.toString().trim()
            dailyFlowSheetFormModel.color = mEtColor.text.toString().trim()
            dailyFlowSheetFormModel.consistency = mEtConsistency.text.toString().trim()
            dailyFlowSheetFormModel.interventionoutcome = mEtInterventionOutcome.text.toString().trim()
            dailyFlowSheetFormModel.gtubesite = mEtGTubeSite.text.toString().trim()
            dailyFlowSheetFormModel.nutrition = mEtNutrition.text.toString().trim()
            dailyFlowSheetFormModel.input = mEtInput.text.toString().trim()
            dailyFlowSheetFormModel.outcome = mEtOutcome.text.toString().trim()
            dailyFlowSheetFormModel.position = mEtPosition.text.toString().trim()

            val selectedId: Int = mEtAssist.getCheckedRadioButtonId()
            if(selectedId!=-1) {
                radioAssist = findViewById<View>(selectedId) as RadioButton
                dailyFlowSheetFormModel.assist = radioAssist.text.toString().trim()
            }


            val selectedId1: Int = mEtBraces.getCheckedRadioButtonId()
            if(selectedId1!=-1) {
                radioBraces = findViewById<View>(selectedId1) as RadioButton
                dailyFlowSheetFormModel.braces = radioBraces.text.toString().trim()
            }

            dailyFlowSheetFormModel.activity = mEtActivity.text.toString().trim()

            if(mEtStretched.isChecked)
                dailyFlowSheetFormModel.stretched = "Yes"
            else
                dailyFlowSheetFormModel.stretched="N/A"

            dailyFlowSheetFormModel.medication = mEtMedication.text.toString().trim()

            dailyFlowSheetFormModel.monitortpn = et_monitoring_tpn.text.toString().trim()
            if(et_teach.isChecked)
               dailyFlowSheetFormModel.teach = "Yes"
            else
                dailyFlowSheetFormModel.teach = "N/A"
            if(et_vent_decompress_abd.isChecked)
                dailyFlowSheetFormModel.ventdecompressabd = "Yes"
            else
                dailyFlowSheetFormModel.ventdecompressabd = "N/A"
            if(et_participating.isChecked)
                dailyFlowSheetFormModel.participating = "Yes"
            else
                dailyFlowSheetFormModel.participating = "N/A"


//            dailyFlowSheetFormModel.reportgivento = mEtReportGivenTo.text.toString().trim()
            Log.e("key111",prefrenceKey);
            when (intent.getIntExtra("position", -1)) {

                0 -> {
                    val json: String? = Gson().toJson(dailyFlowSheetFormModel)
                    mAppPreferences.setFirstTimeZoneData(json.toString(),prefrenceKey)
                }

                1 -> {
                    val json: String? = Gson().toJson(dailyFlowSheetFormModel)
                    mAppPreferences.setFirstTimeZoneData(json.toString(),prefrenceKey)
                }

                2 -> {
                    val json: String? = Gson().toJson(dailyFlowSheetFormModel)
                    mAppPreferences.setFirstTimeZoneData(json.toString(),prefrenceKey)
                }

                3 -> {
                    val json: String? = Gson().toJson(dailyFlowSheetFormModel)
                    mAppPreferences.setFirstTimeZoneData(json.toString(),prefrenceKey)
                }

                4 -> {
                    val json: String? = Gson().toJson(dailyFlowSheetFormModel)
                    mAppPreferences.setFirstTimeZoneData(json.toString(),prefrenceKey)
                }
            }

            finish()
        }

        mEtTemp.setOnClickListener {
            showTemperatureDialog()
        }


        mEtRespiration.setOnClickListener {
            showRespirationDialog()
        }


        mEtSp02.setOnClickListener {
            showSpO2Dialog()
        }


        mEtRasScore.setOnClickListener {
            showRASDialog()
        }


        mEtPulse.setOnClickListener {
            showPulseDialog()
        }

        mEtInterventionTime.setOnClickListener {
            TimePickerDialog(
                this,
                R.style.datePickerTheme,
                timePickerDialog,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
            ).show()
        }


    }


    private fun showPulseDialog() {

        pulseDialog = Dialog(this)
        pulseDialog.setContentView(R.layout.dialog_pulse)
        pulseDialog.setCancelable(false)
        pulseDialog.show()

        val mPicker1 = pulseDialog.findViewById(R.id.picker1) as NumberPicker
        val mPicker2 = pulseDialog.findViewById(R.id.picker2) as NumberPicker
        val mPicker3 = pulseDialog.findViewById(R.id.picker3) as NumberPicker
        val mBtnDone = pulseDialog.findViewById(R.id.btn_done) as Button
        val mBtnCancel = pulseDialog.findViewById(R.id.btn_cancel) as Button

        mPicker1.maxValue = 3
        mPicker1.minValue = 0
        mPicker2.maxValue = 9
        mPicker2.minValue = 0
        mPicker3.maxValue = 9
        mPicker3.minValue = 0

        mBtnDone.setOnClickListener {
            val pulse =
                (mPicker1.value.toString() + mPicker2.value.toString() + mPicker3.value.toString()).toInt()

            if (pulse < 20 || pulse > 300) {
                ToastUtil.showErrorToast(this, "Invalid pulse.")
            } else {
                mEtPulse.setText(pulse.toString())
                pulseDialog.dismiss()
            }
        }

        mBtnCancel.setOnClickListener {
            pulseDialog.dismiss()
        }

        val window = pulseDialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    private fun showRASDialog() {

        rasDialog = Dialog(this)
        rasDialog.setContentView(R.layout.dialog_respiration)
        rasDialog.setCancelable(false)
        rasDialog.show()

        val mPicker1 = rasDialog.findViewById(R.id.picker1) as NumberPicker
        val mBtnDone = rasDialog.findViewById(R.id.btn_done) as Button
        val mBtnCancel = rasDialog.findViewById(R.id.btn_cancel) as Button

        mPicker1.maxValue = 12
        mPicker1.minValue = 0

        mBtnDone.setOnClickListener {
            mEtRasScore.setText(mPicker1.value.toString())
            rasDialog.dismiss()
        }

        mBtnCancel.setOnClickListener {
            rasDialog.dismiss()
        }

        val window = rasDialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    @SuppressLint("SetTextI18n")
    private fun showSpO2Dialog() {

        spo2Dialog = Dialog(this)
        spo2Dialog.setContentView(R.layout.dialog_respiration)
        spo2Dialog.setCancelable(false)
        spo2Dialog.show()

        val mPicker1 = spo2Dialog.findViewById(R.id.picker1) as NumberPicker
        val mBtnDone = spo2Dialog.findViewById(R.id.btn_done) as Button
        val mBtnCancel = spo2Dialog.findViewById(R.id.btn_cancel) as Button

        mPicker1.maxValue = 100
        mPicker1.minValue = 10

        mPicker1.setFormatter { value -> "$value%" }

        mBtnDone.setOnClickListener {
            mEtSp02.setText(mPicker1.value.toString() + "%")
            spo2Dialog.dismiss()
        }

        mBtnCancel.setOnClickListener {
            spo2Dialog.dismiss()
        }

        val window = spo2Dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    @SuppressLint("SetTextI18n")
    private fun showRespirationDialog() {

        respirationDialog = Dialog(this)
        respirationDialog.setContentView(R.layout.dialog_respiration)
        respirationDialog.setCancelable(false)
        respirationDialog.show()

        val mPicker1 = respirationDialog.findViewById(R.id.picker1) as NumberPicker
        val mBtnDone = respirationDialog.findViewById(R.id.btn_done) as Button
        val mBtnCancel = respirationDialog.findViewById(R.id.btn_cancel) as Button

        mPicker1.maxValue = 60
        mPicker1.minValue = 4

        mBtnDone.setOnClickListener {
            mEtRespiration.setText(mPicker1.value.toString())
            respirationDialog.dismiss()
        }

        mBtnCancel.setOnClickListener {
            respirationDialog.dismiss()
        }

        val window = respirationDialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    @SuppressLint("SetTextI18n")
    private fun showTemperatureDialog() {

        temperatureDialog = Dialog(this)
        temperatureDialog.setContentView(R.layout.dialog_temperature)
        temperatureDialog.setCancelable(false)
        temperatureDialog.show()

        val mPicker1 = temperatureDialog.findViewById(R.id.picker1) as NumberPicker
        val mPicker2 = temperatureDialog.findViewById(R.id.picker2) as NumberPicker
        val mPicker3 = temperatureDialog.findViewById(R.id.picker3) as NumberPicker
        val mPicker4 = temperatureDialog.findViewById(R.id.picker4) as NumberPicker
        val mBtnDone = temperatureDialog.findViewById(R.id.btn_done) as Button
        val mBtnCancel = temperatureDialog.findViewById(R.id.btn_cancel) as Button

        mPicker1.maxValue = 1
        mPicker1.minValue = 0
        mPicker2.maxValue = 9
        mPicker2.minValue = 0
        mPicker3.maxValue = 9
        mPicker3.minValue = 0
        mPicker4.maxValue = 9
        mPicker4.minValue = 0

        mBtnDone.setOnClickListener {
            val temp =
                (mPicker1.value.toString() + mPicker2.value.toString() + mPicker3.value.toString()).toInt()

            if (temp < 70 || temp > 110) {
                ToastUtil.showErrorToast(this, "Invalid Temperature")
            } else {
                mEtTemp.setText(temp.toString() + "." + mPicker4.value + " \u00B0F")
                temperatureDialog.dismiss()
            }
        }

        mBtnCancel.setOnClickListener {
            temperatureDialog.dismiss()
        }

        val window = temperatureDialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    private fun setData() {

        Log.e("key",prefrenceKey);

        when (intent.getIntExtra("position", -1)) {

            0 -> {
                if (mAppPreferences.getFirstTimeZoneData(prefrenceKey) != "") {
                    try {
                        dailyFlowSheetFormModel = Gson().fromJson<DailySheetFormModel>(
                            mAppPreferences.getFirstTimeZoneData(prefrenceKey),
                            object :
                                TypeToken<DailySheetFormModel?>() {}.type
                        ) as DailySheetFormModel

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        dismissLoader()
                    }
                } else {
                    dismissLoader()
                }
            }


            1 -> {
                if (mAppPreferences.getFirstTimeZoneData(prefrenceKey) != "") {
                    try {
                        dailyFlowSheetFormModel = Gson().fromJson<DailySheetFormModel>(
                            mAppPreferences.getFirstTimeZoneData(prefrenceKey),
                            object :
                                TypeToken<DailySheetFormModel?>() {}.type
                        ) as DailySheetFormModel

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        dismissLoader()
                    }
                } else {
                    dismissLoader()
                }
            }


            2 -> {
                if (mAppPreferences.getFirstTimeZoneData(prefrenceKey) != "") {
                    try {
                        dailyFlowSheetFormModel = Gson().fromJson<DailySheetFormModel>(
                            mAppPreferences.getFirstTimeZoneData(prefrenceKey),
                            object :
                                TypeToken<DailySheetFormModel?>() {}.type
                        ) as DailySheetFormModel

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        dismissLoader()
                    }
                } else {
                    dismissLoader()
                }
            }


            3 -> {
                if (mAppPreferences.getFirstTimeZoneData(prefrenceKey) != "") {
                    try {
                        dailyFlowSheetFormModel = Gson().fromJson<DailySheetFormModel>(
                            mAppPreferences.getFirstTimeZoneData(prefrenceKey),
                            object : TypeToken<DailySheetFormModel?>() {}.type
                        ) as DailySheetFormModel

                    } catch (e:java.lang.Exception) {
                        e.printStackTrace()
                        dismissLoader()
                    }
                } else {
                    dismissLoader()
                }
            }


            4 -> {
                if (mAppPreferences.getFirstTimeZoneData(prefrenceKey) != "") {
                    try {
                        dailyFlowSheetFormModel = Gson().fromJson<DailySheetFormModel>(
                            mAppPreferences.getFirstTimeZoneData(prefrenceKey),
                            object :
                                TypeToken<DailySheetFormModel?>() {}.type
                        ) as DailySheetFormModel

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        dismissLoader()
                    }
                } else {
                    dismissLoader()
                }
            }
        }

        if (dailyFlowSheetFormModel.temperature != "")
            mEtTemp.setText(dailyFlowSheetFormModel.temperature)
        if (dailyFlowSheetFormModel.pulse != "")
            mEtPulse.setText(dailyFlowSheetFormModel.pulse)
        if (dailyFlowSheetFormModel.spo2 != "")
            mEtSp02.setText(dailyFlowSheetFormModel.spo2)
        if (dailyFlowSheetFormModel.oxygen != "")
            mEtOxygen.setText(dailyFlowSheetFormModel.oxygen)
        if (dailyFlowSheetFormModel.respiration != "")
            mEtRespiration.setText(dailyFlowSheetFormModel.respiration)
        if (dailyFlowSheetFormModel.lungsound != "")
            mEtLungSound.setText(dailyFlowSheetFormModel.lungsound)
        if (dailyFlowSheetFormModel.workofbreathe != "")
            mEtWorkOfBreathe.setText(dailyFlowSheetFormModel.workofbreathe)
        if (dailyFlowSheetFormModel.tracheostomy != "")
            mEtTracheostomy.setText(dailyFlowSheetFormModel.tracheostomy)
        if (dailyFlowSheetFormModel.interventiontime != "")
            mEtInterventionTime.setText(dailyFlowSheetFormModel.interventiontime)
        if (dailyFlowSheetFormModel.rasscore != "")
            mEtRasScore.setText(dailyFlowSheetFormModel.rasscore)
        if (dailyFlowSheetFormModel.intervention != "")
            mEtIntervention.setText(dailyFlowSheetFormModel.intervention)
        if (dailyFlowSheetFormModel.quantity != "")
            mEtQuantity.setText(dailyFlowSheetFormModel.quantity)
        if (dailyFlowSheetFormModel.bloodGulcose != "")
            et_blood_glucose.setText(dailyFlowSheetFormModel.bloodGulcose)
        if (dailyFlowSheetFormModel.color != "")
            mEtColor.setText(dailyFlowSheetFormModel.color)
        if (dailyFlowSheetFormModel.consistency != "")
            mEtConsistency.setText(dailyFlowSheetFormModel.consistency)
        if (dailyFlowSheetFormModel.interventionoutcome != "")
            mEtInterventionOutcome.setText(dailyFlowSheetFormModel.interventionoutcome)
        if (dailyFlowSheetFormModel.gtubesite != "")
            mEtGTubeSite.setText(dailyFlowSheetFormModel.gtubesite)
        if (dailyFlowSheetFormModel.nutrition != "")
            mEtNutrition.setText(dailyFlowSheetFormModel.nutrition)
        if (dailyFlowSheetFormModel.input != "")
            mEtInput.setText(dailyFlowSheetFormModel.input)
        if (dailyFlowSheetFormModel.outcome != "")
            mEtOutcome.setText(dailyFlowSheetFormModel.outcome)
        if (dailyFlowSheetFormModel.position != "")
            mEtPosition.setText(dailyFlowSheetFormModel.position)
        if (dailyFlowSheetFormModel.assist != "")
        {
            if(dailyFlowSheetFormModel.assist.equals("Full Assist")) {
                radioButton1!!.setChecked(true)
            }
            else if(dailyFlowSheetFormModel.assist.equals("Partial Assist"))
            {
                radioButton2!!.setChecked(true)
            }
            else{
                radioButton3!!.setChecked(true)
            }
        }
        if (dailyFlowSheetFormModel.braces != "")
        {
            if(dailyFlowSheetFormModel.braces.equals("ON")) {
                radioButton4!!.setChecked(true)
            }
            else if(dailyFlowSheetFormModel.braces.equals("OFF"))
            {
                radioButton5!!.setChecked(true)
            }

        }
        if (dailyFlowSheetFormModel.activity != "")
            mEtActivity.setText(dailyFlowSheetFormModel.activity)


        if (dailyFlowSheetFormModel.stretched != "" && dailyFlowSheetFormModel.stretched.equals("Yes"))
            mEtStretched.setChecked(true)
        else
            mEtStretched.setChecked(false)

        if (dailyFlowSheetFormModel.medication != "" )
            mEtMedication.setText(dailyFlowSheetFormModel.medication)

        if (dailyFlowSheetFormModel.monitortpn != "")
            et_monitoring_tpn.setText(dailyFlowSheetFormModel.monitortpn)

        if (dailyFlowSheetFormModel.teach != "" && dailyFlowSheetFormModel.teach.equals("Yes"))
            et_teach.setChecked(true)
        else
            et_teach.setChecked(false)

        if (dailyFlowSheetFormModel.ventdecompressabd != "" && dailyFlowSheetFormModel.ventdecompressabd.equals("Yes"))
            et_vent_decompress_abd.setChecked(true)
        else
            et_vent_decompress_abd.setChecked(false)

        if (dailyFlowSheetFormModel.participating != "" && dailyFlowSheetFormModel.participating.equals("Yes"))
            et_participating.setChecked(true)
        else
            et_participating.setChecked(false)


        dismissLoader()

    }


    private fun initViews() {

        mTvTitle = findViewById(R.id.tv_title)
        mEtTemp = findViewById(R.id.et_temp)
        mEtPulse = findViewById(R.id.et_pulse)
        mEtSp02 = findViewById(R.id.et_sp02)
        mEtOxygen = findViewById(R.id.et_oxygen)
        mEtRespiration = findViewById(R.id.et_respiration)
        mEtLungSound = findViewById(R.id.et_lung_sound)
        mEtWorkOfBreathe = findViewById(R.id.et_work_of_breathe)
        mEtTracheostomy = findViewById(R.id.et_tracheostomy)
        mEtInterventionTime = findViewById(R.id.et_intervention_time)
        mEtRasScore = findViewById(R.id.et_ras_score)
        mEtIntervention = findViewById(R.id.et_intervention)
        mEtQuantity = findViewById(R.id.et_quantity)
        mEtColor = findViewById(R.id.et_color)
        mEtConsistency = findViewById(R.id.et_consistency)
        mEtInterventionOutcome = findViewById(R.id.et_intervention_outcome)
        et_blood_glucose = findViewById(R.id.et_blood_glucose)
        mEtGTubeSite = findViewById(R.id.et_g_tube_site)
        mEtNutrition = findViewById(R.id.et_nutrition)
        mEtInput = findViewById(R.id.et_input)
        mEtOutcome = findViewById(R.id.et_outcome)
        mEtPosition = findViewById(R.id.et_position)
        mEtAssist = findViewById(R.id.et_assist)
        mEtParticipating = findViewById(R.id.et_participating)
        mEtActivity = findViewById(R.id.et_Activity)
        mEtBraces = findViewById(R.id.et_braces)
        mEtStretched = findViewById(R.id.et_stretched)
        mEtMedication = findViewById(R.id.et_medication)
        mEtReportGivenTo = findViewById(R.id.et_report_given_to)
        mBtnSave = findViewById(R.id.btn_save)
        mBtnChangeTimeZone = findViewById(R.id.btn_change_time_zone)
        et_vent_decompress_abd = findViewById(R.id.et_vent_decompress_abd)
        et_teach = findViewById(R.id.et_teach)
        txtOption=findViewById(R.id.txtOption)

        mAppPreferences = AppPreferences.getInstance(this)
        timeZoneList = mAppPreferences.getTimeZone().split("@") as ArrayList<String>
        mTvTitle.text = timeZoneList[intent.getIntExtra("position", 0)]

        mEtTemp.isFocusable = false
        mEtRespiration.isFocusable = false
        mEtSp02.isFocusable = false
        mEtRasScore.isFocusable = false
        mEtPulse.isFocusable = false
        mEtInterventionTime.isFocusable = false

        txtOption.setOnClickListener{
            val popupMenu = PopupMenu(this@DailyFlowSheetTimeZoneForm, txtOption)

            // Inflating popup menu from popup_menu.xml file
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                // Toast message on menu item clicked
                val value=intent.getIntExtra("value",0)
                when (menuItem.itemId) {

                    R.id.copy1 -> {
                        try {
                            dailyFlowSheetFormModel = Gson().fromJson<DailySheetFormModel>(
                                mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data"+value+"1"),
                                object :
                                    TypeToken<DailySheetFormModel?>() {}.type
                            ) as DailySheetFormModel

                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                            dismissLoader()
                        }
                        bindPast()

                        true
                    }
                    R.id.copy2 -> {

                        try {
                            dailyFlowSheetFormModel = Gson().fromJson<DailySheetFormModel>(
                                mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data"+value+"2"),
                                object :
                                    TypeToken<DailySheetFormModel?>() {}.type
                            ) as DailySheetFormModel

                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                            dismissLoader()
                        }
                        bindPast()
                        true
                    }
                    R.id.copy3 -> {

                        try {
                            dailyFlowSheetFormModel = Gson().fromJson<DailySheetFormModel>(
                                mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data"+value+"3"),
                                object :
                                    TypeToken<DailySheetFormModel?>() {}.type
                            ) as DailySheetFormModel

                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                            dismissLoader()
                        }
                        bindPast()
                        true
                    }
                    R.id.copy4 -> {

                        try {
                            dailyFlowSheetFormModel = Gson().fromJson<DailySheetFormModel>(
                                mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data"+value+"4"),
                                object :
                                    TypeToken<DailySheetFormModel?>() {}.type
                            ) as DailySheetFormModel

                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                            dismissLoader()
                        }
                        bindPast()
                        true
                    }

                    else -> super.onOptionsItemSelected(menuItem)
                }
                true
            }
            // Showing the popup menu
            popupMenu.show()
        }
    }

    private fun bindPast() {
        if (dailyFlowSheetFormModel.intervention != "")
            mEtIntervention.setText(dailyFlowSheetFormModel.intervention)

        if (dailyFlowSheetFormModel.workofbreathe != "")
            mEtWorkOfBreathe.setText(dailyFlowSheetFormModel.workofbreathe)

        if (dailyFlowSheetFormModel.interventionoutcome != "")
            mEtInterventionOutcome.setText(dailyFlowSheetFormModel.interventionoutcome)

        if (dailyFlowSheetFormModel.tracheostomy != "")
            mEtTracheostomy.setText(dailyFlowSheetFormModel.tracheostomy)
        if (dailyFlowSheetFormModel.quantity != "")
            mEtQuantity.setText(dailyFlowSheetFormModel.quantity)
        if (dailyFlowSheetFormModel.color != "")
            mEtColor.setText(dailyFlowSheetFormModel.color)

        if (dailyFlowSheetFormModel.consistency != "")
            mEtConsistency.setText(dailyFlowSheetFormModel.consistency)

        if (dailyFlowSheetFormModel.nutrition != "")
            mEtNutrition.setText(dailyFlowSheetFormModel.nutrition)

        if (dailyFlowSheetFormModel.gtubesite != "")
            mEtGTubeSite.setText(dailyFlowSheetFormModel.gtubesite)



    }


}

