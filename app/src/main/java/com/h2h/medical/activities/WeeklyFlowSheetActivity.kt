package com.h2h.medical.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.h2h.medical.R
import com.h2h.medical.helper.PermissionHelper
import com.h2h.medical.helper.SignatureView
import com.h2h.medical.models.DailySheetModel
import com.h2h.medical.models.WeeklySheetFormModel
import com.h2h.medical.models.WeeklySheetModel
import com.h2h.medical.utils.AppPreferences
import com.h2h.medical.utils.ToastUtil
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.KITKAT)
class WeeklyFlowSheetActivity : BaseActivity() {


    private lateinit var mEtH2hRepresentative: EditText
    private lateinit var mEtTitle: EditText
    private lateinit var mEtClient: EditText
    private lateinit var mEtPayPeriod: EditText
    private lateinit var mEtDateMonday: EditText
    private lateinit var mEtTimeInMonday: EditText
    private lateinit var mEtTimeOutMonday: EditText
    private lateinit var mEtLunchMonday: EditText
    private lateinit var mEtClassRoomMonday: EditText
    private lateinit var mEtDayTotalMonday: EditText
    private lateinit var mEtCommentMonday: EditText
    private lateinit var mEtDateTuesday: EditText
    private lateinit var mEtTimeInTuesday: EditText
    private lateinit var mEtTimeOutTuesday: EditText
    private lateinit var mEtLunchTuesday: EditText
    private lateinit var mEtClassRoomTuesday: EditText
    private lateinit var mEtDayTotalTuesday: EditText
    private lateinit var mEtCommentTuesday: EditText
    private lateinit var mEtDateWednesday: EditText
    private lateinit var mEtTimeInWednesday: EditText
    private lateinit var mEtTimeOutWednesday: EditText
    private lateinit var mEtLunchWednesday: EditText
    private lateinit var mEtClassRoomWednesday: EditText
    private lateinit var mEtDayTotalWednesday: EditText
    private lateinit var mEtCommentWednesday: EditText
    private lateinit var mEtDateThursday: EditText
    private lateinit var mEtTimeInThursday: EditText
    private lateinit var mEtTimeOutThursday: EditText
    private lateinit var mEtLunchThursday: EditText
    private lateinit var mEtClassRoomThursday: EditText
    private lateinit var mEtDayTotalThursday: EditText
    private lateinit var mEtCommentThursday: EditText
    private lateinit var mEtDateFriday: EditText
    private lateinit var mEtTimeInFriday: EditText
    private lateinit var mEtTimeOutFriday: EditText
    private lateinit var mEtLunchFriday: EditText
    private lateinit var mEtClassRoomFriday: EditText
    private lateinit var mEtDayTotalFriday: EditText
    private lateinit var mEtCommentFriday: EditText
    private lateinit var mEtDateSaturday: EditText
    private lateinit var mEtTimeInSaturday: EditText
    private lateinit var mEtTimeOutSaturday: EditText
    private lateinit var mEtLunchSaturday: EditText
    private lateinit var mEtClassRoomSaturday: EditText
    private lateinit var mEtDayTotalSaturday: EditText
    private lateinit var mEtCommentSaturday: EditText
    private lateinit var mEtDateSunday: EditText
    private lateinit var mEtTimeInSunday: EditText
    private lateinit var mEtTimeOutSunday: EditText
    private lateinit var mEtLunchSunday: EditText
    private lateinit var mEtClassRoomSunday: EditText
    private lateinit var mEtDayTotalSunday: EditText
    private lateinit var mEtCommentSunday: EditText
    private lateinit var mEtDateClient: EditText
    private lateinit var mEtDateRepresentative: EditText
    private lateinit var mEtTotalHours: EditText

    private lateinit var mBtnSubmit: Button
    private lateinit var mBtnSaveLater: Button
    private lateinit var mViewH2HRepresentativeSignature: LinearLayout
    private lateinit var mViewClientSignature: LinearLayout

    private lateinit var mLlMonday: LinearLayout
    private lateinit var mLlChildMonday: LinearLayout
    private lateinit var mLlTuesday: LinearLayout
    private lateinit var mLlChildTuesday: LinearLayout
    private lateinit var mLlWednesday: LinearLayout
    private lateinit var mLlChildWednesday: LinearLayout
    private lateinit var mLlThursday: LinearLayout
    private lateinit var mLlChildThursday: LinearLayout
    private lateinit var mLlFriday: LinearLayout
    private lateinit var mLlChildFriday: LinearLayout
    private lateinit var mLlSaturday: LinearLayout
    private lateinit var mLlChildSaturday: LinearLayout
    private lateinit var mLlSunday: LinearLayout
    private lateinit var mLlChildSunday: LinearLayout

