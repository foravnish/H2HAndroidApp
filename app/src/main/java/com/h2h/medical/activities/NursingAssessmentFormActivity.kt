package com.h2h.medical.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.text.*
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspose.pdf.CryptoAlgorithm
import com.aspose.pdf.Document
import com.aspose.pdf.GoToAction
import com.aspose.pdf.OutlineItemCollection
import com.aspose.pdf.facades.DocumentPrivilege
import com.crmtrail.ViewModel.CommentViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.h2h.medical.R
import com.h2h.medical.Volley.BuildConfig
import com.h2h.medical.helper.PermissionHelper
import com.h2h.medical.helper.SignatureView
import com.h2h.medical.models.CommentData
import com.h2h.medical.models.DailySheetModel
import com.h2h.medical.models.NursingAssessmentSheetModel
import com.h2h.medical.room.SqliteDb
import com.h2h.medical.utils.AppPreferences
import com.h2h.medical.utils.AppUtils
import com.h2h.medical.utils.DateInputMask
import com.h2h.medical.utils.ToastUtil
import com.kamdhenuteam.Adapter.CommentsListAdapter
import kotlinx.android.synthetic.main.activity_nursing_assessment_form.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


@RequiresApi(Build.VERSION_CODES.KITKAT)
class NursingAssessmentFormActivity : BaseActivity() {


    private lateinit var mEtName: EditText
    private lateinit var mEtDob: EditText
    private lateinit var mEtDate: EditText
    private lateinit var mEtExceptionNeurological: EditText
    private lateinit var mEtExceptionPulmonary: EditText
    private lateinit var mEtO2Pulmonary: EditText
    private lateinit var mEtExceptionCardiovascular: EditText
    private lateinit var mEtHeartRhythmCardiovascular: EditText
    private lateinit var mEtEdemaCardiovascular: EditText
    private lateinit var mEtCapillaryRefillCardiovascular: EditText
    private lateinit var mEtExceptionGastrointestinal: EditText
    private lateinit var mEtBowelSoundsGastrointestinal: EditText
    private lateinit var mEtExceptionGenitoUrinary: EditText
    private lateinit var et_exception_gen_uni:EditText
    private lateinit var mEtUrineOrderGenitoUrinary: EditText
    private lateinit var mEtUrineColorGenitoUrinary: EditText
    private lateinit var mEtExceptionIntegumentary: EditText
    private lateinit var mEtBracesMusculoSkeletal: EditText
    private lateinit var mEtMobilityMusculoSkeletal: EditText
    private lateinit var mEtExceptionMusculoSkeletal: EditText
    private lateinit var mEtExceptionPsychosocial: EditText
    private lateinit var mEtExceptionPain: EditText
    private lateinit var mEtNursingNote: EditText


    private lateinit var mLlPain: LinearLayout
    private lateinit var mLlChildPain: LinearLayout
    private lateinit var mLlPsychosocial: LinearLayout
    private lateinit var mLlChildPsychosocial: LinearLayout
    private lateinit var mLlMusculoSkeletal: LinearLayout
    private lateinit var mLlChildMusculoSkeletal: LinearLayout
    private lateinit var mLlNeurological: LinearLayout
    private lateinit var mLlChildNeurological: LinearLayout
    private lateinit var mLlPulmonary: LinearLayout
    private lateinit var mLlChildPulmonary: LinearLayout
    private lateinit var mLlCardiovascular: LinearLayout
    private lateinit var mLlChildCardiovascular: LinearLayout
    private lateinit var mLlGastrointestinal: LinearLayout
    private lateinit var mLlChildGastrointestinal: LinearLayout
    private lateinit var mLlGenitoUrinary: LinearLayout
    private lateinit var mLlChildGenitoUrinary: LinearLayout
    private lateinit var mLlIntegumentary: LinearLayout
    private lateinit var mLlChildIntegumentary: LinearLayout


    private lateinit var mCbWnlNeurological: CheckBox
    private lateinit var mCbExceptionNeurological: CheckBox
    private lateinit var mCbWnlPulmonary: CheckBox
    private lateinit var mCbExceptionPulmonary: CheckBox
    private lateinit var mCbTracheostomyPulmonary: CheckBox
    private lateinit var mCbO2Pulmonary: CheckBox
    private lateinit var mCbWnlCardiovascular: CheckBox
    private lateinit var mCbExceptionCardiovascular: CheckBox
    private lateinit var mCbHeartRhythmCardiovascular: CheckBox
    private lateinit var mCbEdemaCardiovascular: CheckBox
    private lateinit var mCbCapillaryRefillCardiovascular: CheckBox
    private lateinit var mCbFatigueCardiovascular: CheckBox
    private lateinit var mCbDizzinessCardiovascular: CheckBox
    private lateinit var mCbWnlGastrointestinal: CheckBox
    private lateinit var mCbExceptionGastrointestinal: CheckBox
    private lateinit var mCbBowelSoundsGastrointestinal: CheckBox
    private lateinit var mCbWnlGenitoUrinary: CheckBox

    private lateinit var cb_exceptions_ch: CheckBox
    private lateinit var cb_incontinent_ch: CheckBox
    private lateinit var cb_urgency_ch: CheckBox

    private lateinit var mCbExceptionGenitoUrinary: CheckBox
    private lateinit var mCbWnlIntegumentary: CheckBox
    private lateinit var mCbExceptionIntegumentary: CheckBox
    private lateinit var mCbWnlMusculoSkeletal: CheckBox
    private lateinit var mCbExceptionMusculoSkeletal: CheckBox
    private lateinit var mCbBracesMusculoSkeletal: CheckBox
    private lateinit var mCbMobilityMusculoSkeletal: CheckBox
    private lateinit var mCbWnlPsychosocial: CheckBox
    private lateinit var mCbExceptionPsychosocial: CheckBox
    private lateinit var mCbWnlPain: CheckBox
    private lateinit var mCbExceptionPain: CheckBox
    private lateinit var mCbGTubeGastrointestinal: CheckBox
    private lateinit var mCbIncontinentGastrointestinal: CheckBox
    private lateinit var mCbNauseaGastrointestinal: CheckBox
    private lateinit var mCbVomitingGastrointestinal: CheckBox
    private lateinit var mCbConstipationGastrointestinal: CheckBox
    private lateinit var mCbDiarrheaGastrointestinal: CheckBox
    private lateinit var mCbBruisingIntegumentary: CheckBox
    private lateinit var mCbFragileSkinIntegumentary: CheckBox
    private lateinit var mCbRashIntegumentary: CheckBox
    private lateinit var mCbPetechiaeIntegumentary: CheckBox
    private lateinit var mCbAcneIntegumentary: CheckBox
    private lateinit var mCbAbrasionIntegumentary: CheckBox

    private lateinit var mViewSignature: LinearLayout
    private lateinit var mIvSignature: ImageView
    private lateinit var signatureDialog: Dialog
    private var signatureBitmap: Bitmap =
        Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888)

    private lateinit var mBtnSubmit: Button
    private lateinit var mBtnSubmitLater: Button

    private var myCalendar: Calendar = Calendar.getInstance()
    private lateinit var mPermissionHelper: PermissionHelper
    private lateinit var permissions: Array<String>

    private lateinit var pdfDocument: PdfDocument
    private lateinit var myPageInfo: PdfDocument.PageInfo
    private lateinit var page: PdfDocument.Page

    private var paint = Paint()  //Paint for normal text
    private var labelPaint = Paint()  //Paint for label like name,dob,date
    private var headerPaint = Paint()  //Paint for headings
    private var subHeadingPaint = Paint()    //Paint for sub heading
    private var strokePaint = Paint()   //Stroke paint for rectangle border
    private var linePaint = Paint()  //Paint for line

    private var xPosition = 20.toFloat()    //Default x axis padding for PDF
    private var yPosition = 40.toFloat()    //Y axis padding for first line only
    private var yLeftPosition = 40.toFloat()
    private var yRightPosition = 40.toFloat()
    private var count = 1
    lateinit var mDbModel: CommentViewModel

    lateinit var objDB: SqliteDb
    val random = Random()
    var msgId = random.nextInt(99999999 - 10000000) + 10000000
    lateinit var mAdapter: CommentsListAdapter

    private var nurssingAssessmentModel = NursingAssessmentSheetModel()
    private lateinit var mAppPreferences: AppPreferences
    private  var prefrenceKey: String="nurssing_assessment"

    private val datePickerDialog =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = "MM/dd/yyyy"
            val sdf = SimpleDateFormat(dateFormat, Locale.US)
            mEtDate.setText(sdf.format(myCalendar.time))
        }


    companion object {
        fun open(myActivity: Activity) {
            val intent = Intent(myActivity, NursingAssessmentFormActivity::class.java)
            myActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nursing_assessment_form)

        mDbModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(CommentViewModel::class.java)
        mDbModel.init(this)

        objDB = SqliteDb(this)

        initViews()
        initListeners()
        validateCheckBox()
        mLlNeurological.performClick()

        setData()
//        mEtNursingNote.setText(""+AppPreferences.getInstance(this@NursingAssessmentFormActivity).getUserName()+"\n")


    }

    private fun setData() {

        if (mAppPreferences.getNursingAssessment(prefrenceKey) != "") {

            try {
                nurssingAssessmentModel = Gson().fromJson<NursingAssessmentSheetModel>(
                    mAppPreferences.getNursingAssessment(prefrenceKey), object :
                        TypeToken<NursingAssessmentSheetModel?>() {}.type
                ) as NursingAssessmentSheetModel

                mEtName.setText(nurssingAssessmentModel.name)
                mEtDob.setText(nurssingAssessmentModel.dob)
                mEtDate.setText(nurssingAssessmentModel.date)
                mEtNursingNote.setText(nurssingAssessmentModel.daily_notes)
                mEtExceptionNeurological.setText(nurssingAssessmentModel.mEtExceptionNeurological)
                mEtO2Pulmonary.setText(nurssingAssessmentModel.mEtO2Pulmonary)
                mEtBowelSoundsGastrointestinal.setText(nurssingAssessmentModel.mEtBowelSoundsGastrointestinal)
                mEtExceptionPulmonary.setText(nurssingAssessmentModel.mEtExceptionPulmonary)
                mEtExceptionCardiovascular.setText(nurssingAssessmentModel.mEtExceptionCardiovascular)
                mEtHeartRhythmCardiovascular.setText(nurssingAssessmentModel.mEtHeartRhythmCardiovascular)
                mEtEdemaCardiovascular.setText(nurssingAssessmentModel.mEtEdemaCardiovascular)
                mEtCapillaryRefillCardiovascular.setText(nurssingAssessmentModel.mEtCapillaryRefillCardiovascular)
                mEtExceptionGastrointestinal.setText(nurssingAssessmentModel.mEtExceptionGastrointestinal)
                et_exception_gen_uni.setText(nurssingAssessmentModel.et_exception_gen_uni)
                mEtExceptionGenitoUrinary.setText(nurssingAssessmentModel.mEtExceptionGenitoUrinary)
                mEtUrineOrderGenitoUrinary.setText(nurssingAssessmentModel.mEtUrineOrderGenitoUrinary)
                mEtUrineColorGenitoUrinary.setText(nurssingAssessmentModel.mEtUrineColorGenitoUrinary)
                mEtExceptionIntegumentary.setText(nurssingAssessmentModel.mEtExceptionIntegumentary)
                mEtExceptionMusculoSkeletal.setText(nurssingAssessmentModel.mEtExceptionMusculoSkeletal)
                mEtBracesMusculoSkeletal.setText(nurssingAssessmentModel.mEtBracesMusculoSkeletal)
                mEtMobilityMusculoSkeletal.setText(nurssingAssessmentModel.mEtMobilityMusculoSkeletal)
                mEtExceptionPsychosocial.setText(nurssingAssessmentModel.mEtExceptionPsychosocial)
                mEtExceptionPain.setText(nurssingAssessmentModel.mEtExceptionPain)
                if(nurssingAssessmentModel.mCbWnlNeurological)
                    mCbWnlNeurological.isChecked=true
                else mCbWnlNeurological.isChecked=false
                if(nurssingAssessmentModel.mCbExceptionNeurological)
                    mCbExceptionNeurological.isChecked=true
                else mCbExceptionNeurological.isChecked=false

                if(nurssingAssessmentModel.mCbWnlPulmonary)
                    mCbWnlPulmonary.isChecked=true
                else mCbWnlPulmonary.isChecked=false
                if(nurssingAssessmentModel.mCbExceptionPulmonary)
                    mCbExceptionPulmonary.isChecked=true
                else mCbExceptionPulmonary.isChecked=false
                if(nurssingAssessmentModel.mCbTracheostomyPulmonary)
                    mCbTracheostomyPulmonary.isChecked=true
                else mCbTracheostomyPulmonary.isChecked=false
                if(nurssingAssessmentModel.mCbO2Pulmonary)
                    mCbO2Pulmonary.isChecked=true
                else mCbO2Pulmonary.isChecked=false


                if(nurssingAssessmentModel.mCbWnlCardiovascular)
                    mCbWnlCardiovascular.isChecked=true
                else mCbWnlCardiovascular.isChecked=false
                if(nurssingAssessmentModel.mCbExceptionCardiovascular)
                    mCbExceptionCardiovascular.isChecked=true
                else mCbExceptionCardiovascular.isChecked=false
                if(nurssingAssessmentModel.mCbHeartRhythmCardiovascular)
                    mCbHeartRhythmCardiovascular.isChecked=true
                else mCbHeartRhythmCardiovascular.isChecked=false
                if(nurssingAssessmentModel.mCbEdemaCardiovascular)
                    mCbEdemaCardiovascular.isChecked=true
                else mCbEdemaCardiovascular.isChecked=false
                if(nurssingAssessmentModel.mCbCapillaryRefillCardiovascular)
                    mCbCapillaryRefillCardiovascular.isChecked=true
                else mCbCapillaryRefillCardiovascular.isChecked=false
                if(nurssingAssessmentModel.mCbFatigueCardiovascular)
                    mCbFatigueCardiovascular.isChecked=true
                else mCbFatigueCardiovascular.isChecked=false
                if(nurssingAssessmentModel.mCbDizzinessCardiovascular)
                    mCbDizzinessCardiovascular.isChecked=true
                else mCbDizzinessCardiovascular.isChecked=false

                if(nurssingAssessmentModel.mCbWnlGastrointestinal)
                    mCbWnlGastrointestinal.isChecked=true
                else mCbWnlGastrointestinal.isChecked=false
                if(nurssingAssessmentModel.mCbExceptionGastrointestinal)
                    mCbExceptionGastrointestinal.isChecked=true
                else mCbExceptionGastrointestinal.isChecked=false
                if(nurssingAssessmentModel.mCbGTubeGastrointestinal)
                    mCbGTubeGastrointestinal.isChecked=true
                else mCbGTubeGastrointestinal.isChecked=false
                if(nurssingAssessmentModel.mCbIncontinentGastrointestinal)
                    mCbIncontinentGastrointestinal.isChecked=true
                else mCbIncontinentGastrointestinal.isChecked=false
                if(nurssingAssessmentModel.mCbNauseaGastrointestinal)
                    mCbNauseaGastrointestinal.isChecked=true
                else mCbNauseaGastrointestinal.isChecked=false
                if(nurssingAssessmentModel.mCbVomitingGastrointestinal)
                    mCbVomitingGastrointestinal.isChecked=true
                else mCbVomitingGastrointestinal.isChecked=false
                if(nurssingAssessmentModel.mCbConstipationGastrointestinal)
                    mCbConstipationGastrointestinal.isChecked=true
                else mCbConstipationGastrointestinal.isChecked=false
                if(nurssingAssessmentModel.mCbDiarrheaGastrointestinal)
                    mCbDiarrheaGastrointestinal.isChecked=true
                else mCbDiarrheaGastrointestinal.isChecked=false
                if(nurssingAssessmentModel.mCbBowelSoundsGastrointestinal)
                    mCbBowelSoundsGastrointestinal.isChecked=true
                else mCbBowelSoundsGastrointestinal.isChecked=false

                if(nurssingAssessmentModel.mCbWnlGenitoUrinary)
                    mCbWnlGenitoUrinary.isChecked=true
                else mCbWnlGenitoUrinary.isChecked=false
                if(nurssingAssessmentModel.cb_exceptions_ch)
                    cb_exceptions_ch.isChecked=true
                else cb_exceptions_ch.isChecked=false
                if(nurssingAssessmentModel.cb_incontinent_ch)
                    cb_incontinent_ch.isChecked=true
                else cb_incontinent_ch.isChecked=false
                if(nurssingAssessmentModel.cb_urgency_ch)
                    cb_urgency_ch.isChecked=true
                else cb_urgency_ch.isChecked=false
                if(nurssingAssessmentModel.mCbExceptionGenitoUrinary)
                    mCbExceptionGenitoUrinary.isChecked=true
                else mCbExceptionGenitoUrinary.isChecked=false

                if(nurssingAssessmentModel.mCbWnlIntegumentary)
                    mCbWnlIntegumentary.isChecked=true
                else mCbWnlIntegumentary.isChecked=false
                if(nurssingAssessmentModel.mCbBruisingIntegumentary)
                    mCbBruisingIntegumentary.isChecked=true
                else mCbBruisingIntegumentary.isChecked=false
                if(nurssingAssessmentModel.mCbFragileSkinIntegumentary)
                    mCbFragileSkinIntegumentary.isChecked=true
                else mCbFragileSkinIntegumentary.isChecked=false
                if(nurssingAssessmentModel.mCbRashIntegumentary)
                    mCbRashIntegumentary.isChecked=true
                else mCbRashIntegumentary.isChecked=false
                if(nurssingAssessmentModel.mCbPetechiaeIntegumentary)
                    mCbPetechiaeIntegumentary.isChecked=true
                else mCbPetechiaeIntegumentary.isChecked=false
                if(nurssingAssessmentModel.mCbAcneIntegumentary)
                    mCbAcneIntegumentary.isChecked=true
                else mCbAcneIntegumentary.isChecked=false
                if(nurssingAssessmentModel.mCbAbrasionIntegumentary)
                    mCbAbrasionIntegumentary.isChecked=true
                else mCbAbrasionIntegumentary.isChecked=false
                if(nurssingAssessmentModel.mCbExceptionIntegumentary)
                    mCbExceptionIntegumentary.isChecked=true
                else mCbExceptionIntegumentary.isChecked=false

                if(nurssingAssessmentModel.mCbWnlMusculoSkeletal)
                    mCbWnlMusculoSkeletal.isChecked=true
                else mCbWnlMusculoSkeletal.isChecked=false
                if(nurssingAssessmentModel.mCbExceptionMusculoSkeletal)
                    mCbExceptionMusculoSkeletal.isChecked=true
                else mCbExceptionMusculoSkeletal.isChecked=false
                if(nurssingAssessmentModel.mCbBracesMusculoSkeletal)
                    mCbBracesMusculoSkeletal.isChecked=true
                else mCbBracesMusculoSkeletal.isChecked=false
                if(nurssingAssessmentModel.mCbMobilityMusculoSkeletal)
                    mCbMobilityMusculoSkeletal.isChecked=true
                else mCbMobilityMusculoSkeletal.isChecked=false

                if(nurssingAssessmentModel.mCbWnlPsychosocial)
                    mCbWnlPsychosocial.isChecked=true
                else mCbWnlPsychosocial.isChecked=false
                if(nurssingAssessmentModel.mCbExceptionPsychosocial)
                    mCbExceptionPsychosocial.isChecked=true
                else mCbExceptionPsychosocial.isChecked=false


                if(nurssingAssessmentModel.mCbWnlPain)
                    mCbWnlPain.isChecked=true
                else mCbWnlPain.isChecked=false
                if(nurssingAssessmentModel.mCbExceptionPain)
                    mCbExceptionPain.isChecked=true
                else mCbExceptionPain.isChecked=false


                
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

    }


    private fun validateCheckBox() {

        mCbWnlNeurological.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbExceptionNeurological.isChecked = false
            }
        }

        mCbExceptionNeurological.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlNeurological.isChecked = false
            }
        }


        mCbWnlPulmonary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbO2Pulmonary.isChecked = false
                mCbExceptionPulmonary.isChecked = false
                mCbTracheostomyPulmonary.isChecked = false
            }
        }

        mCbO2Pulmonary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlPulmonary.isChecked = false
            }
        }

        mCbExceptionPulmonary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlPulmonary.isChecked = false
            }
        }

        mCbTracheostomyPulmonary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlPulmonary.isChecked = false
            }
        }

        mCbWnlCardiovascular.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbExceptionCardiovascular.isChecked = false
                mCbHeartRhythmCardiovascular.isChecked = false
                mCbEdemaCardiovascular.isChecked = false
                mCbCapillaryRefillCardiovascular.isChecked = false
                mCbFatigueCardiovascular.isChecked = false
                mCbDizzinessCardiovascular.isChecked = false
            }
        }

        mCbExceptionCardiovascular.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlCardiovascular.isChecked = false
            }
        }

        mCbHeartRhythmCardiovascular.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlCardiovascular.isChecked = false
            }
        }

        mCbEdemaCardiovascular.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlCardiovascular.isChecked = false
            }
        }

        mCbCapillaryRefillCardiovascular.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlCardiovascular.isChecked = false
            }
        }

        mCbFatigueCardiovascular.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlCardiovascular.isChecked = false
            }
        }

        mCbDizzinessCardiovascular.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlCardiovascular.isChecked = false
            }
        }

        mCbWnlGastrointestinal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbExceptionGastrointestinal.isChecked = false
                mCbBowelSoundsGastrointestinal.isChecked = false
                mCbGTubeGastrointestinal.isChecked = false
                mCbIncontinentGastrointestinal.isChecked = false
                mCbNauseaGastrointestinal.isChecked = false
                mCbVomitingGastrointestinal.isChecked = false
                mCbConstipationGastrointestinal.isChecked = false
                mCbDiarrheaGastrointestinal.isChecked = false
            }
        }

        mCbGTubeGastrointestinal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlGastrointestinal.isChecked = false
            }
        }

        mCbIncontinentGastrointestinal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlGastrointestinal.isChecked = false
            }
        }

        mCbNauseaGastrointestinal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlGastrointestinal.isChecked = false
            }
        }

        mCbVomitingGastrointestinal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlGastrointestinal.isChecked = false
            }
        }

        mCbConstipationGastrointestinal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlGastrointestinal.isChecked = false
            }
        }

        mCbDiarrheaGastrointestinal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlGastrointestinal.isChecked = false
            }
        }

        mCbExceptionGastrointestinal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlGastrointestinal.isChecked = false
            }
        }

        mCbBowelSoundsGastrointestinal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlGastrointestinal.isChecked = false
            }
        }

        mCbWnlGenitoUrinary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbExceptionGenitoUrinary.isChecked = false
            }
        }

        mCbExceptionGenitoUrinary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlGenitoUrinary.isChecked = false
            }
        }