    private lateinit var h2hRepresentativeSignatureDialog: Dialog
    private lateinit var clientSignatureDialog: Dialog
    private lateinit var mIvH2HRepresentative: ImageView
    private lateinit var mIvClientSignature: ImageView
    private var clientSignatureBitmap: Bitmap =
        Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888)
    private var h2hRepresentativeSignatureBitmap: Bitmap =
        Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888)

    private var myCalendar: Calendar = Calendar.getInstance()

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


    private lateinit var mAppPreferences: AppPreferences
    private var weeklySheetModel = WeeklySheetModel()
    private var weeklyFormDataList = ArrayList<WeeklySheetFormModel>()
    private var keysList = ArrayList<String>()

    private lateinit var mPermissionHelper: PermissionHelper
    private lateinit var permissions: Array<String>


    companion object {
        fun open(myActivity: Activity) {
            val intent = Intent(myActivity, WeeklyFlowSheetActivity::class.java)
            myActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_flow_sheet)

        initViews()
        initListeners()
        setData()

    }


    private fun setData() {
        if (mAppPreferences.getWeeklySheetData() != "") {
            try {
                weeklySheetModel = Gson().fromJson<DailySheetModel>(
                    mAppPreferences.getWeeklySheetData(),
                    object :
                        TypeToken<WeeklySheetModel?>() {}.type
                ) as WeeklySheetModel

                mEtClient.setText(weeklySheetModel.client)
                mEtH2hRepresentative.setText(weeklySheetModel.representative)
                mEtTitle.setText(weeklySheetModel.title)
                mEtPayPeriod.setText(weeklySheetModel.payperiod)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        if (mAppPreferences.getWeeklySheetFormData() != "") {
            weeklyFormDataList.clear()
            weeklyFormDataList = ArrayList()

            try {
                weeklyFormDataList = Gson().fromJson<List<WeeklySheetFormModel>>(
                    mAppPreferences.getWeeklySheetFormData(),
                    object :
                        TypeToken<List<WeeklySheetFormModel>>() {}.type
                ) as ArrayList<WeeklySheetFormModel>
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

            mEtDateMonday.setText(weeklyFormDataList[0].date)
            mEtTimeInMonday.setText(weeklyFormDataList[0].timein)
            mEtTimeOutMonday.setText(weeklyFormDataList[0].timeout)
            mEtLunchMonday.setText(weeklyFormDataList[0].lunch)
            mEtClassRoomMonday.setText(weeklyFormDataList[0].classroom)
            mEtDayTotalMonday.setText(weeklyFormDataList[0].daytotal)
            mEtCommentMonday.setText(weeklyFormDataList[0].comments)

            mEtDateTuesday.setText(weeklyFormDataList[1].date)
            mEtTimeInTuesday.setText(weeklyFormDataList[1].timein)
            mEtTimeOutTuesday.setText(weeklyFormDataList[1].timeout)
            mEtLunchTuesday.setText(weeklyFormDataList[1].lunch)
            mEtClassRoomTuesday.setText(weeklyFormDataList[1].classroom)
            mEtDayTotalTuesday.setText(weeklyFormDataList[1].daytotal)
            mEtCommentTuesday.setText(weeklyFormDataList[1].comments)

            mEtDateWednesday.setText(weeklyFormDataList[2].date)
            mEtTimeInWednesday.setText(weeklyFormDataList[2].timein)
            mEtTimeOutWednesday.setText(weeklyFormDataList[2].timeout)
            mEtLunchWednesday.setText(weeklyFormDataList[2].lunch)
            mEtClassRoomWednesday.setText(weeklyFormDataList[2].classroom)
            mEtDayTotalWednesday.setText(weeklyFormDataList[2].daytotal)
            mEtCommentWednesday.setText(weeklyFormDataList[2].comments)

            mEtDateThursday.setText(weeklyFormDataList[3].date)
            mEtTimeInThursday.setText(weeklyFormDataList[3].timein)
            mEtTimeOutThursday.setText(weeklyFormDataList[3].timeout)
            mEtLunchThursday.setText(weeklyFormDataList[3].lunch)
            mEtClassRoomThursday.setText(weeklyFormDataList[3].classroom)
            mEtDayTotalThursday.setText(weeklyFormDataList[3].daytotal)
            mEtCommentThursday.setText(weeklyFormDataList[3].comments)

            mEtDateFriday.setText(weeklyFormDataList[4].date)
            mEtTimeInFriday.setText(weeklyFormDataList[4].timein)
            mEtTimeOutFriday.setText(weeklyFormDataList[4].timeout)
            mEtLunchFriday.setText(weeklyFormDataList[4].lunch)
            mEtClassRoomFriday.setText(weeklyFormDataList[4].classroom)
            mEtDayTotalFriday.setText(weeklyFormDataList[4].daytotal)
            mEtCommentFriday.setText(weeklyFormDataList[4].comments)

            mEtDateSaturday.setText(weeklyFormDataList[5].date)
            mEtTimeInSaturday.setText(weeklyFormDataList[5].timein)
            mEtTimeOutSaturday.setText(weeklyFormDataList[5].timeout)
            mEtLunchSaturday.setText(weeklyFormDataList[5].lunch)
            mEtClassRoomSaturday.setText(weeklyFormDataList[5].classroom)
            mEtDayTotalSaturday.setText(weeklyFormDataList[5].daytotal)
            mEtCommentSaturday.setText(weeklyFormDataList[5].comments)

            mEtDateSunday.setText(weeklyFormDataList[6].date)
            mEtTimeInSunday.setText(weeklyFormDataList[6].timein)
            mEtTimeOutSunday.setText(weeklyFormDataList[6].timeout)
            mEtLunchSunday.setText(weeklyFormDataList[6].lunch)
            mEtClassRoomSunday.setText(weeklyFormDataList[6].classroom)
            mEtDayTotalSunday.setText(weeklyFormDataList[6].daytotal)
            mEtCommentSunday.setText(weeklyFormDataList[6].comments)

        }
    }


    private fun initListeners() {

        mLlMonday.setOnClickListener {
            if (mLlChildMonday.visibility == View.VISIBLE) {
                mLlChildMonday.visibility = View.GONE
            } else {
                mLlChildMonday.visibility = View.VISIBLE
                mLlChildTuesday.visibility = View.GONE
                mLlChildWednesday.visibility = View.GONE
                mLlChildThursday.visibility = View.GONE
                mLlChildFriday.visibility = View.GONE
                mLlChildSaturday.visibility = View.GONE
                mLlChildSunday.visibility = View.GONE
            }
        }


        mLlTuesday.setOnClickListener {
            if (mLlChildTuesday.visibility == View.VISIBLE) {
                mLlChildTuesday.visibility = View.GONE
            } else {
                mLlChildMonday.visibility = View.GONE
                mLlChildTuesday.visibility = View.VISIBLE
                mLlChildWednesday.visibility = View.GONE
                mLlChildThursday.visibility = View.GONE
                mLlChildFriday.visibility = View.GONE
                mLlChildSaturday.visibility = View.GONE
                mLlChildSunday.visibility = View.GONE
            }
        }


        mLlWednesday.setOnClickListener {
            if (mLlChildWednesday.visibility == View.VISIBLE) {
                mLlChildWednesday.visibility = View.GONE
            } else {
                mLlChildMonday.visibility = View.GONE
                mLlChildTuesday.visibility = View.GONE
                mLlChildWednesday.visibility = View.VISIBLE
                mLlChildThursday.visibility = View.GONE
                mLlChildFriday.visibility = View.GONE
                mLlChildSaturday.visibility = View.GONE
                mLlChildSunday.visibility = View.GONE
            }
        }


        mLlThursday.setOnClickListener {
            if (mLlChildThursday.visibility == View.VISIBLE) {
                mLlChildThursday.visibility = View.GONE
            } else {
                mLlChildMonday.visibility = View.GONE
                mLlChildTuesday.visibility = View.GONE
                mLlChildWednesday.visibility = View.GONE
                mLlChildThursday.visibility = View.VISIBLE
                mLlChildFriday.visibility = View.GONE
                mLlChildSaturday.visibility = View.GONE
                mLlChildSunday.visibility = View.GONE
            }
        }


        mLlFriday.setOnClickListener {
            if (mLlChildFriday.visibility == View.VISIBLE) {
                mLlChildFriday.visibility = View.GONE
            } else {
                mLlChildMonday.visibility = View.GONE
                mLlChildTuesday.visibility = View.GONE
                mLlChildWednesday.visibility = View.GONE
                mLlChildThursday.visibility = View.GONE
                mLlChildFriday.visibility = View.VISIBLE
                mLlChildSaturday.visibility = View.GONE
                mLlChildSunday.visibility = View.GONE
            }
        }


        mLlSaturday.setOnClickListener {
            if (mLlChildSaturday.visibility == View.VISIBLE) {
                mLlChildSaturday.visibility = View.GONE
            } else {
                mLlChildMonday.visibility = View.GONE
                mLlChildTuesday.visibility = View.GONE
                mLlChildWednesday.visibility = View.GONE
                mLlChildThursday.visibility = View.GONE
                mLlChildFriday.visibility = View.GONE
                mLlChildSaturday.visibility = View.VISIBLE
                mLlChildSunday.visibility = View.GONE
            }
        }


        mLlSunday.setOnClickListener {
            if (mLlChildSunday.visibility == View.VISIBLE) {
                mLlChildSunday.visibility = View.GONE
            } else {
                mLlChildMonday.visibility = View.GONE
                mLlChildTuesday.visibility = View.GONE
                mLlChildWednesday.visibility = View.GONE
                mLlChildThursday.visibility = View.GONE
                mLlChildFriday.visibility = View.GONE
                mLlChildSaturday.visibility = View.GONE
                mLlChildSunday.visibility = View.VISIBLE
            }
        }

        mEtDateClient.setOnClickListener { selectDate(mEtDateClient) }
        mEtDateRepresentative.setOnClickListener { selectDate(mEtDateRepresentative) }
        mEtDateMonday.setOnClickListener { selectDate(mEtDateMonday) }
        mEtDateTuesday.setOnClickListener { selectDate(mEtDateTuesday) }
        mEtDateWednesday.setOnClickListener { selectDate(mEtDateWednesday) }
        mEtDateThursday.setOnClickListener { selectDate(mEtDateThursday) }
        mEtDateFriday.setOnClickListener { selectDate(mEtDateFriday) }
        mEtDateSaturday.setOnClickListener { selectDate(mEtDateSaturday) }
        mEtDateSunday.setOnClickListener { selectDate(mEtDateSunday) }

        mEtTimeInMonday.setOnClickListener {
            selectTime(
                mEtTimeInMonday,
                mEtTimeInMonday,
                mEtTimeOutMonday,
                mEtDayTotalMonday
            )
        }

        mEtTimeOutMonday.setOnClickListener {
            selectTime(
                mEtTimeOutMonday,
                mEtTimeInMonday,
                mEtTimeOutMonday,
                mEtDayTotalMonday
            )
        }

        mEtTimeInTuesday.setOnClickListener {
            selectTime(
                mEtTimeInTuesday,
                mEtTimeInTuesday,
                mEtTimeOutTuesday,
                mEtDayTotalTuesday
            )
        }

        mEtTimeOutTuesday.setOnClickListener {
            selectTime(
                mEtTimeOutTuesday,
                mEtTimeInTuesday,
                mEtTimeOutTuesday,
                mEtDayTotalTuesday
            )
        }

        mEtTimeInWednesday.setOnClickListener {
            selectTime(
                mEtTimeInWednesday,
                mEtTimeInWednesday,
                mEtTimeOutWednesday,
                mEtDayTotalWednesday
            )
        }

        mEtTimeOutWednesday.setOnClickListener {
            selectTime(
                mEtTimeOutWednesday,
                mEtTimeInWednesday,
                mEtTimeOutWednesday,
                mEtDayTotalWednesday
            )
        }

        mEtTimeInThursday.setOnClickListener {
            selectTime(
                mEtTimeInThursday,
                mEtTimeInThursday,
                mEtTimeOutThursday,
                mEtDayTotalThursday
            )
        }

        mEtTimeOutThursday.setOnClickListener {
            selectTime(
                mEtTimeOutThursday,
                mEtTimeInThursday,
                mEtTimeOutThursday,
                mEtDayTotalThursday
            )
        }

        mEtTimeInFriday.setOnClickListener {
            selectTime(
                mEtTimeInFriday,
                mEtTimeInFriday,
                mEtTimeOutFriday,
                mEtDayTotalFriday
            )
        }

        mEtTimeOutFriday.setOnClickListener {
            selectTime(
                mEtTimeOutFriday,
                mEtTimeInFriday,
                mEtTimeOutFriday,
                mEtDayTotalFriday
            )
        }

        mEtTimeInSaturday.setOnClickListener {
            selectTime(
                mEtTimeInSaturday,
                mEtTimeInSaturday,
                mEtTimeOutSaturday,
                mEtDayTotalSaturday
            )
        }

        mEtTimeOutSaturday.setOnClickListener {
            selectTime(
                mEtTimeOutSaturday,
                mEtTimeInSaturday,
                mEtTimeOutSaturday,
                mEtDayTotalSaturday
            )
        }

        mEtTimeInSunday.setOnClickListener {
            selectTime(
                mEtTimeInSunday,
                mEtTimeInSunday,
                mEtTimeOutSunday,
                mEtDayTotalSunday
            )
        }

        mEtTimeOutSunday.setOnClickListener {
            selectTime(
                mEtTimeOutSunday,
                mEtTimeInSunday,
                mEtTimeOutSunday,
                mEtDayTotalSunday
            )
        }

        mViewH2HRepresentativeSignature.setOnClickListener {
            showRepresentativeSignatureDialog()
        }


        mViewClientSignature.setOnClickListener {
            showClientSignatureDialog()
        }


        mBtnSaveLater.setOnClickListener {
            saveData()
            finish()
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
                    showLoader(this, "Generating pdf...")
                    Handler(Looper.getMainLooper()).postDelayed({
                        generatePDF()
                    }, 100)
                }
//            }
        }
    }


    private fun selectDate(mEtDate: EditText) {

        DatePickerDialog(
            this,
            R.style.datePickerTheme,
            { view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val dateFormat = "MM/dd/yyyy"
                val sdf = SimpleDateFormat(dateFormat, Locale.US)
                mEtDate.setText(sdf.format(myCalendar.time))
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).show()

    }

    private fun selectTime(
        mEtTime: EditText,
        inTime: EditText,
        outTime: EditText,
        totalTime: EditText
    ) {

        TimePickerDialog(
            this,
            R.style.datePickerTheme,
            { view, hourOfDay, minute ->
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalendar.set(Calendar.MINUTE, minute)

                val dateFormat = "HH:mm"
                val sdf = SimpleDateFormat(dateFormat, Locale.US)
                mEtTime.setText(sdf.format(myCalendar.time))
                calculateTime(inTime, outTime, totalTime)
            },
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            true
        ).show()

    }


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun calculateTime(
        inTime: EditText,
        outTime: EditText,
        totalTime: EditText
    ) {
        if (inTime.text.toString().trim() != "" && outTime.text.toString()
                .trim() != ""
        ) {
            val simpleDateFormat = SimpleDateFormat("HH:mm")

            val date1 = simpleDateFormat.parse(inTime.text.toString().trim())
            val date2 = simpleDateFormat.parse(outTime.text.toString().trim())

            val difference: Long = date2.time - date1.time
            val days = (difference / (1000 * 60 * 60 * 24)).toInt()
            val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
            val min =
                (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)

            if (hours < 0 || min < 0) {
                ToastUtil.showErrorToast(
                    this,
                    "Day total can not be calculated because of incorrect in and out time."
                )
            } else {
                val time = if (hours > 9) {
                    hours.toString()
                } else {
                    "0$hours"
                } + ":" + if (min > 9) {
                    min.toString()
                } else {
                    "0$min"
                }

                totalTime.setText(time)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun generatePDF() {  //Design and generate pdf

        try {
            xPosition = 20.toFloat()
            yPosition = 40.toFloat()

            pdfDocument = PdfDocument()
            myPageInfo = PdfDocument.PageInfo.Builder(2000, 2000, 1).create()
            page = pdfDocument.startPage(myPageInfo) as PdfDocument.Page

            paint.textSize = paint.textSize * 2
            paint.color = Color.BLACK

            labelPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            labelPaint.strokeWidth = 2F
            labelPaint.textSize = labelPaint.textSize * 2
            labelPaint.color = Color.BLACK


            headerPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            headerPaint.strokeWidth = 2F
            headerPaint.textSize = headerPaint.textSize * 3
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


            createHeader()


            page.canvas.drawText("REPORT", xPosition, yPosition + 30, headerPaint)
            yPosition += (headerPaint.descent() - headerPaint.ascent())
            yPosition += 50F

            saveData()

            createKeysList()

            designTableInPDF()

            page.canvas.drawLine(xPosition, yPosition + 10, 1980F, yPosition, linePaint)
            yPosition += (paint.descent() - paint.ascent()) + 10

            changePage()

            setSignature()


            pdfDocument.finishPage(page)

            val fileName =
                AppPreferences.getInstance(this)
                    .getUserName() + SimpleDateFormat("MM-dd-yyyy hh:mm").format(
                    Date()
                ) + ".pdf"

            val myDirectoryPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path

            if (!File(myDirectoryPath).exists()) {
                File(myDirectoryPath).mkdir()
            }

            val myDirectoryFile = File(myDirectoryPath, "/H2H Reports/")

            if (!myDirectoryFile.exists()) {
                myDirectoryFile.mkdir()
            }

            val myFilePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path + "/H2H Reports/" + fileName

            val myFile = File(myDirectoryFile, fileName)
            if (!myFile.exists())
                myFile.createNewFile()

            pdfDocument.writeTo(FileOutputStream(myFile))
            pdfDocument.close()

//            val document = Document(FileInputStream(myFile))
//            document.encrypt("test", "nurse", 0, CryptoAlgorithm.AESx256)
//            document.save(myFilePath)

            ToastUtil.showSuccessToast(this, "PDF saved in H2H Reports directory.")

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "application/pdf"
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$myFilePath"))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, fileName.replace(".pdf", ""))
            startActivity(Intent.createChooser(shareIntent, "SHARE WEEKLY REPORT"))

            mAppPreferences.setWeeklySheetData("")
            mAppPreferences.setWeeklySheetFormData("")
            finish()
        } catch (exception: Exception) {
            ToastUtil.showErrorToast(this, "Error generating pdf. Please try again.")
        } finally {
            dismissLoader()
        }
    }

    private fun createHeader() {
        page.canvas.drawText("PATIENT INFORMATION", xPosition, yPosition + 10, headerPaint)
        yPosition += (paint.descent() - paint.ascent()) + 10

        createField(
            paint,
            labelPaint,
            yPosition,
            "H2H Representative : ",
            mEtH2hRepresentative.text.toString().trim()
        )
        yPosition += (paint.descent() - paint.ascent()) + 10

        createField(
            paint,
            labelPaint,
            yPosition,
            "Title : ",
            mEtTitle.text.toString().trim()
        )

        yPosition += (paint.descent() - paint.ascent()) + 10

        createField(
            paint,
            labelPaint,
            yPosition,
            "Client : ",
            mEtClient.text.toString().trim()
        )
        yPosition += (paint.descent() - paint.ascent()) + 10

        createField(
            paint,
            labelPaint,
            yPosition,
            "Pay Period : ",
            mEtPayPeriod.text.toString().trim()
        )
        yPosition += (paint.descent() - paint.ascent()) + 10


        page.canvas.drawLine(xPosition, yPosition + 10, 1980F, yPosition, linePaint)
        yPosition += (paint.descent() - paint.ascent())
        yPosition += 30F
    }


    private fun setSignature() {          //Set signature bitmap on pdf

        page.canvas.drawBitmap(
            Bitmap.createScaledBitmap(h2hRepresentativeSignatureBitmap, 250, 150, false),
            ((((page.canvas.width / 2) / 2) - (250 / 2))).toFloat(),
            yPosition + 30F,
            headerPaint
        )

        page.canvas.drawRect(
            ((((page.canvas.width / 2) / 2) - (250 / 2))).toFloat() - 20F,
            yPosition + 10F,
            ((((page.canvas.width / 2) / 2) + (250 / 2))).toFloat() + 20F,
            yPosition + 150 + 60F,
            strokePaint
        )

        page.canvas.drawText(
            "Date :" + mEtDateRepresentative.text.toString().trim(),
            ((((page.canvas.width / 2) / 2) - (labelPaint.measureText(
                "Date :" + mEtDateRepresentative.text.toString().trim()
            ) / 2))),
            yPosition + 150 + 90F,
            labelPaint
        )

        page.canvas.drawText(
            "H2H Representative Signature",
            ((((page.canvas.width / 2) / 2) - (labelPaint.measureText("H2H Representative Signature") / 2))),
            yPosition + 150 + 90F + (labelPaint.descent() - labelPaint.ascent()),
            labelPaint
        )

        page.canvas.drawBitmap(
            Bitmap.createScaledBitmap(clientSignatureBitmap, 250, 150, false),
            (((((page.canvas.width / 2) / 2) - (250 / 2))) + (page.canvas.width / 2)).toFloat(),
            yPosition + 30F,
            headerPaint
        )

        page.canvas.drawRect(
            (((((page.canvas.width / 2) / 2) - (250 / 2))).toFloat() - 20F) + (page.canvas.width / 2),
            yPosition + 10F,
            (((((page.canvas.width / 2) / 2) + (250 / 2))).toFloat() + 20F) + (page.canvas.width / 2),
            yPosition + 150 + 60F,
            strokePaint
        )

        page.canvas.drawText(
            "Date :" + mEtDateClient.text.toString().trim(),
            (((((page.canvas.width / 2) / 2) - (labelPaint.measureText(
                "Date :" + mEtDateClient.text.toString().trim()
            ) / 2))) + (page.canvas.width / 2)),
            yPosition + 150 + 90F,
            labelPaint
        )

        page.canvas.drawText(
            "Client Signature",
            (((((page.canvas.width / 2) / 2) - (labelPaint.measureText("Client Signature") / 2))) + (page.canvas.width / 2)),
            yPosition + 150 + 90F + (labelPaint.descent() - labelPaint.ascent()),
            labelPaint
        )
    }


    private fun createKeysList() {

        keysList.clear()
        keysList.add("Day")
        keysList.add("Monday")
        keysList.add("Tuesday")
        keysList.add("Wednesday")
        keysList.add("Thursday")
        keysList.add("Friday")
        keysList.add("Saturday")
        keysList.add("Sunday")

    }


    private fun designTableInPDF() {          //Draw main data table of form in pdf

        page.canvas.drawLine(
            xPosition,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10,
            page.canvas.width - 20F,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10, headerPaint
        )

        val mTextPaint = TextPaint()
        mTextPaint.textSize = mTextPaint.textSize * 2

        page.canvas.drawText(keysList[0], xPosition, yPosition + 5, headerPaint)
        page.canvas.drawText("Date", xPosition + 420, yPosition + 5, subHeadingPaint)
        page.canvas.drawText("Time In", xPosition + 620, yPosition + 5, subHeadingPaint)
        page.canvas.drawText(
            "Time Out",
            xPosition + 820,
            yPosition + 5,
            subHeadingPaint
        )
        page.canvas.drawText(
            "Lunch",
            xPosition + 1020,
            yPosition + 5,
            subHeadingPaint
        )
        page.canvas.drawText(
            "Location",
            xPosition + 1220,
            yPosition + 5,
            subHeadingPaint
        )
        page.canvas.drawText(
            "Day Total",
            xPosition + 1420,
            yPosition + 5,
            subHeadingPaint
        )
        page.canvas.drawText(
            "Comments",
            xPosition + 1620,
            yPosition + 5,
            subHeadingPaint
        )
        yPosition += (mTextPaint.descent() - mTextPaint.ascent())

        page.canvas.drawLine(
            xPosition + 400,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10,
            xPosition + 400,
            yPosition + 10, paint
        )

        page.canvas.drawLine(
            xPosition + 600,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10,
            xPosition + 600,
            yPosition + 10, paint
        )

        page.canvas.drawLine(
            xPosition + 800,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10,
            xPosition + 800,
            yPosition + 10, paint
        )

        page.canvas.drawLine(
            xPosition + 1000,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10,
            xPosition + 1000,
            yPosition + 10, paint
        )

        page.canvas.drawLine(
            xPosition + 1200,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10,
            xPosition + 1200,
            yPosition + 10, paint
        )

        page.canvas.drawLine(
            xPosition + 1400,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10,
            xPosition + 1400,
            yPosition + 10, paint
        )

        page.canvas.drawLine(
            xPosition + 1600,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10,
            xPosition + 1600,
            yPosition + 10, paint
        )

        //DRAW HORIZONTAL LINE
        page.canvas.drawLine(
            xPosition,
            yPosition + 10,
            page.canvas.width - 20F,
            yPosition + 10, headerPaint
        )

        yPosition += (headerPaint.descent() - headerPaint.ascent()) + 20

        changePage()

        for (i in 0 until 7) {

            var xtemp = xPosition
            var ytemp = yPosition
            var lineCount = 1

            page.canvas.drawText(keysList[i], xtemp, ytemp + 5, headerPaint)

            var staticLayout = StaticLayout(
                weeklyFormDataList[i].date,
                mTextPaint,
                175,
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                1f,
                false
            )
            page.canvas.save()
            page.canvas.translate(
                xtemp + 420,
                ytemp - (headerPaint.descent() - headerPaint.ascent())
            )
            staticLayout.draw(page.canvas)
            page.canvas.restore()
            if (lineCount < staticLayout.lineCount)
                lineCount = staticLayout.lineCount


            var staticLayout1 = StaticLayout(
                weeklyFormDataList[i].timein,
                mTextPaint,
                175,
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                1f,
                false
            )
            page.canvas.save()
            page.canvas.translate(
                xtemp + 620,
                ytemp - (headerPaint.descent() - headerPaint.ascent())
            )
            staticLayout1.draw(page.canvas)
            page.canvas.restore()
            if (lineCount < staticLayout1.lineCount)
                lineCount = staticLayout1.lineCount


            var staticLayout2 = StaticLayout(
                weeklyFormDataList[i].timeout,
                mTextPaint,
                175,
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                1f,
                false
            )
            page.canvas.save()
            page.canvas.translate(
                xtemp + 820,
                ytemp - (headerPaint.descent() - headerPaint.ascent())
            )
            staticLayout2.draw(page.canvas)
            page.canvas.restore()
            if (lineCount < staticLayout2.lineCount)
                lineCount = staticLayout2.lineCount


            var staticLayout3 = StaticLayout(
                weeklyFormDataList[i].lunch,
                mTextPaint,
                175,
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                1f,
                false
            )
            page.canvas.save()
            page.canvas.translate(
                xtemp + 1020,
                ytemp - (headerPaint.descent() - headerPaint.ascent())
            )
            staticLayout3.draw(page.canvas)
            page.canvas.restore()
            if (lineCount < staticLayout3.lineCount)
                lineCount = staticLayout3.lineCount

            var staticLayout4 = StaticLayout(
                weeklyFormDataList[i].classroom,
                mTextPaint,
                175,
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                1f,
                false
            )
            page.canvas.save()
            page.canvas.translate(
                xtemp + 1220,
                ytemp - (headerPaint.descent() - headerPaint.ascent())
            )
            staticLayout4.draw(page.canvas)
            page.canvas.restore()
            if (lineCount < staticLayout4.lineCount)
                lineCount = staticLayout4.lineCount

            var staticLayout5 = StaticLayout(
                weeklyFormDataList[i].daytotal,
                mTextPaint,
                175,
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                1f,
                false
            )
            page.canvas.save()
            page.canvas.translate(
                xtemp + 1420,
                ytemp - (headerPaint.descent() - headerPaint.ascent())
            )
            staticLayout5.draw(page.canvas)
            page.canvas.restore()
            if (lineCount < staticLayout5.lineCount)
                lineCount = staticLayout5.lineCount


            var staticLayout6 = StaticLayout(
                weeklyFormDataList[i].comments,
                mTextPaint,
                375,
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                1f,
                false
            )
            page.canvas.save()
            page.canvas.translate(
                xtemp + 1620,
                ytemp - (headerPaint.descent() - headerPaint.ascent())
            )
            staticLayout6.draw(page.canvas)
            page.canvas.restore()
            if (lineCount < staticLayout6.lineCount)
                lineCount = staticLayout6.lineCount

            yPosition += ((mTextPaint.descent() - mTextPaint.ascent()) * lineCount)


            //DRAW VERTICAL LINES

            page.canvas.drawLine(
                xtemp + 400,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 10,
                xtemp + 400,
                yPosition + 10, paint
            )

            page.canvas.drawLine(
                xtemp + 600,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 10,
                xtemp + 600,
                yPosition + 10, paint
            )

            page.canvas.drawLine(
                xtemp + 800,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 10,
                xtemp + 800,
                yPosition + 10, paint
            )

            page.canvas.drawLine(
                xtemp + 1000,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 10,
                xtemp + 1000,
                yPosition + 10, paint
            )

            page.canvas.drawLine(
                xtemp + 1200,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 10,
                xtemp + 1200,
                yPosition + 10, paint
            )

            page.canvas.drawLine(
                xtemp + 1400,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 10,
                xtemp + 1400,
                yPosition + 10, paint
            )

            page.canvas.drawLine(
                xtemp + 1600,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 10,
                xtemp + 1600,
                yPosition + 10, paint
            )

            //DRAW HORIZONTAL LINE
            page.canvas.drawLine(
                xPosition,
                yPosition + 10,
                page.canvas.width - 20F,
                yPosition + 10, headerPaint
            )

            yPosition += (headerPaint.descent() - headerPaint.ascent()) + 20

            changePage()
        }

        // SET TOTAL HOURS COLUMN IN PDF


        var staticLayout = StaticLayout(
            "Total Hours",
            mTextPaint,
            375,
            Layout.Alignment.ALIGN_NORMAL,
            1f,
            1f,
            false
        )
        page.canvas.save()
        page.canvas.translate(
            xPosition + 1420,
            (yPosition - (mTextPaint.descent() - mTextPaint.ascent()))
        )
        staticLayout.draw(page.canvas)
        page.canvas.restore()


        var staticLayout1 = StaticLayout(
            mEtTotalHours.text.toString().trim(),
            mTextPaint,
            (xPosition + 1620).toInt(),
            Layout.Alignment.ALIGN_NORMAL,
            1f,
            1f,
            false
        )
        page.canvas.save()
        page.canvas.translate(
            xPosition + 1620,
            (yPosition - (mTextPaint.descent() - mTextPaint.ascent()))
        )
        staticLayout1.draw(page.canvas)
        page.canvas.restore()

        page.canvas.drawLine(
            xPosition + 1400,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10,
            xPosition + 1400,
            yPosition + 10, paint
        )

        page.canvas.drawLine(
            xPosition + 1600,
            yPosition - (headerPaint.descent() - headerPaint.ascent()) - 10,
            xPosition + 1600,
            yPosition + 10, paint
        )

        yPosition += ((mTextPaint.descent() - mTextPaint.ascent()) * staticLayout1.lineCount)
    }


    private fun changePage() {              //Check the y axis coordinate to change the page

        if (yPosition > 2000 - 100) {
            pdfDocument.finishPage(page)
            yPosition = 60F
            page = pdfDocument.startPage(myPageInfo)
            createHeader()
        }

    }

    private fun createField(       //Design the patient information fields
        paint: Paint,
        labelPaint: Paint,
        y: Float,
        key: String,
        value: String
    ) {

        page.canvas.drawText(key, xPosition, y + 10, labelPaint)
        page.canvas.drawText(value, paint.measureText(key) + 20, y + 10, paint)

    }


    private fun valid(): Boolean {

        return when {
            mEtH2hRepresentative.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(this, "H2H Representative name required.")
                false
            }
            mEtClient.text.toString() == "" -> {
                ToastUtil.showErrorToast(this, "Client name required.")
                false
            }
            mEtPayPeriod.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(this, "Pay period required")
                false
            }
            mEtTitle.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(
                    this,
                    "Title required"
                )
                false
            }
            clientSignatureBitmap.width == 2 -> {
                ToastUtil.showErrorToast(this, "Confirm client signature.")
                false
            }
            h2hRepresentativeSignatureBitmap.width == 2 -> {
                ToastUtil.showErrorToast(this, "Confirm representative signature.")
                false
            }
            mEtDateClient.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(this, "Mention client signature date.")
                false
            }
            mEtDateRepresentative.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(this, "Mention H2H Representative signature date.")
                false
            }
            else -> {
                true
            }
        }

    }


    private fun saveData() {
        weeklyFormDataList.clear()
        weeklyFormDataList = ArrayList()

        weeklySheetModel.title = mEtTitle.text.toString().trim()
        weeklySheetModel.client = mEtClient.text.toString().trim()
        weeklySheetModel.payperiod = mEtPayPeriod.text.toString().trim()
        weeklySheetModel.representative = mEtH2hRepresentative.text.toString().trim()

        mAppPreferences.setWeeklySheetData(Gson().toJson(weeklySheetModel).toString())

        val mondayWeeklySheetFormModel = WeeklySheetFormModel()
        mondayWeeklySheetFormModel.date = mEtDateMonday.text.toString().trim()
        mondayWeeklySheetFormModel.timein = mEtTimeInMonday.text.toString().trim()
        mondayWeeklySheetFormModel.timeout = mEtTimeOutMonday.text.toString().trim()
        mondayWeeklySheetFormModel.lunch = mEtLunchMonday.text.toString().trim()
        mondayWeeklySheetFormModel.classroom = mEtClassRoomMonday.text.toString().trim()
        mondayWeeklySheetFormModel.daytotal = mEtDayTotalMonday.text.toString().trim()
        mondayWeeklySheetFormModel.comments = mEtCommentMonday.text.toString().trim()

        weeklyFormDataList.add(mondayWeeklySheetFormModel)

        val tuesdayWeeklySheetFormModel = WeeklySheetFormModel()
        tuesdayWeeklySheetFormModel.date = mEtDateTuesday.text.toString().trim()
        tuesdayWeeklySheetFormModel.timein = mEtTimeInTuesday.text.toString().trim()
        tuesdayWeeklySheetFormModel.timeout = mEtTimeOutTuesday.text.toString().trim()
        tuesdayWeeklySheetFormModel.lunch = mEtLunchTuesday.text.toString().trim()
        tuesdayWeeklySheetFormModel.classroom = mEtClassRoomTuesday.text.toString().trim()
        tuesdayWeeklySheetFormModel.daytotal = mEtDayTotalTuesday.text.toString().trim()
        tuesdayWeeklySheetFormModel.comments = mEtCommentTuesday.text.toString().trim()

        weeklyFormDataList.add(tuesdayWeeklySheetFormModel)

        val wednesdayWeeklySheetFormModel = WeeklySheetFormModel()
        wednesdayWeeklySheetFormModel.date = mEtDateWednesday.text.toString().trim()
        wednesdayWeeklySheetFormModel.timein = mEtTimeInWednesday.text.toString().trim()
        wednesdayWeeklySheetFormModel.timeout = mEtTimeOutWednesday.text.toString().trim()
        wednesdayWeeklySheetFormModel.lunch = mEtLunchWednesday.text.toString().trim()
        wednesdayWeeklySheetFormModel.classroom = mEtClassRoomWednesday.text.toString().trim()
        wednesdayWeeklySheetFormModel.daytotal = mEtDayTotalWednesday.text.toString().trim()
        wednesdayWeeklySheetFormModel.comments = mEtCommentWednesday.text.toString().trim()

        weeklyFormDataList.add(wednesdayWeeklySheetFormModel)

        val thursdayWeeklySheetFormModel = WeeklySheetFormModel()
        thursdayWeeklySheetFormModel.date = mEtDateThursday.text.toString().trim()
        thursdayWeeklySheetFormModel.timein = mEtTimeInThursday.text.toString().trim()
        thursdayWeeklySheetFormModel.timeout = mEtTimeOutThursday.text.toString().trim()
        thursdayWeeklySheetFormModel.lunch = mEtLunchThursday.text.toString().trim()
        thursdayWeeklySheetFormModel.classroom = mEtClassRoomThursday.text.toString().trim()
        thursdayWeeklySheetFormModel.daytotal = mEtDayTotalThursday.text.toString().trim()
        thursdayWeeklySheetFormModel.comments = mEtCommentThursday.text.toString().trim()

        weeklyFormDataList.add(thursdayWeeklySheetFormModel)

        val fridayWeeklySheetFormModel = WeeklySheetFormModel()
        fridayWeeklySheetFormModel.date = mEtDateFriday.text.toString().trim()
        fridayWeeklySheetFormModel.timein = mEtTimeInFriday.text.toString().trim()
        fridayWeeklySheetFormModel.timeout = mEtTimeOutFriday.text.toString().trim()
        fridayWeeklySheetFormModel.lunch = mEtLunchFriday.text.toString().trim()
        fridayWeeklySheetFormModel.classroom = mEtClassRoomFriday.text.toString().trim()
        fridayWeeklySheetFormModel.daytotal = mEtDayTotalFriday.text.toString().trim()
        fridayWeeklySheetFormModel.comments = mEtCommentFriday.text.toString().trim()

        weeklyFormDataList.add(fridayWeeklySheetFormModel)

        val saturdayWeeklySheetFormModel = WeeklySheetFormModel()
        saturdayWeeklySheetFormModel.date = mEtDateSaturday.text.toString().trim()
        saturdayWeeklySheetFormModel.timein = mEtTimeInSaturday.text.toString().trim()
        saturdayWeeklySheetFormModel.timeout = mEtTimeOutSaturday.text.toString().trim()
        saturdayWeeklySheetFormModel.lunch = mEtLunchSaturday.text.toString().trim()
        saturdayWeeklySheetFormModel.classroom = mEtClassRoomSaturday.text.toString().trim()
        saturdayWeeklySheetFormModel.daytotal = mEtDayTotalSaturday.text.toString().trim()
        saturdayWeeklySheetFormModel.comments = mEtCommentSaturday.text.toString().trim()

        weeklyFormDataList.add(saturdayWeeklySheetFormModel)

        val sundayWeeklySheetFormModel = WeeklySheetFormModel()
        sundayWeeklySheetFormModel.date = mEtDateSunday.text.toString().trim()
        sundayWeeklySheetFormModel.timein = mEtTimeInSunday.text.toString().trim()
        sundayWeeklySheetFormModel.timeout = mEtTimeOutSunday.text.toString().trim()
        sundayWeeklySheetFormModel.lunch = mEtLunchSunday.text.toString().trim()
        sundayWeeklySheetFormModel.classroom = mEtClassRoomSunday.text.toString().trim()
        sundayWeeklySheetFormModel.daytotal = mEtDayTotalSunday.text.toString().trim()
        sundayWeeklySheetFormModel.comments = mEtCommentSunday.text.toString().trim()

        weeklyFormDataList.add(sundayWeeklySheetFormModel)

        mAppPreferences.setWeeklySheetFormData(Gson().toJson(weeklyFormDataList).toString())

    }


    private fun showClientSignatureDialog() {
        clientSignatureDialog = Dialog(this)
        clientSignatureDialog.setContentView(R.layout.dialog_signature)
        clientSignatureDialog.setCancelable(false)
        clientSignatureDialog.show()


        val signatureView: SignatureView =
            clientSignatureDialog.findViewById(R.id.signature_view)
        val btnDone: Button = clientSignatureDialog.findViewById(R.id.btn_done)


        btnDone.setOnClickListener {
            clientSignatureBitmap = signatureView.getImage()!!
            mIvClientSignature.setImageBitmap(signatureView.getImage())
            clientSignatureDialog.dismiss()
        }

        val window = clientSignatureDialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun showRepresentativeSignatureDialog() {

        h2hRepresentativeSignatureDialog = Dialog(this)
        h2hRepresentativeSignatureDialog.setContentView(R.layout.dialog_signature)
        h2hRepresentativeSignatureDialog.setCancelable(false)
        h2hRepresentativeSignatureDialog.show()


        val signatureView: SignatureView =
            h2hRepresentativeSignatureDialog.findViewById(R.id.signature_view)
        val btnDone: Button = h2hRepresentativeSignatureDialog.findViewById(R.id.btn_done)


        btnDone.setOnClickListener {
            h2hRepresentativeSignatureBitmap = signatureView.getImage()!!
            mIvH2HRepresentative.setImageBitmap(signatureView.getImage())
            h2hRepresentativeSignatureDialog.dismiss()
        }

        val window = h2hRepresentativeSignatureDialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

    }


    private fun initViews() {

        mViewH2HRepresentativeSignature = findViewById(R.id.view_h2h_representative_signature)
        mIvH2HRepresentative = findViewById(R.id.iv_h2h_representative_signature)
        mViewClientSignature = findViewById(R.id.view_client_signature)
        mIvClientSignature = findViewById(R.id.iv_client_signature)
        mAppPreferences = AppPreferences.getInstance(this)

        mEtTotalHours = findViewById(R.id.et_total_hours)
        mEtDateClient = findViewById(R.id.et_date_client)
        mEtDateRepresentative = findViewById(R.id.et_date_representative)
        mEtH2hRepresentative = findViewById(R.id.et_h2h_representative)
        mEtTitle = findViewById(R.id.et_title)
        mEtClient = findViewById(R.id.et_client)
        mEtPayPeriod = findViewById(R.id.et_pay_period)
        mLlMonday = findViewById(R.id.ll_monday)
        mLlChildMonday = findViewById(R.id.ll_child_monday)
        mEtDateMonday = findViewById(R.id.et_date_monday)
        mEtTimeInMonday = findViewById(R.id.et_time_in_monday)
        mEtTimeOutMonday = findViewById(R.id.et_time_out_monday)
        mEtLunchMonday = findViewById(R.id.et_lunch_monday)
        mEtClassRoomMonday = findViewById(R.id.et_class_room_monday)
        mEtDayTotalMonday = findViewById(R.id.et_day_total_monday)
        mEtCommentMonday = findViewById(R.id.et_comment_monday)
        mLlTuesday = findViewById(R.id.ll_tuesday)
        mLlChildTuesday = findViewById(R.id.ll_child_tuesday)
        mEtDateTuesday = findViewById(R.id.et_date_tuesday)
        mEtTimeInTuesday = findViewById(R.id.et_time_in_tuesday)
        mEtTimeOutTuesday = findViewById(R.id.et_time_out_tuesday)
        mEtLunchTuesday = findViewById(R.id.et_lunch_tuesday)
        mEtClassRoomTuesday = findViewById(R.id.et_class_room_tuesday)
        mEtDayTotalTuesday = findViewById(R.id.et_day_total_tuesday)
        mEtCommentTuesday = findViewById(R.id.et_comment_tuesday)
        mLlWednesday = findViewById(R.id.ll_wednesday)
        mLlChildWednesday = findViewById(R.id.ll_child_wednesday)
        mEtDateWednesday = findViewById(R.id.et_date_wednesday)
        mEtTimeInWednesday = findViewById(R.id.et_time_in_wednesday)
        mEtTimeOutWednesday = findViewById(R.id.et_time_out_wednesday)
        mEtLunchWednesday = findViewById(R.id.et_lunch_wednesday)
        mEtClassRoomWednesday = findViewById(R.id.et_class_room_wednesday)
        mEtDayTotalWednesday = findViewById(R.id.et_day_total_wednesday)
        mEtCommentWednesday = findViewById(R.id.et_comment_wednesday)
        mLlThursday = findViewById(R.id.ll_thursday)
        mLlChildThursday = findViewById(R.id.ll_child_thursday)
        mEtDateThursday = findViewById(R.id.et_date_thursday)
        mEtTimeInThursday = findViewById(R.id.et_time_in_thursday)
        mEtTimeOutThursday = findViewById(R.id.et_time_out_thursday)
        mEtLunchThursday = findViewById(R.id.et_lunch_thursday)
        mEtClassRoomThursday = findViewById(R.id.et_class_room_thursday)
        mEtDayTotalThursday = findViewById(R.id.et_day_total_thursday)
        mEtCommentThursday = findViewById(R.id.et_comment_thursday)
        mLlFriday = findViewById(R.id.ll_friday)
        mLlChildFriday = findViewById(R.id.ll_child_friday)
        mEtDateFriday = findViewById(R.id.et_date_friday)
        mEtTimeInFriday = findViewById(R.id.et_time_in_friday)
        mEtTimeOutFriday = findViewById(R.id.et_time_out_friday)
        mEtLunchFriday = findViewById(R.id.et_lunch_friday)
        mEtClassRoomFriday = findViewById(R.id.et_class_room_friday)
        mEtDayTotalFriday = findViewById(R.id.et_day_total_friday)
        mEtCommentFriday = findViewById(R.id.et_comment_friday)
        mLlSaturday = findViewById(R.id.ll_saturday)
        mLlChildSaturday = findViewById(R.id.ll_child_saturday)
        mEtDateSaturday = findViewById(R.id.et_date_saturday)
        mEtTimeInSaturday = findViewById(R.id.et_time_in_saturday)
        mEtTimeOutSaturday = findViewById(R.id.et_time_out_saturday)
        mEtLunchSaturday = findViewById(R.id.et_lunch_saturday)
        mEtClassRoomSaturday = findViewById(R.id.et_class_room_saturday)
        mEtDayTotalSaturday = findViewById(R.id.et_day_total_saturday)
        mEtCommentSaturday = findViewById(R.id.et_comment_saturday)
        mLlSunday = findViewById(R.id.ll_sunday)
        mLlChildSunday = findViewById(R.id.ll_child_sunday)
        mEtDateSunday = findViewById(R.id.et_date_sunday)
        mEtTimeInSunday = findViewById(R.id.et_time_in_sunday)
        mEtTimeOutSunday = findViewById(R.id.et_time_out_sunday)
        mEtLunchSunday = findViewById(R.id.et_lunch_sunday)
        mEtClassRoomSunday = findViewById(R.id.et_class_room_sunday)
        mEtDayTotalSunday = findViewById(R.id.et_day_total_sunday)
        mEtCommentSunday = findViewById(R.id.et_comment_sunday)
        mBtnSubmit = findViewById(R.id.btn_submit)
        mBtnSaveLater = findViewById(R.id.btn_submit_later)

        mEtDateRepresentative.isFocusable = false
        mEtDateClient.isFocusable = false
        mEtDateMonday.isFocusable = false
        mEtDateTuesday.isFocusable = false
        mEtDateWednesday.isFocusable = false
        mEtDateThursday.isFocusable = false
        mEtDateFriday.isFocusable = false
        mEtDateSaturday.isFocusable = false
        mEtDateSunday.isFocusable = false

        mEtTimeInMonday.isFocusable = false
        mEtTimeOutMonday.isFocusable = false
        mEtTimeInTuesday.isFocusable = false
        mEtTimeOutTuesday.isFocusable = false
        mEtTimeInWednesday.isFocusable = false
        mEtTimeOutWednesday.isFocusable = false
        mEtTimeInThursday.isFocusable = false
        mEtTimeOutThursday.isFocusable = false
        mEtTimeInFriday.isFocusable = false
        mEtTimeOutFriday.isFocusable = false
        mEtTimeInSaturday.isFocusable = false
        mEtTimeOutSaturday.isFocusable = false
        mEtTimeInSunday.isFocusable = false
        mEtTimeOutSunday.isFocusable = false

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
}