//        mCbExceptionGenitoUrinary.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                mCbExceptionGenitoUrinary.isChecked = false
//            }
//        }

        mCbWnlIntegumentary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbExceptionIntegumentary.isChecked = false
                mCbBruisingIntegumentary.isChecked = false
                mCbFragileSkinIntegumentary.isChecked = false
                mCbRashIntegumentary.isChecked = false
                mCbPetechiaeIntegumentary.isChecked = false
                mCbAcneIntegumentary.isChecked = false
                mCbAbrasionIntegumentary.isChecked = false
            }
        }

        mCbBruisingIntegumentary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlIntegumentary.isChecked = false
            }
        }

        mCbFragileSkinIntegumentary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlIntegumentary.isChecked = false
            }
        }

        mCbRashIntegumentary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlIntegumentary.isChecked = false
            }
        }

        mCbPetechiaeIntegumentary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlIntegumentary.isChecked = false
            }
        }

        mCbAcneIntegumentary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlIntegumentary.isChecked = false
            }
        }

        mCbAbrasionIntegumentary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlIntegumentary.isChecked = false
            }
        }

        mCbExceptionIntegumentary.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlIntegumentary.isChecked = false
            }
        }

        mCbWnlMusculoSkeletal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbExceptionMusculoSkeletal.isChecked = false
                mCbBracesMusculoSkeletal.isChecked = false
                mCbMobilityMusculoSkeletal.isChecked = false
            }
        }

        mCbExceptionMusculoSkeletal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlMusculoSkeletal.isChecked = false
//                mCbBracesMusculoSkeletal.isChecked = false
//                mCbMobilityMusculoSkeletal.isChecked = false
            }
        }

        mCbBracesMusculoSkeletal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlMusculoSkeletal.isChecked = false
//                mCbExceptionMusculoSkeletal.isChecked = false
//                mCbMobilityMusculoSkeletal.isChecked = false
            }
        }

        mCbMobilityMusculoSkeletal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlMusculoSkeletal.isChecked = false
//                mCbExceptionMusculoSkeletal.isChecked = false
//                mCbBracesMusculoSkeletal.isChecked = false
            }
        }

        mCbWnlPsychosocial.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbExceptionPsychosocial.isChecked = false
            }
        }

        mCbExceptionPsychosocial.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlPsychosocial.isChecked = false
            }
        }

        mCbWnlPain.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbExceptionPain.isChecked = false
            }
        }

        mCbExceptionPain.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCbWnlPain.isChecked = false
            }
        }
    }


    private fun initListeners() {

        mLlPain.setOnClickListener {
            if (mLlChildPain.visibility == View.VISIBLE) {
                mLlChildPain.visibility = View.GONE
            } else {
                mLlChildPain.visibility = View.VISIBLE
                mLlChildPsychosocial.visibility = View.GONE
                mLlChildMusculoSkeletal.visibility = View.GONE
                mLlChildNeurological.visibility = View.GONE
                mLlChildPulmonary.visibility = View.GONE
                mLlChildCardiovascular.visibility = View.GONE
                mLlChildGastrointestinal.visibility = View.GONE
                mLlChildGenitoUrinary.visibility = View.GONE
                mLlChildIntegumentary.visibility = View.GONE
            }
        }

        mLlPsychosocial.setOnClickListener {
            if (mLlChildPsychosocial.visibility == View.VISIBLE) {
                mLlChildPsychosocial.visibility = View.GONE
            } else {
                mLlChildPain.visibility = View.GONE
                mLlChildPsychosocial.visibility = View.VISIBLE
                mLlChildMusculoSkeletal.visibility = View.GONE
                mLlChildNeurological.visibility = View.GONE
                mLlChildPulmonary.visibility = View.GONE
                mLlChildCardiovascular.visibility = View.GONE
                mLlChildGastrointestinal.visibility = View.GONE
                mLlChildGenitoUrinary.visibility = View.GONE
                mLlChildIntegumentary.visibility = View.GONE
            }
        }

        mLlMusculoSkeletal.setOnClickListener {
            if (mLlChildMusculoSkeletal.visibility == View.VISIBLE) {
                mLlChildMusculoSkeletal.visibility = View.GONE
            } else {
                mLlChildPain.visibility = View.GONE
                mLlChildPsychosocial.visibility = View.GONE
                mLlChildMusculoSkeletal.visibility = View.VISIBLE
                mLlChildNeurological.visibility = View.GONE
                mLlChildPulmonary.visibility = View.GONE
                mLlChildCardiovascular.visibility = View.GONE
                mLlChildGastrointestinal.visibility = View.GONE
                mLlChildGenitoUrinary.visibility = View.GONE
                mLlChildIntegumentary.visibility = View.GONE
            }
        }

        mLlNeurological.setOnClickListener {
            if (mLlChildNeurological.visibility == View.VISIBLE) {
                mLlChildNeurological.visibility = View.GONE
            } else {
                mLlChildPain.visibility = View.GONE
                mLlChildPsychosocial.visibility = View.GONE
                mLlChildMusculoSkeletal.visibility = View.GONE
                mLlChildNeurological.visibility = View.VISIBLE
                mLlChildPulmonary.visibility = View.GONE
                mLlChildCardiovascular.visibility = View.GONE
                mLlChildGastrointestinal.visibility = View.GONE
                mLlChildGenitoUrinary.visibility = View.GONE
                mLlChildIntegumentary.visibility = View.GONE
            }
        }

        mLlPulmonary.setOnClickListener {
            if (mLlChildPulmonary.visibility == View.VISIBLE) {
                mLlChildPulmonary.visibility = View.GONE
            } else {
                mLlChildPain.visibility = View.GONE
                mLlChildPsychosocial.visibility = View.GONE
                mLlChildMusculoSkeletal.visibility = View.GONE
                mLlChildNeurological.visibility = View.GONE
                mLlChildPulmonary.visibility = View.VISIBLE
                mLlChildCardiovascular.visibility = View.GONE
                mLlChildGastrointestinal.visibility = View.GONE
                mLlChildGenitoUrinary.visibility = View.GONE
                mLlChildIntegumentary.visibility = View.GONE
            }
        }

        mLlCardiovascular.setOnClickListener {
            if (mLlChildCardiovascular.visibility == View.VISIBLE) {
                mLlChildCardiovascular.visibility = View.GONE
            } else {
                mLlChildPain.visibility = View.GONE
                mLlChildPsychosocial.visibility = View.GONE
                mLlChildMusculoSkeletal.visibility = View.GONE
                mLlChildNeurological.visibility = View.GONE
                mLlChildPulmonary.visibility = View.GONE
                mLlChildCardiovascular.visibility = View.VISIBLE
                mLlChildGastrointestinal.visibility = View.GONE
                mLlChildGenitoUrinary.visibility = View.GONE
                mLlChildIntegumentary.visibility = View.GONE
            }
        }

        mLlGastrointestinal.setOnClickListener {
            if (mLlChildGastrointestinal.visibility == View.VISIBLE) {
                mLlChildGastrointestinal.visibility = View.GONE
            } else {
                mLlChildPain.visibility = View.GONE
                mLlChildPsychosocial.visibility = View.GONE
                mLlChildMusculoSkeletal.visibility = View.GONE
                mLlChildNeurological.visibility = View.GONE
                mLlChildPulmonary.visibility = View.GONE
                mLlChildCardiovascular.visibility = View.GONE
                mLlChildGastrointestinal.visibility = View.VISIBLE
                mLlChildGenitoUrinary.visibility = View.GONE
                mLlChildIntegumentary.visibility = View.GONE
            }
        }

        mLlGenitoUrinary.setOnClickListener {
            if (mLlChildGenitoUrinary.visibility == View.VISIBLE) {
                mLlChildGenitoUrinary.visibility = View.GONE
            } else {
                mLlChildPain.visibility = View.GONE
                mLlChildPsychosocial.visibility = View.GONE
                mLlChildMusculoSkeletal.visibility = View.GONE
                mLlChildNeurological.visibility = View.GONE
                mLlChildPulmonary.visibility = View.GONE
                mLlChildCardiovascular.visibility = View.GONE
                mLlChildGastrointestinal.visibility = View.GONE
                mLlChildGenitoUrinary.visibility = View.VISIBLE
                mLlChildIntegumentary.visibility = View.GONE
            }
        }

        mLlIntegumentary.setOnClickListener {
            if (mLlChildIntegumentary.visibility == View.VISIBLE) {
                mLlChildIntegumentary.visibility = View.GONE
            } else {
                mLlChildPain.visibility = View.GONE
                mLlChildPsychosocial.visibility = View.GONE
                mLlChildMusculoSkeletal.visibility = View.GONE
                mLlChildNeurological.visibility = View.GONE
                mLlChildPulmonary.visibility = View.GONE
                mLlChildCardiovascular.visibility = View.GONE
                mLlChildGastrointestinal.visibility = View.GONE
                mLlChildGenitoUrinary.visibility = View.GONE
                mLlChildIntegumentary.visibility = View.VISIBLE
            }
        }

        mViewSignature.setOnClickListener {
            showSignatureDialog()
        }

        mEtDate.setOnClickListener {
            DatePickerDialog(
                this,
                R.style.datePickerTheme,
                datePickerDialog,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        mBtnSubmit.setOnClickListener {
//            if (!mPermissionHelper.isPermissionGranted(android.Manifest.permission.READ_EXTERNAL_STORAGE) || !mPermissionHelper.isPermissionGranted(
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//            ) {
//                mPermissionHelper.requestPermission(
//                    permissions,
//                    "Storage permission required for uploading profile picture."
//                )
//            } else {
                if (valid()) {
                    var tabId="1"
                    objDB.insertCommentData(""+mEtNursingNote.text.toString(),""+msgId,tabId)

                    showLoader(this, "Generating pdf...")
                    Handler(Looper.getMainLooper()).postDelayed({
                        generatePDF()
                    }, 100)
                }
//            }
        }
    }

    private fun valid(): Boolean {
        var lastFour =0
        var firstTwo =0
        var mDate =0

        try {
            lastFour=mEtDob.text.toString().takeLast(4).toString().toInt()
            firstTwo=mEtDob.text.toString().take(2).toString().toInt()
            var firstFive=mEtDob.text.toString().take(5).toString()
            mDate=firstFive.toString().takeLast(2).toInt()
        } catch (e: Exception) {
        }
        return if (mEtName.text.toString().trim() == "") {
            ToastUtil.showErrorToast(this, "Patient name required.")
            false
        } else if (mEtName.text.toString().length<3) {
            ToastUtil.showErrorToast(this, "Patient name required longer than 3 characters.")
            false
        } else if (mEtDob.text.toString() == "") {
            ToastUtil.showErrorToast(this, "Patient date of birth required.")
            false
        } else if (lastFour<AppUtils.DOB_VALIDATION) {
            ToastUtil.showErrorToast(this@NursingAssessmentFormActivity, resources.getString(R.string.error_dob))
            false
        } else if (firstTwo>12) {
            ToastUtil.showErrorToast(this@NursingAssessmentFormActivity, resources.getString(R.string.error_year))
            false
        } else if (mDate>31) {
            ToastUtil.showErrorToast(this@NursingAssessmentFormActivity, resources.getString(R.string.error_date))
            false
        } else if (mEtDob.text.toString().length<10) {
            ToastUtil.showErrorToast(this, resources.getString(R.string.error_dd_mm_yyyy))
            false
        } else if (mEtDate.text.toString().trim() == "") {
            ToastUtil.showErrorToast(this, "Select date.")
            false
        } else if (signatureBitmap.width == 2) {
            ToastUtil.showErrorToast(this, "Confirm your signature.")
            false
        } else {
            true
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun generatePDF() {  //Design and generate pdf

        try {
            count = 1
            xPosition = 10.toFloat()
            yPosition = 40.toFloat()

            pdfDocument = PdfDocument()
            myPageInfo = PdfDocument.PageInfo.Builder(1500, 3000, 1).create()
            page = pdfDocument.startPage(myPageInfo) as PdfDocument.Page

            paint.textSize = paint.textSize * 2
            paint.color = Color.BLACK

            labelPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            labelPaint.strokeWidth = 2F
            labelPaint.textSize = labelPaint.textSize * 2
            labelPaint.color = Color.BLACK


            headerPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            headerPaint.strokeWidth = 2F
            headerPaint.textSize = headerPaint.textSize * 2
            headerPaint.color = Color.BLACK


            subHeadingPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            subHeadingPaint.textSize = subHeadingPaint.textSize * 2
            subHeadingPaint.color = Color.BLACK


            strokePaint.style = Paint.Style.STROKE
            strokePaint.strokeWidth = 2F
            strokePaint.color = Color.BLACK


            linePaint.isAntiAlias = true
            linePaint.color = Color.BLACK
            linePaint.strokeWidth = 3F

            val checkedBitmap: Bitmap = BitmapFactory.decodeResource(
                resources,
                R.drawable.check_mark
            )  // Convert drawable image to bitmap  //For checked checkbox
            val unCheckedBitmap: Bitmap =
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.uncheck_box
                )  //For unchecked checkbox


            createHeader()
            var endColor = -35f
            var topStart = 0f

            var strokePaint1 = Paint()
            endColor = -40f;
            topStart =  20f
            strokePaint1.strokeWidth = 120F
            strokePaint1.color = Color.parseColor("#ff0066")
            page.canvas.drawRect(
                -150f,
                topStart ,
                page.canvas.width + 1500F,
                endColor,
                strokePaint1
            )


            page.canvas.drawText("NURSING ASSESSMENT", xPosition, yPosition + 20, headerPaint)
            yPosition += (paint.descent() - paint.ascent()) + 15


            //*******    DESIGN NEUROLOGICAL BULLET POINTS    *********


            yLeftPosition = yPosition
            yRightPosition = yPosition

            designMainHeadingPoints(
                yPosition,
                labelPaint,
                getString(R.string.neurological)
            )


            yPosition += (paint.descent() - paint.ascent()) + 10
            yLeftPosition += (paint.descent() - paint.ascent()) + 10
            yRightPosition += (paint.descent() - paint.ascent()) + 10


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_alert_amp_oriented_x3)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbWnlNeurological.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.wnl)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.wnl)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_behaviour_appropriate_to_situation)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbExceptionNeurological.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.exceptions_extra) + " :  " + mEtExceptionNeurological.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.exceptions_extra)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_active_rom_of_all_extremities)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_speech_appropriate)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            designBulletPoints(yLeftPosition, paint, getString(R.string.u2022_perrl))
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_reflexes_intact_gag_swallow_blink_cough)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            setyAxisPosition()


            //*******    DESIGN PULMONARY BULLET POINTS   *******


            designMainHeadingPoints(
                yPosition,
                labelPaint,
                getString(R.string.pulmonary)
            )

            yPosition += (paint.descent() - paint.ascent()) + 10
            yLeftPosition += (paint.descent() - paint.ascent()) + 10
            yRightPosition += (paint.descent() - paint.ascent()) + 10


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_respirations_unlabored_amp_regular)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (mCbWnlPulmonary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.wnl)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.wnl)
                )

            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_bbs_clear)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (mCbExceptionPulmonary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.exceptions_extra) + " :  " + mEtExceptionPulmonary.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.exceptions_extra)
                )

            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_symmetrical_chest_expansion)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (mCbTracheostomyPulmonary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.tracheostomy)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.tracheostomy)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_nail_beds_pink)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (mCbO2Pulmonary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.o2_l_min_rate_cannula_mask) + " :  " + mEtO2Pulmonary.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.o2_l_min_rate_cannula_mask)
                )


            setyAxisPosition()


            //********     DESIGN CARDIOVASCULAR BULLET POINTS      *************


            designMainHeadingPoints(
                yPosition,
                labelPaint,
                getString(R.string.cardiovascular)
            )

            yPosition += (paint.descent() - paint.ascent()) + 10
            yLeftPosition += (paint.descent() - paint.ascent()) + 10
            yRightPosition += (paint.descent() - paint.ascent()) + 10


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_radial_pedial_pulses_regular_strong_amp_palpable_bilaterally)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbWnlCardiovascular.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.wnl)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.wnl)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_no_peripheral_edema)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbExceptionCardiovascular.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.exceptions_extra) + " :  " + mEtExceptionCardiovascular.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.exceptions_extra).replace(":", "")
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_cap_refill_3_secs)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbHeartRhythmCardiovascular.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.heart_rhythm) + "  " + mEtHeartRhythmCardiovascular.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.heart_rhythm).replace(":", "")
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_no_edema)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (mCbEdemaCardiovascular.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.edema) + "  " + mEtEdemaCardiovascular.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.edema).replace(":", "")
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_homan_s_negative)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbCapillaryRefillCardiovascular.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.capillary_refill) + "  " + mEtCapillaryRefillCardiovascular.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.capillary_refill).replace(":", "")
                )

            designBulletPoints(yLeftPosition, paint, getString(R.string.u2022_no_jvd))
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (mCbFatigueCardiovascular.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.fatigue)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.fatigue)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_apical_pulse_regular)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbDizzinessCardiovascular.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.dizziness)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.dizziness)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_radial_pulse_regular_s1_amp_s2_audible)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            setyAxisPosition()


            //***********     DESIGN GASTROINTESTINAL BULLET POINTS     *************


            designMainHeadingPoints(
                yPosition,
                labelPaint,
                getString(R.string.gastrointestinal)
            )
            yPosition += (paint.descent() - paint.ascent()) + 10
            yLeftPosition += (paint.descent() - paint.ascent()) + 10
            yRightPosition += (paint.descent() - paint.ascent()) + 10



            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_abdomen_soft_non_tender_non_distended)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbWnlGastrointestinal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.wnl)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.wnl)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_bowel_sounds_active_x4)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbExceptionGastrointestinal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.exceptions_g_tube_ng_tube_incontinent_nausea_vomiting_constipation_diarrhea) + "  " + mEtExceptionGastrointestinal.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.exceptions_g_tube_ng_tube_incontinent_nausea_vomiting_constipation_diarrhea).replace(
                        ":",
                        ""
                    )
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_tolerated_diet)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (mCbGTubeGastrointestinal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.g_tube)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.g_tube)
                )

            if (mCbIncontinentGastrointestinal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.incontinent)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.incontinent)
                )

            if (mCbNauseaGastrointestinal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.nausea)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.nausea)
                )

            if (mCbVomitingGastrointestinal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.vomiting)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.vomiting)
                )

            if (mCbConstipationGastrointestinal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.constipation)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.constipation)
                )

            if (mCbDiarrheaGastrointestinal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.diarrhea)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.diarrhea)
                )

            if (mCbBowelSoundsGastrointestinal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.bowel_sounds) + "  " + mEtBowelSoundsGastrointestinal.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.bowel_sounds).replace(":", "")
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_normal_chewing_and_swallowing)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_continent)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            setyAxisPosition()


            //**************      DESIGN GENITO URINARY BULLET POINTS       ************


            designMainHeadingPoints(
                yPosition,
                labelPaint,
                getString(R.string.genito_urinary)
            )

            yPosition += (paint.descent() - paint.ascent()) + 10
            yLeftPosition += (paint.descent() - paint.ascent()) + 10
            yRightPosition += (paint.descent() - paint.ascent()) + 10

            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_urine_clear)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (cb_exceptions_ch.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.exceptions)+ "  " + et_exception_gen_uni.text.toString()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.exceptions)
                )


            if (mCbWnlGenitoUrinary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.wnl)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.wnl)
                )


            designBulletPoints(yLeftPosition, paint, getString(R.string.u2022_no_odor))
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbExceptionGenitoUrinary.isChecked)
            {
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.frequency) + "  " + mEtExceptionGenitoUrinary.text.toString()
                        .trim()
                )

                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.urine_odor) + " :  " + mEtUrineOrderGenitoUrinary.text.toString()
                        .trim()
                )
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.urine_color) + " :  " + mEtUrineColorGenitoUrinary.text.toString()
                        .trim()
                )

            }

            else{

                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.frequency) + "  " + mEtExceptionGenitoUrinary.text.toString()
                        .trim()
                )
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.urine_odor) + " :  " + mEtUrineOrderGenitoUrinary.text.toString()
                        .trim()
                )
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.urine_color) + " :  " + mEtUrineColorGenitoUrinary.text.toString()
                        .trim()
                )
//                designCheckBoxFields(
//                    yRightPosition,
//                    paint,
//                    unCheckedBitmap,
//                    getString(R.string.exceptions_extra)
//                )
            }




            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_color_yellow_to_amber)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10




            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_continent)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10





            setyAxisPosition()


            //**********        DESIGN INTEGUMENTARY BULLET POINTS         *************


            designMainHeadingPoints(
                yPosition,
                labelPaint,
                getString(R.string.integumentary)
            )

            yPosition += (paint.descent() - paint.ascent()) + 10
            yLeftPosition += (paint.descent() - paint.ascent()) + 10
            yRightPosition += (paint.descent() - paint.ascent()) + 10


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_warm_dry_pink)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbWnlIntegumentary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.wnl)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.wnl)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_elastic_turgor)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (mCbBruisingIntegumentary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.bruising)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.bruising)
                )


            designBulletPoints(yLeftPosition, paint, getString(R.string.u2022_intact))
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (mCbFragileSkinIntegumentary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.fragile_skin)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.fragile_skin)
                )

            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_mucous_membranes_moist_pink)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            if (mCbRashIntegumentary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.rash)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.rash)
                )

            if (mCbPetechiaeIntegumentary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.petechiae)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.petechiae)
                )

            if (mCbAcneIntegumentary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.acne)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.acne)
                )

            if (mCbAbrasionIntegumentary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.abrasion)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.abrasion)
                )

            if (mCbExceptionIntegumentary.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.exceptions_bruising_fragile_rash_petechiae_acne_abrasion) + "  " + mEtExceptionIntegumentary.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.exceptions_bruising_fragile_rash_petechiae_acne_abrasion).replace(
                        ":",
                        " "
                    )
                )

            setyAxisPosition()


            //**********        DESIGN MUSCULOSKELETAL BULLET POINTS       ***************


            designMainHeadingPoints(
                yPosition,
                labelPaint,
                getString(R.string.musculoskeletal)
            )
            yPosition += (paint.descent() - paint.ascent()) + 10
            yLeftPosition += (paint.descent() - paint.ascent()) + 10
            yRightPosition += (paint.descent() - paint.ascent()) + 10

            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_absence_of_joint_swelling_or_tenderness)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbWnlMusculoSkeletal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.wnl)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.wnl)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_normal_rom_of_all_joints)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbExceptionMusculoSkeletal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.exceptions) + "  " + mEtExceptionMusculoSkeletal.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.exceptions_extra)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_independent_gait)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbBracesMusculoSkeletal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.braces) + "  " + mEtBracesMusculoSkeletal.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.braces).replace(":", "")
                )


            if (mCbMobilityMusculoSkeletal.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.mobility) + "  " + mEtMobilityMusculoSkeletal.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.mobility).replace(":", "")
                )


            setyAxisPosition()


            //*************         DESIGN PSYCHOSOCIAL BULLET POINTS       ****************


            designMainHeadingPoints(
                yPosition,
                labelPaint,
                getString(R.string.psychosocial)
            )
            yPosition += (paint.descent() - paint.ascent()) + 10
            yLeftPosition += (paint.descent() - paint.ascent()) + 10
            yRightPosition += (paint.descent() - paint.ascent()) + 10

            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_appropriate_appearance_amp_behavior)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbWnlPsychosocial.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.wnl)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.wnl)
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_verbalizations_appropriate_to_situation)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbExceptionPsychosocial.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.exceptions_nonverbal) + "  " + mEtExceptionPsychosocial.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.exceptions_nonverbal).replace(":", " ")
                )


            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_memory_intact)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10

            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_appropriate_affect)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            setyAxisPosition()


            //*********          DESIGN PAIN BULLET POINTS        **************


            designMainHeadingPoints(yPosition, labelPaint, getString(R.string.pain))

            yPosition += (paint.descent() - paint.ascent()) + 10
            yLeftPosition += (paint.descent() - paint.ascent()) + 10
            yRightPosition += (paint.descent() - paint.ascent()) + 10

            designBulletPoints(
                yLeftPosition,
                paint,
                getString(R.string.u2022_experiencing_no_pai)
            )
            yLeftPosition += (paint.descent() - paint.ascent()) + 10


            if (mCbWnlPain.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.wnl)
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.wnl)
                )


            if (mCbExceptionPain.isChecked)
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    checkedBitmap,
                    getString(R.string.exceptions) + "  " + mEtExceptionPain.text.toString()
                        .trim()
                )
            else
                designCheckBoxFields(
                    yRightPosition,
                    paint,
                    unCheckedBitmap,
                    getString(R.string.exceptions_extra).replace(":", "")
                )


            setyAxisPosition()


            page.canvas.drawLine(xPosition, yPosition + 10, 1480F, yPosition, linePaint)
            yPosition += (paint.descent() - paint.ascent()) + 10

            changePage()

            page.canvas.drawText("NURSING NOTE", xPosition, yPosition + 20, headerPaint)
            yPosition += (paint.descent() - paint.ascent()) + 15

            setNursingNote(yPosition, paint)

//            changePage()

            setSignature()

            pdfDocument.finishPage(page)
            var myFile: File
            var myFilePath:String
            val fileName = SimpleDateFormat("MMddyyyy hh:mm").format(Date()).toString().replace(" ","")+AppPreferences.getInstance(this).getUserName()  + ".pdf"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val file = File(getExternalFilesDir(null).toString() + "/H2H Reports")
                val myDirectoryPath = file.path


                if (!File(myDirectoryPath).exists()) {
                    File(myDirectoryPath).mkdir()
                }

                val myDirectoryFile = File(myDirectoryPath, "/H2H Reports/")

                if (!myDirectoryFile.exists()) {
                    myDirectoryFile.mkdir()
                }

                myFilePath = myDirectoryFile.absolutePath + "/"+fileName

                myFile = File(myDirectoryFile, fileName)
                if (!myFile.exists())
                    myFile.createNewFile()
                pdfDocument.writeTo(FileOutputStream(myFile))
                pdfDocument.close()

                val document = Document(FileInputStream(myFile))
                val documentPrivilege = DocumentPrivilege.getForbidAll()
                documentPrivilege.allowScreenReaders = true
                documentPrivilege.allowPrint=true
                documentPrivilege.allowModifyContents=true
                documentPrivilege.allowCopy=true
                documentPrivilege.allowDegradedPrinting=true
                documentPrivilege.allowModifyAnnotations=true
                document.encrypt("test", "nurse", documentPrivilege,  CryptoAlgorithm.AESx256,false)

                val pdfOutline = OutlineItemCollection(document.getOutlines())
                pdfOutline.title = ""
                pdfOutline.italic = false
                pdfOutline.bold = false
                pdfOutline.action = GoToAction(document.pages.get_Item(1))
                document.outlines.add(pdfOutline)
                //val fileName1 = AppPreferences.getInstance(this).getUserName() + SimpleDateFormat("MM-dd-yyyy hh:mm").format(Date()) + ".pdf"
                val fileName1 =SimpleDateFormat("MMddyyyy hh:mm").format(Date()).toString().replace(" ","")+ AppPreferences.getInstance(this).getUserName() + ".pdf"
                myFilePath = myDirectoryFile.absolutePath + "/"+fileName1
                document.save(myFilePath)
                myFile = File(myFilePath)
                if (!myFile.exists())
                    myFile.createNewFile()

                ToastUtil.showSuccessToast(this, "PDF saved in H2H Reports directory.")

                /* val shareIntent = Intent(Intent.ACTION_SEND)
                 shareIntent.type = "application/pdf"
                 shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$myFilePath"))
                 shareIntent.putExtra(Intent.EXTRA_SUBJECT, fileName.replace(".pdf", ""))
                 startActivity(Intent.createChooser(shareIntent, "SHARE DAILY REPORT"))*/
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_STREAM,  uriFromFile(this,
                    myFile))
                shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                shareIntent.type = "application/pdf"
                startActivity(Intent.createChooser(shareIntent, "share.."))

                mAppPreferences.setNursingAssessment("",prefrenceKey)
                finish()
            }
            else{
                val myDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path

                if (!File(myDirectoryPath).exists()) {
                    File(myDirectoryPath).mkdir()
                }

                val myDirectoryFile = File(myDirectoryPath, "/H2H Reports/")

                if (!myDirectoryFile.exists()) {
                    myDirectoryFile.mkdir()
                }

                myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path + "/H2H Reports/" + fileName

                myFile = File(myDirectoryFile, fileName)
                if (!myFile.exists())
                    myFile.createNewFile()
                pdfDocument.writeTo(FileOutputStream(myFile))
                pdfDocument.close()

                val document = Document(FileInputStream(myFile))
                val documentPrivilege = DocumentPrivilege.getForbidAll()
                documentPrivilege.allowScreenReaders = true
                documentPrivilege.allowPrint=true
                documentPrivilege.allowModifyContents=true
                documentPrivilege.allowCopy=true
                documentPrivilege.allowDegradedPrinting=true
                documentPrivilege.allowModifyAnnotations=true
                document.encrypt("test", "nurse", documentPrivilege,  CryptoAlgorithm.AESx256,false)

                //val fileName1 = AppPreferences.getInstance(this).getUserName() + SimpleDateFormat("MM-dd-yyyy hh:mm").format(Date()) + ".pdf"
                val pdfOutline = OutlineItemCollection(document.getOutlines())
                pdfOutline.title = ""
                pdfOutline.italic = false
                pdfOutline.bold = false
                pdfOutline.action = GoToAction(document.pages.get_Item(1))
                document.outlines.add(pdfOutline)
                val fileName1 =SimpleDateFormat("MMddyyyy hh:mm").format(Date()).toString().replace(" ","") + AppPreferences.getInstance(this).getUserName() + ".pdf"
                myFilePath = myDirectoryFile.absolutePath + "/"+fileName1
                document.save(myFilePath)
                myFile = File(myFilePath)
                if (!myFile.exists())
                    myFile.createNewFile()

                ToastUtil.showSuccessToast(this, "PDF saved in H2H Reports directory.")

                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "application/pdf"
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$myFilePath"))
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, fileName.replace(".pdf", ""))
                startActivity(Intent.createChooser(shareIntent, "SHARE DAILY REPORT"))

                mAppPreferences.setNursingAssessment("",prefrenceKey)
                finish()
            }
        } catch (exception: Exception) {
            ToastUtil.showErrorToast(this, exception.message.toString())
        } finally {
            dismissLoader()
        }
    }
    fun uriFromFile(context: Context, file:File):Uri {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
        }
        else
        {
            return Uri.fromFile(file)
        }
    }
    private fun createHeader() {
        page.canvas.drawText("PATIENT INFORMATION", xPosition, yPosition + 20, headerPaint)
        yPosition += (paint.descent() - paint.ascent()) + 10

        createField(
            paint,
            labelPaint,
            yPosition,
            "Name : ",
            mEtName.text.toString().trim()
        )
        yPosition += (paint.descent() - paint.ascent()) + 10

        createField(
            paint,
            labelPaint,
            yPosition,
            "DOB : ",
            mEtDob.text.toString().trim()
        )
        yPosition += (paint.descent() - paint.ascent()) + 10

        createField(
            paint,
            labelPaint,
            yPosition,
            "Date : ",
            mEtDate.text.toString().trim()
        )
        yPosition += (paint.descent() - paint.ascent()) + 10

        page.canvas.drawLine(xPosition, yPosition + 10, 1480F, yPosition, linePaint)
        yPosition += (paint.descent() - paint.ascent()) + 10

        yLeftPosition = yPosition
        yRightPosition = yPosition



    }

    private fun changePage() {

        if (yPosition > 2000 - 100 || yLeftPosition > 2000 - 100 || yRightPosition > 2000 - 100) {
            pdfDocument.finishPage(page)
            yPosition = 40.toFloat()
            yLeftPosition = yPosition
            yRightPosition = yPosition
            page = pdfDocument.startPage(myPageInfo)


            createHeader()


            var endColor = -35f
            var topStart = 0f

            var strokePaint1 = Paint()
            endColor = -40f;
            topStart =  20f
            strokePaint1.strokeWidth = 120F
            strokePaint1.color = Color.parseColor("#ff0066")
            page.canvas.drawRect(
                -150f,
                topStart ,
                page.canvas.width + 1500F,
                endColor,
                strokePaint1
            )
        }

    }

    private fun setSignature() {          //Set signature bitmap on pdf

//        changePage()

        page.canvas.drawBitmap(
            Bitmap.createScaledBitmap(signatureBitmap, 250, 150, false),
            (((page.canvas.width / 2) - (250 / 2))).toFloat(),
            yPosition + 30F,
            headerPaint
        )

        page.canvas.drawRect(
            (((page.canvas.width / 2) - (250 / 2))).toFloat() - 20F,
            yPosition + 20F,
            (((page.canvas.width / 2) + (250 / 2))).toFloat() + 20F,
            yPosition + 150 + 60F,
            strokePaint
        )

        page.canvas.drawText(
            "Signature",
            (((page.canvas.width / 2) - (labelPaint.measureText("Signature") / 2))),
            yPosition + 150 + 90F,
            labelPaint
        )

    }

    private fun setyAxisPosition() {        //Maintain the y axis for all sub heading of the form

        yPosition = if (yLeftPosition > yRightPosition) {
            yLeftPosition
        } else {
            yRightPosition
        }

        yPosition += (paint.descent() - paint.ascent()) + 10
        yLeftPosition = yPosition
        yRightPosition = yPosition
    }

    private fun setNursingNote(y: Float, paint: Paint) {           //Add nursing notes to pdf

//        changePage()

        val mTextPaint = TextPaint()
        mTextPaint.textSize = mTextPaint.textSize * 2

        var staticLayout = StaticLayout(
            AppPreferences.getInstance(this@NursingAssessmentFormActivity).getUserName()+"\n"+mEtNursingNote.text.toString().trim(),
            mTextPaint,
            page.canvas.width ,
            Layout.Alignment.ALIGN_NORMAL,
            1f,
            1f,
            false
        )

        page.canvas.save()
        page.canvas.translate(
            xPosition,
            y + 10
        )

        staticLayout.draw(page.canvas)
        page.canvas.restore()

        yPosition += ((mTextPaint.descent() - mTextPaint.ascent()) * staticLayout.lineCount)
        yPosition += 5f

    }


    private fun designCheckBoxFields(
        //Design parameters filled by user (Checkbox and text of form)
        y: Float,
        paint: Paint,
        bitmap: Bitmap,
        value: String,
    ) {

        val mTextPaint = TextPaint()
        mTextPaint.textSize = mTextPaint.textSize * 2

        page.canvas.drawBitmap(
            Bitmap.createScaledBitmap(bitmap, 30, 30, false),
            xPosition + paint.measureText(getString(R.string.u2022_radial_pedial_pulses_regular_strong_amp_palpable_bilaterally)) + 85,
            y - (mTextPaint.descent() - mTextPaint.ascent()) + 5F,
            paint
        )

        var staticLayout = StaticLayout(
            value,
            mTextPaint,
            (page.canvas.width - (xPosition + paint.measureText(getString(R.string.u2022_radial_pedial_pulses_regular_strong_amp_palpable_bilaterally))) - 180).toInt(),
            Layout.Alignment.ALIGN_NORMAL,
            1f,
            1f,
            false
        )

        page.canvas.save()
        page.canvas.translate(
            xPosition + paint.measureText(getString(R.string.u2022_radial_pedial_pulses_regular_strong_amp_palpable_bilaterally)) + 125,
            y - (mTextPaint.descent() - mTextPaint.ascent()) + 10
        )
        staticLayout.draw(page.canvas)
        page.canvas.restore()

        yRightPosition += ((mTextPaint.descent() - mTextPaint.ascent()) * staticLayout.lineCount)
        yRightPosition += 5f

//        changePage()

    }

    private fun designBulletPoints(
        //Design the sub heading points of form
        y: Float,
        paint: Paint,
        value: String,
    ) {
//        changePage()

        page.canvas.drawText(value, xPosition + 25, y + 10, paint)

    }

    private fun designMainHeadingPoints(
        // Design main heading of the form
        y: Float,
        paint: Paint,
        value: String,
    ) {
//        changePage()

        page.canvas.drawCircle(xPosition + 5, y + 10, 5f, paint)
        page.canvas.drawText(value, xPosition + 15, y + 17, paint)

    }


    private fun createField(
        //Design the patient information fields
        paint: Paint,
        labelPaint: Paint,
        y: Float,
        key: String,
        value: String,
    ) {

        page.canvas.drawText(key, xPosition, y + 10, labelPaint)
        page.canvas.drawText(value, paint.measureText(key) + 20, y + 10, paint)

    }


    private fun showSignatureDialog() {
        signatureDialog = Dialog(this)
        signatureDialog.setContentView(R.layout.dialog_signature)
        signatureDialog.setCancelable(false)
        signatureDialog.show()


        val signatureView: SignatureView =
            signatureDialog.findViewById(R.id.signature_view)
        val btnDone: Button = signatureDialog.findViewById(R.id.btn_done)


        btnDone.setOnClickListener {
            signatureBitmap = signatureView.getImage()!!
            mIvSignature.setImageBitmap(signatureView.getImage())
            signatureDialog.dismiss()
        }

        val window = signatureDialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initViews() {

        mEtName = findViewById(R.id.et_name)
        mEtDob = findViewById(R.id.et_dob)
        mEtDate = findViewById(R.id.et_date)
        mLlNeurological = findViewById(R.id.ll_neurological)
        mLlChildNeurological = findViewById(R.id.ll_child_neurological)
        mCbWnlNeurological = findViewById(R.id.cb_wnl_neurological)
        mCbExceptionNeurological = findViewById(R.id.cb_exception_neurological)
        mEtExceptionNeurological = findViewById(R.id.et_exception_neurological)
        mLlPulmonary = findViewById(R.id.ll_pulmonary)
        mLlChildPulmonary = findViewById(R.id.ll_child_pulmonary)
        mCbWnlPulmonary = findViewById(R.id.cb_wnl_pulmonary)
        mCbExceptionPulmonary = findViewById(R.id.cb_exception_pulmonary)
        mEtExceptionPulmonary = findViewById(R.id.et_exception_pulmonary)
        mCbTracheostomyPulmonary = findViewById(R.id.cb_tracheostomy_pulmonary)
        mCbO2Pulmonary = findViewById(R.id.cb_o2_pulmonary)
        mEtO2Pulmonary = findViewById(R.id.et_o2_pulmonary)
        mLlCardiovascular = findViewById(R.id.ll_cardiovascular)
        mLlChildCardiovascular = findViewById(R.id.ll_child_cardiovascular)
        mCbWnlCardiovascular = findViewById(R.id.cb_wnl_cardiovascular)
        mCbExceptionCardiovascular = findViewById(R.id.cb_exception_cardiovascular)
        mEtExceptionCardiovascular = findViewById(R.id.et_exception_cardiovascular)
        mCbHeartRhythmCardiovascular = findViewById(R.id.cb_heart_rhythm_cardiovascular)
        mEtHeartRhythmCardiovascular = findViewById(R.id.et_heart_rhythm_cardiovascular)
        mCbEdemaCardiovascular = findViewById(R.id.cb_edema_cardiovascular)
        mEtEdemaCardiovascular = findViewById(R.id.et_edema_cardiovascular)
        mCbCapillaryRefillCardiovascular = findViewById(R.id.cb_capillary_refill_cardiovascular)
        mEtCapillaryRefillCardiovascular = findViewById(R.id.et_capillary_refill_cardiovascular)
        mCbFatigueCardiovascular = findViewById(R.id.cb_fatigue_cardiovascular)
        mCbDizzinessCardiovascular = findViewById(R.id.cb_dizziness_cardiovascular)
        mLlGastrointestinal = findViewById(R.id.ll_gastrointestinal)
        mLlChildGastrointestinal = findViewById(R.id.ll_child_gastrointestinal)
        mCbWnlGastrointestinal = findViewById(R.id.cb_wnl_gastrointestinal)
        mCbExceptionGastrointestinal = findViewById(R.id.cb_exception_gastrointestinal)
        mEtExceptionGastrointestinal = findViewById(R.id.et_exception_gastrointestinal)
        mCbBowelSoundsGastrointestinal = findViewById(R.id.cb_bowel_sounds_gastrointestinal)
        mEtBowelSoundsGastrointestinal = findViewById(R.id.et_bowel_sounds_gastrointestinal)
        mLlGenitoUrinary = findViewById(R.id.ll_genito_urinary)
        mLlChildGenitoUrinary = findViewById(R.id.ll_child_genito_urinary)
        mCbWnlGenitoUrinary = findViewById(R.id.cb_wnl_genito_urinary)
        cb_exceptions_ch = findViewById(R.id.cb_exceptions_ch)
        cb_incontinent_ch = findViewById(R.id.cb_incontinent_ch)
        cb_urgency_ch = findViewById(R.id.cb_urgency_ch)
        mCbExceptionGenitoUrinary = findViewById(R.id.cb_exception_genito_urinary)
        mEtExceptionGenitoUrinary = findViewById(R.id.et_exception_genito_urinary)
        et_exception_gen_uni = findViewById(R.id.et_exception_gen_uni)
        mEtUrineOrderGenitoUrinary = findViewById(R.id.et_urine_order_genito_urinary)
        mEtUrineColorGenitoUrinary = findViewById(R.id.et_urine_color_genito_urinary)
        mLlIntegumentary = findViewById(R.id.ll_integumentary)
        mLlChildIntegumentary = findViewById(R.id.ll_child_integumentary)
        mCbWnlIntegumentary = findViewById(R.id.cb_wnl_integumentary)
        mCbExceptionIntegumentary = findViewById(R.id.cb_exception_integumentary)
        mEtExceptionIntegumentary = findViewById(R.id.et_exception_integumentary)
        mLlMusculoSkeletal = findViewById(R.id.ll_musculoskeletal)
        mLlChildMusculoSkeletal = findViewById(R.id.ll_child_musculoskeletal)
        mCbWnlMusculoSkeletal = findViewById(R.id.cb_wnl_musculoskeletal)
        mCbExceptionMusculoSkeletal = findViewById(R.id.cb_exception_musculoskeletal)
        mEtExceptionMusculoSkeletal = findViewById(R.id.et_exception_musculoskeletal)
        mCbBracesMusculoSkeletal = findViewById(R.id.cb_braces_musculoskeletal)
        mEtBracesMusculoSkeletal = findViewById(R.id.et_braces_musculoskeletal)
        mCbMobilityMusculoSkeletal = findViewById(R.id.cb_mobility_musculoskeletal)
        mEtMobilityMusculoSkeletal = findViewById(R.id.et_mobility_musculoskeletal)
        mLlPsychosocial = findViewById(R.id.ll_psychosocial)
        mLlChildPsychosocial = findViewById(R.id.ll_child_psychosocial)
        mCbWnlPsychosocial = findViewById(R.id.cb_wnl_psychosocial)
        mCbExceptionPsychosocial = findViewById(R.id.cb_exception_psychosocial)
        mEtExceptionPsychosocial = findViewById(R.id.et_exception_psychosocial)
        mLlPain = findViewById(R.id.ll_pain)
        mLlChildPain = findViewById(R.id.ll_child_pain)
        mCbWnlPain = findViewById(R.id.cb_wnl_pain)
        mCbExceptionPain = findViewById(R.id.cb_exception)
        mEtExceptionPain = findViewById(R.id.et_exception_pain)
        mBtnSubmit = findViewById(R.id.btn_submit)
        mBtnSubmitLater= findViewById(R.id.btn_submit_later)
        mViewSignature = findViewById(R.id.view_signature)
        mIvSignature = findViewById(R.id.iv_signature)
        mEtNursingNote = findViewById(R.id.et_nursing_notes)
        mCbGTubeGastrointestinal = findViewById(R.id.cb_g_tube_gastrointestinal)
        mCbIncontinentGastrointestinal = findViewById(R.id.cb_incontinent_gastrointestinal)
        mCbNauseaGastrointestinal = findViewById(R.id.cb_nausea_gastrointestinal)
        mCbVomitingGastrointestinal = findViewById(R.id.cb_vomiting_gastrointestinal)
        mCbConstipationGastrointestinal = findViewById(R.id.cb_constipation_gastrointestinal)
        mCbDiarrheaGastrointestinal = findViewById(R.id.cb_diarrhea_gastrointestinal)
        mCbBruisingIntegumentary = findViewById(R.id.cb_bruising_integumentary)
        mCbFragileSkinIntegumentary = findViewById(R.id.cb_fragile_skin_integumentary)
        mCbRashIntegumentary = findViewById(R.id.cb_rash_integumentary)
        mCbPetechiaeIntegumentary = findViewById(R.id.cb_petechiae_integumentary)
        mCbAcneIntegumentary = findViewById(R.id.cb_acne_integumentary)
        mCbAbrasionIntegumentary = findViewById(R.id.cb_abrasion_integumentary)

        mEtDate.isFocusable = false


        DateInputMask(mEtDob).listen()

        mEtNursingNote.addTextChangedListener { editable ->

            editable?.let {
                // When last char is space, return complete string
                if (it.isNotEmpty() && it.last() == ' ') {
                    val fullText = it.toString().trim()

                    try {
                        rvList.visibility = View.VISIBLE
                        getCommentsList(fullText)
//                    runOnUiThread(Runnable {
//                        if(::mAdapter.isInitialized){
//                            mAdapter.filter.filter(mEtNursingNote.text.toString())
//                        }
//                    })

                    } catch (e: IndexOutOfBoundsException) {
                    }
                }
            }

        }


        mEtNursingNote.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

//                try {
//                    rvList.visibility = View.VISIBLE
//                    getCommentsList()
////                    runOnUiThread(Runnable {
////                        if(::mAdapter.isInitialized){
////                            mAdapter.filter.filter(mEtNursingNote.text.toString())
////                        }
////                    })
//
//                } catch (e: IndexOutOfBoundsException) {
//                }
            }

        })

        mBtnSubmitLater.setOnClickListener {
            nurssingAssessmentModel.name = mEtName.text.toString().trim()
            nurssingAssessmentModel.dob = mEtDob.text.toString().trim()
            nurssingAssessmentModel.date = mEtDate.text.toString().trim()
            nurssingAssessmentModel.daily_notes = mEtNursingNote.text.toString().trim()
            nurssingAssessmentModel.mEtExceptionNeurological = mEtExceptionNeurological.text.toString().trim()
            nurssingAssessmentModel.mEtExceptionPulmonary= mEtExceptionPulmonary.text.toString().trim()
            nurssingAssessmentModel.mEtO2Pulmonary= mEtO2Pulmonary.text.toString().trim()
            nurssingAssessmentModel.mEtBowelSoundsGastrointestinal= mEtBowelSoundsGastrointestinal.text.toString().trim()

            nurssingAssessmentModel.mEtExceptionCardiovascular = mEtExceptionCardiovascular.text.toString().trim()
            nurssingAssessmentModel.mEtHeartRhythmCardiovascular  = mEtHeartRhythmCardiovascular.text.toString().trim()
            nurssingAssessmentModel.mEtEdemaCardiovascular = mEtEdemaCardiovascular.text.toString().trim()
            nurssingAssessmentModel.mEtCapillaryRefillCardiovascular = mEtCapillaryRefillCardiovascular.text.toString().trim()
            nurssingAssessmentModel.mEtExceptionGastrointestinal = mEtExceptionGastrointestinal.text.toString().trim()
            nurssingAssessmentModel.et_exception_gen_uni = et_exception_gen_uni.text.toString().trim()
            nurssingAssessmentModel.mEtExceptionGenitoUrinary = mEtExceptionGenitoUrinary.text.toString().trim()
            nurssingAssessmentModel.mEtUrineOrderGenitoUrinary = mEtUrineOrderGenitoUrinary.text.toString().trim()
            nurssingAssessmentModel.mEtUrineColorGenitoUrinary = mEtUrineColorGenitoUrinary.text.toString().trim()
            nurssingAssessmentModel.mEtExceptionIntegumentary= mEtExceptionIntegumentary.text.toString().trim()
            nurssingAssessmentModel.mEtExceptionMusculoSkeletal= mEtExceptionMusculoSkeletal.text.toString().trim()
            nurssingAssessmentModel.mEtBracesMusculoSkeletal = mEtBracesMusculoSkeletal.text.toString().trim()
            nurssingAssessmentModel.mEtMobilityMusculoSkeletal = mEtMobilityMusculoSkeletal.text.toString().trim()
            nurssingAssessmentModel.mEtExceptionPsychosocial= mEtExceptionPsychosocial.text.toString().trim()
            nurssingAssessmentModel.mEtExceptionPain= mEtExceptionPain.text.toString().trim()

            if (mCbWnlNeurological.isChecked)
                nurssingAssessmentModel.mCbWnlNeurological=true
            else nurssingAssessmentModel.mCbWnlNeurological=false

            if(mCbExceptionNeurological.isChecked)
                nurssingAssessmentModel.mCbExceptionNeurological=true
            else nurssingAssessmentModel.mCbExceptionNeurological=false

            if(mCbWnlPulmonary.isChecked)
                nurssingAssessmentModel.mCbWnlPulmonary=true
            else nurssingAssessmentModel.mCbWnlPulmonary=false

            if(mCbExceptionPulmonary.isChecked)
                nurssingAssessmentModel.mCbExceptionPulmonary=true
            else nurssingAssessmentModel.mCbExceptionPulmonary=false

            if(mCbTracheostomyPulmonary.isChecked)
                nurssingAssessmentModel.mCbTracheostomyPulmonary=true
            else nurssingAssessmentModel.mCbTracheostomyPulmonary=false

            if(mCbO2Pulmonary.isChecked)
                nurssingAssessmentModel.mCbO2Pulmonary=true
            else nurssingAssessmentModel.mCbO2Pulmonary=false





            if(mCbWnlCardiovascular.isChecked)
                nurssingAssessmentModel.mCbWnlCardiovascular=true
            else nurssingAssessmentModel.mCbWnlCardiovascular=false
            if(mCbExceptionCardiovascular.isChecked)
                nurssingAssessmentModel.mCbExceptionCardiovascular=true
            else nurssingAssessmentModel.mCbExceptionCardiovascular=false
            if(mCbHeartRhythmCardiovascular.isChecked)
                nurssingAssessmentModel.mCbHeartRhythmCardiovascular=true
            else nurssingAssessmentModel.mCbHeartRhythmCardiovascular=false
            if(mCbEdemaCardiovascular.isChecked)
                nurssingAssessmentModel.mCbEdemaCardiovascular=true
            else nurssingAssessmentModel.mCbEdemaCardiovascular=false
            if(mCbCapillaryRefillCardiovascular.isChecked)
                nurssingAssessmentModel.mCbCapillaryRefillCardiovascular=true
            else nurssingAssessmentModel.mCbCapillaryRefillCardiovascular=false
            if(mCbFatigueCardiovascular.isChecked)
                nurssingAssessmentModel.mCbFatigueCardiovascular=true
            else nurssingAssessmentModel.mCbFatigueCardiovascular=false
            if(mCbDizzinessCardiovascular.isChecked)
                nurssingAssessmentModel.mCbDizzinessCardiovascular=true
            else nurssingAssessmentModel.mCbDizzinessCardiovascular=false

            if(mCbWnlGastrointestinal.isChecked)
                nurssingAssessmentModel.mCbWnlGastrointestinal=true
            else nurssingAssessmentModel.mCbWnlGastrointestinal=false
            if(mCbExceptionGastrointestinal.isChecked)
                nurssingAssessmentModel.mCbExceptionGastrointestinal=true
            else nurssingAssessmentModel.mCbExceptionGastrointestinal=false
            if(mCbGTubeGastrointestinal.isChecked)
                nurssingAssessmentModel.mCbGTubeGastrointestinal=true
            else nurssingAssessmentModel.mCbGTubeGastrointestinal=false
            if(mCbIncontinentGastrointestinal.isChecked)
                nurssingAssessmentModel.mCbIncontinentGastrointestinal=true
            else nurssingAssessmentModel.mCbIncontinentGastrointestinal=false
            if(mCbNauseaGastrointestinal.isChecked)
                nurssingAssessmentModel.mCbNauseaGastrointestinal=true
            else nurssingAssessmentModel.mCbNauseaGastrointestinal=false
            if(mCbVomitingGastrointestinal.isChecked)
                nurssingAssessmentModel.mCbVomitingGastrointestinal=true
            else nurssingAssessmentModel.mCbVomitingGastrointestinal=false
            if(mCbConstipationGastrointestinal.isChecked)
                nurssingAssessmentModel.mCbConstipationGastrointestinal=true
            else nurssingAssessmentModel.mCbConstipationGastrointestinal=false
            if(mCbDiarrheaGastrointestinal.isChecked)
                nurssingAssessmentModel.mCbDiarrheaGastrointestinal=true
            else nurssingAssessmentModel.mCbDiarrheaGastrointestinal=false
            if(mCbBowelSoundsGastrointestinal.isChecked)
                nurssingAssessmentModel.mCbBowelSoundsGastrointestinal=true
            else nurssingAssessmentModel.mCbBowelSoundsGastrointestinal=false

            if(mCbWnlGenitoUrinary.isChecked)
                nurssingAssessmentModel.mCbWnlGenitoUrinary=true
            else nurssingAssessmentModel.mCbWnlGenitoUrinary=false
            if(cb_exceptions_ch.isChecked)
                nurssingAssessmentModel.cb_exceptions_ch=true
            else nurssingAssessmentModel.cb_exceptions_ch=false
            if(cb_incontinent_ch.isChecked)
                nurssingAssessmentModel.cb_incontinent_ch=true
            else nurssingAssessmentModel.cb_incontinent_ch=false
            if(cb_urgency_ch.isChecked)
                nurssingAssessmentModel.cb_urgency_ch=true
            else nurssingAssessmentModel.cb_urgency_ch=false
            if(mCbExceptionGenitoUrinary.isChecked)
                nurssingAssessmentModel.mCbExceptionGenitoUrinary=true
            else nurssingAssessmentModel.mCbExceptionGenitoUrinary=false

            if(mCbWnlIntegumentary.isChecked)
                nurssingAssessmentModel.mCbWnlIntegumentary=true
            else nurssingAssessmentModel.mCbWnlIntegumentary=false
            if(mCbBruisingIntegumentary.isChecked)
                nurssingAssessmentModel.mCbBruisingIntegumentary=true
            else nurssingAssessmentModel.mCbBruisingIntegumentary=false
            if(mCbFragileSkinIntegumentary.isChecked)
                nurssingAssessmentModel.mCbFragileSkinIntegumentary=true
            else nurssingAssessmentModel.mCbFragileSkinIntegumentary=false
            if(mCbRashIntegumentary.isChecked)
                nurssingAssessmentModel.mCbRashIntegumentary=true
            else nurssingAssessmentModel.mCbRashIntegumentary=false
            if(mCbPetechiaeIntegumentary.isChecked)
                nurssingAssessmentModel.mCbPetechiaeIntegumentary=true
            else nurssingAssessmentModel.mCbPetechiaeIntegumentary=false
            if(mCbAcneIntegumentary.isChecked)
                nurssingAssessmentModel.mCbAcneIntegumentary=true
            else nurssingAssessmentModel.mCbAcneIntegumentary=false
            if(mCbAbrasionIntegumentary.isChecked)
                nurssingAssessmentModel.mCbAbrasionIntegumentary=true
            else nurssingAssessmentModel.mCbAbrasionIntegumentary=false
            if(mCbExceptionIntegumentary.isChecked)
                nurssingAssessmentModel.mCbExceptionIntegumentary=true
            else nurssingAssessmentModel.mCbExceptionIntegumentary=false

            if(mCbWnlMusculoSkeletal.isChecked)
                nurssingAssessmentModel.mCbWnlMusculoSkeletal=true
            else nurssingAssessmentModel.mCbWnlMusculoSkeletal=false
            if(mCbExceptionMusculoSkeletal.isChecked)
                nurssingAssessmentModel.mCbExceptionMusculoSkeletal=true
            else nurssingAssessmentModel.mCbExceptionMusculoSkeletal=false
            if(mCbBracesMusculoSkeletal.isChecked)
                nurssingAssessmentModel.mCbBracesMusculoSkeletal=true
            else nurssingAssessmentModel.mCbBracesMusculoSkeletal=false
            if(mCbMobilityMusculoSkeletal.isChecked)
                nurssingAssessmentModel.mCbMobilityMusculoSkeletal=true
            else nurssingAssessmentModel.mCbMobilityMusculoSkeletal=false

            if(mCbWnlPsychosocial.isChecked)
                nurssingAssessmentModel.mCbWnlPsychosocial=true
            else nurssingAssessmentModel.mCbWnlPsychosocial=false
            if(mCbExceptionPsychosocial.isChecked)
                nurssingAssessmentModel.mCbExceptionPsychosocial=true
            else nurssingAssessmentModel.mCbExceptionPsychosocial=false

            if(mCbWnlPain.isChecked)
                nurssingAssessmentModel.mCbWnlPain=true
            else nurssingAssessmentModel.mCbWnlPain=false
            if(mCbExceptionPain.isChecked)
                nurssingAssessmentModel.mCbExceptionPain=true
            else nurssingAssessmentModel.mCbExceptionPain=false

            val json: String? = Gson().toJson(nurssingAssessmentModel)
            mAppPreferences.setNursingAssessment(json.toString(),prefrenceKey)
            finish()
        }

        mEtDob.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {
                if(s.toString().length==10){
                    var lastFour=s.toString().takeLast(4).toString().toInt()
                    if(lastFour<AppUtils.DOB_VALIDATION){
                        ToastUtil.showErrorToast(this@NursingAssessmentFormActivity, resources.getString(R.string.error_dob))
                    }
                    if (AppUtils.isValidDate("MM/dd/yyyy", s.toString()).equals("Future", true)){
                        mEtDob.setText("")
                        ToastUtil.showSuccessCenterToast(this@NursingAssessmentFormActivity, resources.getString(R.string.future_sate_not_allow))
                    }
                }

            }
        })
        mAppPreferences = AppPreferences.getInstance(this)
        mPermissionHelper = PermissionHelper(this, this)
        permissions = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        mPermissionHelper.requestPermission(
            permissions,
            "Storage permission required for uploading profile picture"
        )
    }


    private fun getCommentsList(str: String) {
        var tabId="1"
        var mData= ArrayList<CommentData>()
        val cursor = objDB.getComment(tabId,str)
        cursor.use {
            if (cursor.moveToFirst()) {
                do {
                    val message = cursor.getString(cursor.getColumnIndexOrThrow("message"))
                    val msgId = cursor.getString(cursor.getColumnIndexOrThrow("msgId"))

                    CommentData(message,msgId).let {
                        mData.add(it)
                    }

                } while (cursor.moveToNext())

                try {
                    setList(mData)
                } catch (e: Exception) {
                }
            }else   rvList.visibility = View.GONE
        }

    }

    private fun setList(data: ArrayList<CommentData>) {

        try {
            if(data.size>0){
                rvList.layoutManager = LinearLayoutManager(this@NursingAssessmentFormActivity)
                mAdapter = CommentsListAdapter(data) { modelData,type->
                    when(type){
                        "All"->{
                            msgId=modelData.msgId.toInt()
                            mEtNursingNote.setText(modelData.msg)
                            var length=modelData.msg.length
                            mEtNursingNote.setSelection(length)
                            rvList.visibility = View.GONE
                        }
                        "Delete"->{
                            objDB.deleteComment(modelData.msgId)
                            getCommentsList("")
                        }
                    }
                }
                rvList.adapter = mAdapter
                mAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }else  rvList.visibility= View.GONE

        } catch (e: Exception) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}
