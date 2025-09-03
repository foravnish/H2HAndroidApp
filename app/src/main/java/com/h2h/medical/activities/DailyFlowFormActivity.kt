package com.h2h.medical.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
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
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspose.pdf.CryptoAlgorithm
import com.aspose.pdf.Document
import com.aspose.pdf.License
import com.aspose.pdf.facades.DocumentPrivilege
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.h2h.medical.R
import com.h2h.medical.Volley.BuildConfig
import com.h2h.medical.helper.PermissionHelper
import com.h2h.medical.helper.SignatureView
import com.h2h.medical.models.CommentData
import com.h2h.medical.models.DailySheetFormModel
import com.h2h.medical.models.DailySheetModel
import com.h2h.medical.models.DailySheetPdfModel
import com.h2h.medical.room.SqliteDb
import com.h2h.medical.utils.AppPreferences
import com.h2h.medical.utils.AppUtils
import com.h2h.medical.utils.DateInputMask
import com.h2h.medical.utils.ToastUtil
import com.kamdhenuteam.Adapter.CommentsListAdapter
import kotlinx.android.synthetic.main.activity_daily_flow_form.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


@RequiresApi(Build.VERSION_CODES.KITKAT)
class DailyFlowFormActivity : BaseActivity() {
    lateinit var objDB: SqliteDb
    val random = Random()
    var msgId = random.nextInt(99999999 - 10000000) + 10000000
    lateinit var mAdapter: CommentsListAdapter

    private lateinit var mAppPreferences: AppPreferences
    private var timeZoneList = ArrayList<String>()
    private var set =HashSet<String>()
    private lateinit var mEtName: EditText
    private lateinit var mEtDob: EditText
    private lateinit var mEtDate: EditText
    private lateinit var mEtDailyNotes: EditText
    private lateinit var mtvDailyNotes: TextView
    private lateinit var mEtReceivedReportFrom: EditText

    private lateinit var mLlZone1: LinearLayout
    private lateinit var mLlZone2: LinearLayout
    private lateinit var mLlZone3: LinearLayout
    private lateinit var mLlZone4: LinearLayout
    private lateinit var mLlZone5: LinearLayout

    private lateinit var mTvTimeZone1: TextView
    private lateinit var mTvTimeZone2: TextView
    private lateinit var mTvTimeZone3: TextView
    private lateinit var mTvTimeZone4: TextView
    private lateinit var mTvTimeZone5: TextView

    private lateinit var mViewSignature: LinearLayout
    private lateinit var mIvSignature: ImageView
    private lateinit var signatureDialog: Dialog

    private lateinit var mBtnSubmitLater: Button
    private lateinit var mBtnSubmit: Button
    private  var prefrenceKey: String="daily_flow1"

    private var myCalendar: Calendar = Calendar.getInstance()
    private var dailyFlowModel = DailySheetModel()

    private var signatureBitmap: Bitmap =
        Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888)

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
    private var count = 1

    private var valueList = ArrayList<CategoryModel>()
    private var pdfTimeZoneList = ArrayList<String>()
    private var mainList = ArrayList<DailySheetPdfModel>()

    private lateinit var timeZone1Data: DailySheetFormModel
    private lateinit var timeZone2Data: DailySheetFormModel
    private lateinit var timeZone3Data: DailySheetFormModel
    private lateinit var timeZone4Data: DailySheetFormModel
    private lateinit var timeZone5Data: DailySheetFormModel

    private lateinit var mPermissionHelper: PermissionHelper
    private lateinit var permissions: Array<String>


    private val datePickerDialog =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = "MM/dd/yyyy"
            val sdf = SimpleDateFormat(dateFormat, Locale.US)
            mEtDate.setText(sdf.format(myCalendar.time))
        }

    private val dateDOBPickerDialog =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = "MM/dd/yyyy"
            val sdf = SimpleDateFormat(dateFormat, Locale.US)
            mEtDob.setText(sdf.format(myCalendar.time))
        }

    companion object {
        fun open(myActivity: Activity,  value:Int=0) {
            val intent = Intent(myActivity, DailyFlowFormActivity::class.java)
            intent.putExtra("value",value)
            myActivity.startActivity(intent)
        }
    }

    var value = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_flow_form)

        initViews()

        objDB = SqliteDb(this)

        setTimeZone()
         value=intent.getIntExtra("value",0)
        if(value!=0)
        {
            prefrenceKey="daily_flow2"
            pateint_Form_Title.setText(R.string.daily_flow_sheet2)
           // btn_submit_later1.visibility=View.GONE
        }
        et_time.isEnabled=false
        initListeners()

        setData()

    }


    private fun setData() {
        Log.e("Dataaaa",prefrenceKey+"NNUll");
        if (mAppPreferences.getDailyFlowForm(prefrenceKey) != "") {
            try {
                dailyFlowModel = Gson().fromJson<DailySheetModel>(
                    mAppPreferences.getDailyFlowForm(prefrenceKey), object :
                        TypeToken<DailySheetModel?>() {}.type
                ) as DailySheetModel

                mEtName.setText(dailyFlowModel.name)
                mEtDob.setText(dailyFlowModel.dob)
                mEtDate.setText(dailyFlowModel.date)
                mEtDailyNotes.setText(dailyFlowModel.daily_notes)
                mEtReceivedReportFrom.setText(dailyFlowModel.report_received_from)
                if (dailyFlowModel.stime != "")
                    et_stime.setText(dailyFlowModel.stime)
                if (dailyFlowModel.etime != "")
                    et_time.setText(dailyFlowModel.etime)
                if (dailyFlowModel.duration != "")
                    et_duration.setText(dailyFlowModel.duration)

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }



        if (mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data"+value+"0") == "") {
            val json: String? = Gson().toJson(DailySheetPdfModel())
            mAppPreferences.setFirstTimeZoneData(json.toString(),"daily_flow_first_time_zone_data"+value+"0")
        }

        if (mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data"+value+"1") == "") {
            val json: String? = Gson().toJson(DailySheetPdfModel())
            mAppPreferences.setFirstTimeZoneData(json.toString(),"daily_flow_first_time_zone_data"+value+"1")
        }

        if (mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data"+value+"2") == "") {
            val json: String? = Gson().toJson(DailySheetPdfModel())
            mAppPreferences.setFirstTimeZoneData(json.toString(),"daily_flow_first_time_zone_data"+value+"2")
        }

        if (mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data"+value+"3") == "") {
            val json: String? = Gson().toJson(DailySheetPdfModel())
            mAppPreferences.setFirstTimeZoneData(json.toString(),"daily_flow_first_time_zone_data"+value+"3")
        }

        if (mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data"+value+"4") == "") {
            val json: String? = Gson().toJson(DailySheetPdfModel())
            mAppPreferences.setFirstTimeZoneData(json.toString(),"daily_flow_first_time_zone_data"+value+"4")
        }
    }


    private fun setTimeZone() {

        if (mAppPreferences.getTimeZone() == "") {
            mAppPreferences.resetTimeZone()
            timeZoneList = mAppPreferences.getTimeZone().split("@") as ArrayList<String>
            mTvTimeZone1.text = timeZoneList[0]
            mTvTimeZone2.text = timeZoneList[1]
            mTvTimeZone3.text = timeZoneList[2]
            mTvTimeZone4.text = timeZoneList[3]
            mTvTimeZone5.text = timeZoneList[4]
        } else {
            timeZoneList = mAppPreferences.getTimeZone().split("@") as ArrayList<String>
            mTvTimeZone1.text = timeZoneList[0]
            mTvTimeZone2.text = timeZoneList[1]
            mTvTimeZone3.text = timeZoneList[2]
            mTvTimeZone4.text = timeZoneList[3]
            mTvTimeZone5.text = timeZoneList[4]
        }

    }


    private fun initListeners() {
      /*  btn_submit_later1.setOnClickListener {
            DailyFlowFormActivity.open(this,2)
        }*/

        et_stime.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length != 0)
                    et_time.isEnabled=true
                else
                    et_time.isEnabled=false
            }
        })

        mEtDailyNotes.addTextChangedListener { editable ->

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
//        mEtDailyNotes.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {}
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                Log.e("CountLines",countLines(mEtDailyNotes.text.toString()).toString()+"  "+mEtDailyNotes.text.toString())
//                if (s.length != 0)
//                    mtvDailyNotes.setText("2000/"+s.length.toString())
//                if(countLines(mEtDailyNotes.text.toString())>20)
//                    mEtDailyNotes.isEnabled=false
//
//                rvList.visibility = View.VISIBLE
//                getCommentsList()
//                if(::mAdapter.isInitialized)
//                  mAdapter.filter.filter(mEtDailyNotes.text.toString().trim())
//            }
//
//        })

            mLlZone1.setOnClickListener {
                DailyFlowSheetTimeZoneForm.open(this, 0,value)
            }


            mLlZone2.setOnClickListener {
                DailyFlowSheetTimeZoneForm.open(this, 1,value)
            }


            mLlZone3.setOnClickListener {
                DailyFlowSheetTimeZoneForm.open(this, 2,value)
            }


            mLlZone4.setOnClickListener {
                DailyFlowSheetTimeZoneForm.open(this, 3,value)
            }


            mLlZone5.setOnClickListener {
                DailyFlowSheetTimeZoneForm.open(this, 4,value)
            }







        mViewSignature.setOnClickListener {
            showSignatureDialog()
        }


        mEtDate.setOnClickListener {
            val c = Calendar.getInstance()
            var dp= DatePickerDialog(
                this,
                R.style.datePickerTheme,
                datePickerDialog,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )

            dp.datePicker.maxDate=c.timeInMillis
            dp.show()
        }

        DateInputMask(mEtDob).listen()

        mEtDob.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if(s.toString().length==10){
                    var lastFour=s.toString().takeLast(4).toString().toInt()
                    if(lastFour<AppUtils.DOB_VALIDATION){
                        ToastUtil.showErrorToast(this@DailyFlowFormActivity, resources.getString(R.string.error_dob))
                    }

                    if (AppUtils.isValidDate("MM/dd/yyyy", s.toString()).equals("Future", true)){
                        mEtDob.setText("")
                        ToastUtil.showSuccessCenterToast(this@DailyFlowFormActivity, resources.getString(R.string.future_sate_not_allow))
                    }
                }

            }
        })
        mBtnSubmitLater.setOnClickListener {
            dailyFlowModel.name = mEtName.text.toString().trim()
            dailyFlowModel.dob = mEtDob.text.toString().trim()
            dailyFlowModel.date = mEtDate.text.toString().trim()
            dailyFlowModel.daily_notes = mEtDailyNotes.text.toString().trim()
            dailyFlowModel.stime = et_stime.text.toString().trim()
            dailyFlowModel.etime = et_time.text.toString().trim()
            dailyFlowModel.duration = et_duration.text.toString().trim()
            dailyFlowModel.report_received_from = mEtReceivedReportFrom.text.toString().trim()
            val json: String? = Gson().toJson(dailyFlowModel)
            mAppPreferences.setDailyFlowForm(json.toString(),prefrenceKey)
            finish()
        }

        mBtnSubmit.setOnClickListener {

            dailyFlowModel.stime = et_stime.text.toString().trim()
            dailyFlowModel.etime = et_time.text.toString().trim()
            dailyFlowModel.duration = et_duration.text.toString().trim()
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
                    var tabId="2"
                    if(value==2) tabId="3"
                    objDB.insertCommentData(""+mEtDailyNotes.text.toString(),""+msgId,tabId)

                    showLoader(this, "Generating PDF...")
                    Handler(Looper.getMainLooper()).postDelayed({
                        generatePDF()
                    }, 100)

                }
//            }
        }
    }

    private fun getCommentsList(str: String) {
        var tabId="2"
        if(value==2) tabId="3"
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

                setList(mData)
            }else   rvList.visibility = View.GONE
        }

    }

    private fun setList(data: ArrayList<CommentData>) {

        try {
            if(data.size>0){
                rvList.visibility= View.VISIBLE
                rvList.layoutManager = LinearLayoutManager(this@DailyFlowFormActivity)
                mAdapter = CommentsListAdapter(data) { modelData,type->
                    when(type){
                        "All"->{
                            msgId=modelData.msgId.toInt()
                            mEtDailyNotes.setText(modelData.msg)
                            var length=modelData.msg.length
                            mEtDailyNotes.setSelection(length)
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
            }else{
                rvList.visibility= View.GONE
            }


        } catch (e: Exception) {
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun generatePDF() {
        var myFile: File
        var myFilePath:String
        try {
            count = 1
            xPosition = 20.toFloat()
            yPosition = 40.toFloat()

            pdfDocument = PdfDocument()

            if(!!et_daily_flow_notes.text.equals(null)&& et_daily_flow_notes.text.length<300)
             myPageInfo = PdfDocument.PageInfo.Builder(2000, 3300, 1).create()
            else if(countLines(et_daily_flow_notes.text.toString())>0)
                myPageInfo = PdfDocument.PageInfo.Builder(2000, 3100+250+countLines(et_daily_flow_notes.text.toString())*30, 1).create()
            else
                myPageInfo = PdfDocument.PageInfo.Builder(2000, 3300, 1).create()
            page = pdfDocument.startPage(myPageInfo) as PdfDocument.Page
            page.canvas.drawColor(Color.WHITE)
            paint.textSize = paint.textSize * 2
            paint.color = Color.BLACK
            labelPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            labelPaint.strokeWidth = 2F
            labelPaint.textSize = labelPaint.textSize * 2
            labelPaint.color = Color.BLACK


            headerPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            headerPaint.strokeWidth = 2F
            headerPaint.textSize = headerPaint.textSize * 1
            headerPaint.color = Color.BLACK


            subHeadingPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            subHeadingPaint.textSize = subHeadingPaint.textSize * 1
            subHeadingPaint.color = Color.BLACK


            strokePaint.style = Paint.Style.STROKE
            strokePaint.strokeWidth = 2F
            strokePaint.color = Color.BLACK


            linePaint.isAntiAlias = true
            linePaint.color = Color.BLACK
            linePaint.strokeWidth = 3F
            for (i in 0 until mainList.size) {
                if(mainList[i].timeZone2.length>150 || mainList[i].timeZone3.length>150|| mainList[i].timeZone4.length>150|| mainList[i].timeZone5.length>150){
                    yPosition = 150F

                }
            }

            createHeader()
            page.canvas.drawText("REPORT", xPosition, yPosition + 30, headerPaint)
            yPosition += (headerPaint.descent() - headerPaint.ascent())
            yPosition += 50F

               setAllFieldsForPDF()
               createData()
               designTableInPDF()

            page.canvas.drawLine(xPosition, yPosition + 10, 1200F, yPosition, linePaint)
            yPosition += (paint.descent() - paint.ascent()) + 10

            changePage()

            yPosition += (paint.descent() - paint.ascent()) - 15
            page.canvas.drawText("Bus Arrival Time:- "+et_stime.text.toString(), xPosition, yPosition + 20, headerPaint)
            yPosition += (paint.descent() - paint.ascent()) + 15
            page.canvas.drawText("Bus DropOff Time:- "+et_time.text.toString(), xPosition, yPosition + 20, headerPaint)
            yPosition += (paint.descent() - paint.ascent()) + 15
            page.canvas.drawText("Duration:- "+et_duration.text.toString(), xPosition, yPosition + 20, headerPaint)
            yPosition += (paint.descent() - paint.ascent()) + 15
            page.canvas.drawText("NURSING NOTE", xPosition, yPosition + 20, headerPaint)
            yPosition += (paint.descent() - paint.ascent()) + 15
            setDailyNote()
            changePage()
            setSignature()
            pdfDocument.finishPage(page)
            val fileName =SimpleDateFormat("MMddyyyy hh:mm").format(Date()).toString().replace(" ","") + AppPreferences.getInstance(this).getUserName() + ".pdf"

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

                //val fileName1 = AppPreferences.getInstance(this).getUserName() + SimpleDateFormat("MM-dd-yyyy hh:mm").format(Date()) + ".pdf"
                val fileName1 =SimpleDateFormat("MMddyyyy hh:mm").format(Date()).toString().replace(" ","") + AppPreferences.getInstance(this).getUserName() + ".pdf"
                myFilePath = myDirectoryFile.absolutePath + "/"+fileName1
                document.save(myFilePath)
                myFile = File(myFilePath)
                if (!myFile.exists())
                    myFile.createNewFile()

                ToastUtil.showSuccessToast(this, "PDF saved in H2H Reports directory.")

                set.add(File(myDirectoryFile, "$fileName").path)
                mAppPreferences.setUserPDF(set);
                Log.e("Data1111:-"+prefrenceKey, mAppPreferences.getUserIPDF().toString());
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_STREAM,  uriFromFile(this,File(myDirectoryFile, "$fileName")))
                shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                shareIntent.type = "application/pdf"
                startActivity(Intent.createChooser(shareIntent, "share.."))
                mAppPreferences.resetTimeZone()
                mAppPreferences.resetDailyFomData(value)
                Log.e("Data:-"+prefrenceKey,mAppPreferences.getDailyFlowForm(prefrenceKey));

                mAppPreferences.setDailyFlowForm("",prefrenceKey)
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
                val fileName1 =SimpleDateFormat("MMddyyyy hh:mm").format(Date()).toString().replace(" ","") + AppPreferences.getInstance(this).getUserName() + ".pdf"
                myFilePath = myDirectoryFile.absolutePath + "/"+fileName1
                document.save(myFilePath)

                myFile = File(myFilePath)
                if (!myFile.exists())
                    myFile.createNewFile()



                set.add(File(myDirectoryFile, "$fileName").path.toString())
                mAppPreferences.setUserPDF(set);
                Log.e("Data1111:-"+prefrenceKey, mAppPreferences.getUserIPDF().toString());
                ToastUtil.showSuccessToast(this, "PDF saved in H2H Reports directory.")

                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "application/pdf"
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$myFilePath"))
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, fileName.replace(".pdf", ""))
                startActivity(Intent.createChooser(shareIntent, "SHARE DAILY REPORT"))

                mAppPreferences.resetTimeZone()
                mAppPreferences.resetDailyFomData(value)
                Log.e("Data:-"+prefrenceKey,mAppPreferences.getDailyFlowForm(prefrenceKey));
                mAppPreferences.setDailyFlowForm("",prefrenceKey)
                finish()
            }






        } catch (exception: Exception) {
            ToastUtil.showErrorToast(this, exception.message.toString())
        } finally {
            dismissLoader()
        }

    }
    private fun countLines(str: String): Int {

        val m: Matcher = Pattern.compile("\r\n|\r|\n").matcher(str)
        var lines = 1
        while (m.find()) {
            lines++
        }
        return lines
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


        val icon = BitmapFactory.decodeResource(resources, R.drawable.logo)

        page.canvas.drawBitmap(
            Bitmap.createScaledBitmap(icon, 250, 150, false),
            (((page.canvas.width ) - (350))).toFloat(),
            yPosition + 10F,
            headerPaint
        )


        page.canvas.drawText("PATIENT INFORMATION", xPosition, yPosition + 10, headerPaint)
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

        createField(
            paint,
            labelPaint,
            yPosition,
            "Received Report From : ",
            mEtReceivedReportFrom.text.toString().trim()
        )
        yPosition += (paint.descent() - paint.ascent()) + 10

        page.canvas.drawLine(xPosition, yPosition + 10, 1980F, yPosition, linePaint)
        yPosition += (paint.descent() - paint.ascent())
        yPosition += 30F
    }

    private fun setSignature() {          //Set signature bitmap on pdf

        page.canvas.drawBitmap(
            Bitmap.createScaledBitmap(signatureBitmap, 250, 150, false),
            (((page.canvas.width / 2) - (250 / 2))).toFloat(),
            yPosition + 10F,
            headerPaint
        )

        page.canvas.drawRect(
            (((page.canvas.width / 2) - (250 / 2))).toFloat() - 20F,
            yPosition + 20F,
            (((page.canvas.width / 2) + (250 / 2))).toFloat() + 60F,
            yPosition + 150 + 5F,
            strokePaint
        )

        page.canvas.drawText(
            "Signature",
            (((page.canvas.width / 2) - (labelPaint.measureText("Signature") / 2))),
            yPosition + 150 + 45F,
            labelPaint
        )

    }

    private fun setDailyNote() {           //Draw daily note section in pdf

        val mTextPaint = TextPaint()
        mTextPaint.textSize = mTextPaint.textSize * 2

        var staticLayout = StaticLayout(
            AppPreferences.getInstance(this@DailyFlowFormActivity).getUserName()+"\n"+mEtDailyNotes.text.toString().trim(),
            mTextPaint,
            page.canvas.width + 10,
            Layout.Alignment.ALIGN_NORMAL,
            1f,
            1f,
            false
        )

        page.canvas.save()
        page.canvas.translate(
            xPosition,
            yPosition
        )

        staticLayout.draw(page.canvas)
        page.canvas.restore()

        yPosition += ((mTextPaint.descent() - mTextPaint.ascent()) * staticLayout.lineCount)
        yPosition += 5f
    }


    private fun designTableInPDF() {          //Draw main data table of form in pdf
        var headerPaint2 = Paint()
        headerPaint2.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        headerPaint2.strokeWidth = 2F
        headerPaint2.textSize = headerPaint2.textSize * 2
        headerPaint2.color = Color.BLACK

//        page.canvas.drawLine(
//            -50f,
//            yPosition+10 ,
//            page.canvas.width + 50F,
//            yPosition+13,
//            headerPaint2
//        )


        for (i in 0 until mainList.size) {

            var xtemp = xPosition
            var ytemp = yPosition
            var lineCount = 1
            Log.d("Ytemp", ytemp.toString())
            page.canvas.save()
             if(i!=0){
                 var strokePaint1 = Paint()
                 strokePaint1.style = Paint.Style.STROKE
                 strokePaint1.strokeWidth = 70F
                 if(mainList[i].backgroundColore==null){
                     strokePaint1.color = Color.WHITE
                 }else{
                     strokePaint1.color = mainList[i].backgroundColore!!
                 }
                 var topStart = ytemp+10
                 var endColor = -35f;
                 if(mainList[i].timeZone2.length>100 || mainList[i].timeZone3.length>100|| mainList[i].timeZone4.length>100|| mainList[i].timeZone5.length>100){
                     endColor = -10f;
                     topStart = ytemp+30
                     strokePaint1.strokeWidth = 100F
                 }else  if(mainList[i].timeZone2.length>200 || mainList[i].timeZone3.length>200|| mainList[i].timeZone4.length>200|| mainList[i].timeZone5.length>200){
                     endColor = -10f;
                     topStart = ytemp+30
                     strokePaint1.strokeWidth = 120F
                 }



                 if((i+1)==mainList.size){
                     endColor = -10f;
                     topStart = ytemp+30
                     strokePaint1.strokeWidth = 120F
                     strokePaint1.color = Color.WHITE
                 }
                 if((i+1)==mainList.size){
                     endColor = -40f;
                     topStart = -50f
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
                 page.canvas.drawRect(
                     -150f,
                     topStart ,
                     page.canvas.width + 1500F,
                     endColor,
                     strokePaint1
                 )


             }
//            page.canvas.restore()
            page.canvas.drawText(mainList[i].fieldName, xtemp, ytemp + 5, headerPaint)

            val mTextPaint = TextPaint()
            mTextPaint.color=Color.BLACK
            mTextPaint.textSize = mTextPaint.textSize * 1

            /*if (i == 0)
                page.canvas.drawText(mainList[i].timeZone1, xtemp + 425, ytemp + 5, subHeadingPaint)
            else {


                var staticLayout = StaticLayout(
                    if (mainList[i].timeZone1 == "") {
                        "N/A"
                    } else {
                        mainList[i].timeZone1
                    },
                    mTextPaint,
                    290,
                    Layout.Alignment.ALIGN_NORMAL,
                    1f,
                    1f,
                    false
                )

                page.canvas.save()
                page.canvas.translate(
                    xtemp + 425,
                    ytemp - (mTextPaint.descent() - mTextPaint.ascent())+17
                )
                staticLayout.draw(page.canvas)
                page.canvas.restore()
                if (lineCount < staticLayout.lineCount)
                    lineCount = staticLayout.lineCount
            }*/


            if (i == 0)
                page.canvas.drawText(mainList[i].timeZone2,  xtemp + 250, ytemp , subHeadingPaint)
//                page.canvas.drawText(mainList[i].timeZone2, xtemp + 745, ytemp + 5, subHeadingPaint)
            else {
                var staticLayout = StaticLayout(
                    if (mainList[i].timeZone2 == "") {
                        "N/A"
                    } else {
                        mainList[i].timeZone2
                    },
                    mTextPaint,
                    400,
                    Layout.Alignment.ALIGN_NORMAL,
                    1f,
                    1f,
                    false
                )
                page.canvas.save()
                page.canvas.translate(
//                    xtemp + 745,
                    xtemp + 185,
                    ytemp - (mTextPaint.descent() - mTextPaint.ascent())+5
                )
                staticLayout.draw(page.canvas)
                page.canvas.restore()
                if (lineCount < staticLayout.lineCount)
                    lineCount = staticLayout.lineCount
            }


            if (i == 0)
                page.canvas.drawText(
                    mainList[i].timeZone3,
//                    xtemp + 1065,
                    xtemp + 750,
                    ytemp ,
                    subHeadingPaint
                )
            else {
                var staticLayout = StaticLayout(
                    if (mainList[i].timeZone3 == "") {
                        "N/A"
                    } else {
                        mainList[i].timeZone3
                    },
                    mTextPaint,
                    400,
                    Layout.Alignment.ALIGN_NORMAL,
                    1f,
                    1f,
                    false
                )
                page.canvas.save()
                page.canvas.translate(
//                    xtemp + 1065,
                    xtemp + 630,
                    ytemp - (mTextPaint.descent() - mTextPaint.ascent())+5
                )
                staticLayout.draw(page.canvas)
                page.canvas.restore()
                if (lineCount < staticLayout.lineCount)
                    lineCount = staticLayout.lineCount
            }


            if (i == 0)
                page.canvas.drawText(
                    mainList[i].timeZone4,
//                    xtemp + 1385,
                    xtemp + 1150,
                    ytemp ,
                    subHeadingPaint
                )
            else {
                var staticLayout = StaticLayout(
                    if (mainList[i].timeZone4 == "") {
                        "N/A"
                    } else {
                        mainList[i].timeZone4
                    },
                    mTextPaint,
                    400,
                    Layout.Alignment.ALIGN_NORMAL,
                    1f,
                    1f,
                    false
                )
                page.canvas.save()
                page.canvas.translate(
//                    xtemp + 1385,
                    xtemp + 1080,
                    ytemp - (mTextPaint.descent() - mTextPaint.ascent())+5
                )
                staticLayout.draw(page.canvas)
                page.canvas.restore()
                if (lineCount < staticLayout.lineCount)
                    lineCount = staticLayout.lineCount
            }

            if (i == 0)
                page.canvas.drawText(
                    mainList[i].timeZone5,
//                    xtemp + 1705,
                    xtemp + 1700,
                    ytemp,
                    subHeadingPaint
                )
            else {
                var staticLayout = StaticLayout(
                    if (mainList[i].timeZone5 == "") {
                        "N/A"
                    } else {
                        mainList[i].timeZone5
                    },
                    mTextPaint,
                    400,
                    Layout.Alignment.ALIGN_NORMAL,
                    1f,
                    1f,
                    false
                )
                page.canvas.save()
                page.canvas.translate(
//                    xtemp + 1705,
                    xtemp + 1610,
                    ytemp - (mTextPaint.descent() - mTextPaint.ascent())+5
                )
                staticLayout.draw(page.canvas)
                page.canvas.restore()
                if (lineCount < staticLayout.lineCount)
                    lineCount = staticLayout.lineCount
            }

            yPosition += ((mTextPaint.descent() - mTextPaint.ascent()) * lineCount)

            //DRAW VERTICAL LINES

            page.canvas.drawLine(
                xtemp + 160,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 25,
                xtemp + 160,
                yPosition + 25, paint
            )

            page.canvas.drawLine(
                xtemp + 610,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 25,
                xtemp + 610,
                yPosition + 25, paint
            )

            page.canvas.drawLine(
                xtemp + 1050,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 25,
                xtemp + 1050,
                yPosition + 25, paint
            )

            page.canvas.drawLine(
                xtemp + 1600,
                ytemp - (headerPaint.descent() - headerPaint.ascent()) - 25,
                xtemp + 1600,
                yPosition + 25, paint
            )

            //DRAW HORIZONTAL LINE.

            page.canvas.drawLine(
                -50f,
                ytemp-25 ,
                page.canvas.width + 50F,
                ytemp-23,
                headerPaint2
            )
//            page.canvas.drawLine(


//             val backgroundPaint = Paint()  //Paint for line
//            backgroundPaint.isAntiAlias = true
//            backgroundPaint.color = Color.BLACK
//            backgroundPaint.strokeWidth = 50F
//            page.canvas.drawLine(
//                xPosition,
//                yPosition + 10,
//                page.canvas.width - 10F,
//                yPosition + 50, backgroundPaint
//            )

            yPosition += (headerPaint.descent() - headerPaint.ascent()) + 20


        }

    }


    private fun changePage() {              //Check the y axis coordinate to change the page

        /*if (yPosition > 3500 - 100) {
            pdfDocument.finishPage(page)
            yPosition = 60F
            page = pdfDocument.startPage(myPageInfo)
            createHeader()
        }*/

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


    private fun setAllFieldsForPDF() {         //Initialise all the time zone fields and the keys of the time zone to set the data in pdf

        valueList = createFirstColumnList()


            timeZone1Data = Gson().fromJson<DailySheetFormModel>(
                mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data${value}0"),
                object : TypeToken<DailySheetFormModel?>() {}.type) as DailySheetFormModel




        timeZone2Data = Gson().fromJson<DailySheetFormModel>(
            mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data${value}1"),
            object :
                TypeToken<DailySheetFormModel?>() {}.type
        ) as DailySheetFormModel

        timeZone3Data = Gson().fromJson<DailySheetFormModel>(
            mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data${value}2"),
            object :
                TypeToken<DailySheetFormModel?>() {}.type
        ) as DailySheetFormModel

        timeZone4Data = Gson().fromJson<DailySheetFormModel>(
            mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data${value}3"),
            object :
                TypeToken<DailySheetFormModel?>() {}.type
        ) as DailySheetFormModel

        timeZone5Data = Gson().fromJson<DailySheetFormModel>(
            mAppPreferences.getFirstTimeZoneData("daily_flow_first_time_zone_data${value}4"),
            object :
                TypeToken<DailySheetFormModel?>() {}.type
        ) as DailySheetFormModel

    }


    private fun createFirstColumnList(): ArrayList<CategoryModel> {           //Create array list of keys of daily flow sheet form

        val temp = ArrayList<CategoryModel>()
        temp.add(CategoryModel("Temperature"))
        temp.add(CategoryModel("Pulse"))
        temp.add(CategoryModel("SpO2"))

        temp.add(CategoryModel("Oxygen",null,Color.parseColor("#DEEAF6")))
        temp.add(CategoryModel("Respiration",null,Color.parseColor("#DEEAF6")))
        temp.add(CategoryModel("Lung Sounds",null,Color.parseColor("#DEEAF6")))
        temp.add(CategoryModel("Work of Breathing",null,Color.parseColor("#DEEAF6")))
        temp.add(CategoryModel("RAS Score",null,Color.parseColor("#DEEAF6")))

        temp.add(CategoryModel("Tracheostomy"))

        temp.add(CategoryModel("Intervention",null,Color.parseColor("#FBE4D5")))
        temp.add(CategoryModel("Quantity,Color,Consistency",null,Color.parseColor("#FBE4D5")))
        temp.add(CategoryModel("Intervention Outcome",null,Color.parseColor("#FBE4D5")))
        temp.add(CategoryModel("Blood Glucose",null,Color.parseColor("#FBE4D5")))

        temp.add(CategoryModel("G Tube Site",null,Color.parseColor("#DEEAF6")))
        temp.add(CategoryModel("Nutrition",null,Color.parseColor("#DEEAF6")))
        temp.add(CategoryModel("Input",null,Color.parseColor("#DEEAF6")))
        temp.add(CategoryModel("Output",null,Color.parseColor("#DEEAF6")))
        temp.add(CategoryModel("Vent/Decompress Abd",null,Color.parseColor("#DEEAF6")))

        temp.add(CategoryModel("Position"))
        temp.add(CategoryModel("Assist/Independent"))
        temp.add(CategoryModel("Activity"))
        temp.add(CategoryModel("Skin Integrity Check"))
        temp.add(CategoryModel("Braces"))
        temp.add(CategoryModel("Stretched"))

        temp.add(CategoryModel("Medication",null,Color.parseColor("#FBE4D5")))
        temp.add(CategoryModel("Monitor TPN",null,Color.parseColor("#FBE4D5")))
        temp.add(CategoryModel("Teach",null,Color.parseColor("#FBE4D5")))

       /* temp.add(CategoryModel("Start Time",null,Color.parseColor("#E2EFD9")))
        temp.add(CategoryModel("End Time",null,Color.parseColor("#E2EFD9")))
        temp.add(CategoryModel("Duration",null,Color.parseColor("#E2EFD9")))*/

        return temp
    }

    private fun createData() {           //Set list of data accordingly to set the data dynamically in the table created in the PDF


        pdfTimeZoneList = mAppPreferences.getTimeZone().split("@") as ArrayList<String>
        val dailyFlowPdfModel = DailySheetPdfModel()
        dailyFlowPdfModel.fieldName = "Time"
        dailyFlowPdfModel.timeZone1 = pdfTimeZoneList[0]
        dailyFlowPdfModel.timeZone2 = pdfTimeZoneList[1]
        dailyFlowPdfModel.timeZone3 = pdfTimeZoneList[2]
        dailyFlowPdfModel.timeZone4 = pdfTimeZoneList[3]
        dailyFlowPdfModel.timeZone5 = pdfTimeZoneList[4]
        mainList.add(dailyFlowPdfModel)

        for (i in 0 until valueList.size) {
            val dailyFlowPdfModel1 = DailySheetPdfModel()
            dailyFlowPdfModel1.fieldName = valueList[i].name
            dailyFlowPdfModel1.textColore = valueList[i].tcolore
            dailyFlowPdfModel1.backgroundColore = valueList[i].backcolore
            mainList.add(dailyFlowPdfModel1)
        }

        mainList[1].timeZone1 = timeZone1Data.temperature
        mainList[1].timeZone2 = timeZone2Data.temperature
        mainList[1].timeZone3 = timeZone3Data.temperature
        mainList[1].timeZone4 = timeZone4Data.temperature
        mainList[1].timeZone5 = timeZone5Data.temperature

        mainList[2].timeZone1 = timeZone1Data.pulse
        mainList[2].timeZone2 = timeZone2Data.pulse
        mainList[2].timeZone3 = timeZone3Data.pulse
        mainList[2].timeZone4 = timeZone4Data.pulse
        mainList[2].timeZone5 = timeZone5Data.pulse

        mainList[3].timeZone1 = timeZone1Data.spo2
        mainList[3].timeZone2 = timeZone2Data.spo2
        mainList[3].timeZone3 = timeZone3Data.spo2
        mainList[3].timeZone4 = timeZone4Data.spo2
        mainList[3].timeZone5 = timeZone5Data.spo2

        mainList[4].timeZone1 = timeZone1Data.oxygen
        mainList[4].timeZone2 = timeZone2Data.oxygen
        mainList[4].timeZone3 = timeZone3Data.oxygen
        mainList[4].timeZone4 = timeZone4Data.oxygen
        mainList[4].timeZone5 = timeZone5Data.oxygen

        mainList[5].timeZone1 = timeZone1Data.respiration
        mainList[5].timeZone2 = timeZone2Data.respiration
        mainList[5].timeZone3 = timeZone3Data.respiration
        mainList[5].timeZone4 = timeZone4Data.respiration
        mainList[5].timeZone5 = timeZone5Data.respiration

        mainList[6].timeZone1 = timeZone1Data.lungsound
        mainList[6].timeZone2 = timeZone2Data.lungsound
        mainList[6].timeZone3 = timeZone3Data.lungsound
        mainList[6].timeZone4 = timeZone4Data.lungsound
        mainList[6].timeZone5 = timeZone5Data.lungsound

        mainList[7].timeZone1 = timeZone1Data.workofbreathe
        mainList[7].timeZone2 = timeZone2Data.workofbreathe
        mainList[7].timeZone3 = timeZone3Data.workofbreathe
        mainList[7].timeZone4 = timeZone4Data.workofbreathe
        mainList[7].timeZone5 = timeZone5Data.workofbreathe

        mainList[8].timeZone1 = timeZone1Data.rasscore
        mainList[8].timeZone2 = timeZone2Data.rasscore
        mainList[8].timeZone3 = timeZone3Data.rasscore
        mainList[8].timeZone4 = timeZone4Data.rasscore
        mainList[8].timeZone5 = timeZone5Data.rasscore


        mainList[9].timeZone1 = timeZone1Data.tracheostomy
        mainList[9].timeZone2 = timeZone2Data.tracheostomy
        mainList[9].timeZone3 = timeZone3Data.tracheostomy
        mainList[9].timeZone4 = timeZone4Data.tracheostomy
        mainList[9].timeZone5 = timeZone5Data.tracheostomy

        mainList[10].timeZone1 = timeZone1Data.intervention
        mainList[10].timeZone2 = timeZone2Data.intervention
        mainList[10].timeZone3 = timeZone3Data.intervention
        mainList[10].timeZone4 = timeZone4Data.intervention
        mainList[10].timeZone5 = timeZone5Data.intervention


        mainList[11].timeZone1 = timeZone1Data.quantity+","+timeZone1Data.color+","+timeZone1Data.consistency
        mainList[11].timeZone2 = timeZone2Data.quantity+","+timeZone2Data.color+","+timeZone2Data.consistency
        mainList[11].timeZone3 = timeZone3Data.quantity+","+timeZone3Data.color+","+timeZone3Data.consistency
        mainList[11].timeZone4 = timeZone4Data.quantity+","+timeZone4Data.color+","+timeZone4Data.consistency
        mainList[11].timeZone5 = timeZone5Data.quantity+","+timeZone5Data.color+","+timeZone5Data.consistency


        mainList[12].timeZone1 = timeZone1Data.interventionoutcome
        mainList[12].timeZone2 = timeZone2Data.interventionoutcome
        mainList[12].timeZone3 = timeZone3Data.interventionoutcome
        mainList[12].timeZone4 = timeZone4Data.interventionoutcome
        mainList[12].timeZone5 = timeZone5Data.interventionoutcome

        mainList[13].timeZone1 = timeZone1Data.bloodGulcose
        mainList[13].timeZone2 = timeZone2Data.bloodGulcose
        mainList[13].timeZone3 = timeZone3Data.bloodGulcose
        mainList[13].timeZone4 = timeZone4Data.bloodGulcose
        mainList[13].timeZone5 = timeZone5Data.bloodGulcose

        mainList[14].timeZone1 = timeZone1Data.gtubesite
        mainList[14].timeZone2 = timeZone2Data.gtubesite
        mainList[14].timeZone3 = timeZone3Data.gtubesite
        mainList[14].timeZone4 = timeZone4Data.gtubesite
        mainList[14].timeZone5 = timeZone5Data.gtubesite

        mainList[15].timeZone1 = timeZone1Data.nutrition
        mainList[15].timeZone2 = timeZone2Data.nutrition
        mainList[15].timeZone3 = timeZone3Data.nutrition
        mainList[15].timeZone4 = timeZone4Data.nutrition
        mainList[15].timeZone5 = timeZone5Data.nutrition

        mainList[16].timeZone1 = timeZone1Data.input
        mainList[16].timeZone2 = timeZone2Data.input
        mainList[16].timeZone3 = timeZone3Data.input
        mainList[16].timeZone4 = timeZone4Data.input
        mainList[16].timeZone5 = timeZone5Data.input

        mainList[17].timeZone1 = timeZone1Data.outcome
        mainList[17].timeZone2 = timeZone2Data.outcome
        mainList[17].timeZone3 = timeZone3Data.outcome
        mainList[17].timeZone4 = timeZone4Data.outcome
        mainList[17].timeZone5 = timeZone5Data.outcome

        mainList[18].timeZone1 = timeZone1Data.ventdecompressabd
        mainList[18].timeZone2 = timeZone2Data.ventdecompressabd
        mainList[18].timeZone3 = timeZone3Data.ventdecompressabd
        mainList[18].timeZone4 = timeZone4Data.ventdecompressabd
        mainList[18].timeZone5 = timeZone5Data.ventdecompressabd

        mainList[19].timeZone1 = timeZone1Data.position
        mainList[19].timeZone2 = timeZone2Data.position
        mainList[19].timeZone3 = timeZone3Data.position
        mainList[19].timeZone4 = timeZone4Data.position
        mainList[19].timeZone5 = timeZone5Data.position

        mainList[20].timeZone1 = timeZone1Data.assist
        mainList[20].timeZone2 = timeZone2Data.assist
        mainList[20].timeZone3 = timeZone3Data.assist
        mainList[20].timeZone4 = timeZone4Data.assist
        mainList[20].timeZone5 = timeZone5Data.assist

        mainList[21].timeZone1 = timeZone1Data.activity
        mainList[21].timeZone2 = timeZone2Data.activity
        mainList[21].timeZone3 = timeZone3Data.activity
        mainList[21].timeZone4 = timeZone4Data.activity
        mainList[21].timeZone5 = timeZone5Data.activity

        mainList[22].timeZone1 = timeZone1Data.participating
        mainList[22].timeZone2 = timeZone2Data.participating
        mainList[22].timeZone3 = timeZone3Data.participating
        mainList[22].timeZone4 = timeZone4Data.participating
        mainList[22].timeZone5 = timeZone5Data.participating

        mainList[23].timeZone1 = timeZone1Data.braces
        mainList[23].timeZone2 = timeZone2Data.braces
        mainList[23].timeZone3 = timeZone3Data.braces
        mainList[23].timeZone4 = timeZone4Data.braces
        mainList[23].timeZone5 = timeZone5Data.braces

        mainList[24].timeZone1 = timeZone1Data.stretched
        mainList[24].timeZone2 = timeZone2Data.stretched
        mainList[24].timeZone3 = timeZone3Data.stretched
        mainList[24].timeZone4 = timeZone4Data.stretched
        mainList[24].timeZone5 = timeZone5Data.stretched

        mainList[25].timeZone1 = timeZone1Data.medication
        mainList[25].timeZone2 = timeZone2Data.medication
        mainList[25].timeZone3 = timeZone3Data.medication
        mainList[25].timeZone4 = timeZone4Data.medication
        mainList[25].timeZone5 = timeZone5Data.medication

        mainList[26].timeZone1 = timeZone1Data.monitortpn
        mainList[26].timeZone2 = timeZone2Data.monitortpn
        mainList[26].timeZone3 = timeZone3Data.monitortpn
        mainList[26].timeZone4 = timeZone4Data.monitortpn
        mainList[26].timeZone5 = timeZone5Data.monitortpn

        mainList[27].timeZone1 = timeZone1Data.teach
        mainList[27].timeZone2 = timeZone2Data.teach
        mainList[27].timeZone3 = timeZone3Data.teach
        mainList[27].timeZone4 = timeZone4Data.teach
        mainList[27].timeZone5 = timeZone5Data.teach

      /*

        mainList[28].timeZone1 = dailyFlowModel.etime
        mainList[28].timeZone2 = dailyFlowModel.etime
        mainList[28].timeZone3 = dailyFlowModel.etime
        mainList[28].timeZone4 = dailyFlowModel.etime
        mainList[28].timeZone5 = dailyFlowModel.etime

        mainList[29].timeZone1 = dailyFlowModel.duration
        mainList[29].timeZone2 = dailyFlowModel.duration
        mainList[29].timeZone3 = dailyFlowModel.duration
        mainList[29].timeZone4 = dailyFlowModel.duration
        mainList[29].timeZone5 = dailyFlowModel.duration*/

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
        return when {
            mEtName.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(this, "Patient name required.")
                false
            }
            mEtName.text.toString().length<3->{
                ToastUtil.showErrorToast(this, "Patient name required longer than 3 characters.")
                false
            }
            lastFour< AppUtils.DOB_VALIDATION->{
                ToastUtil.showErrorToast(this, resources.getString(R.string.error_dob))
                false
            }
            firstTwo>12->{
                ToastUtil.showErrorToast(this@DailyFlowFormActivity, resources.getString(R.string.error_year))
                false
            }
            mDate>31->{
                ToastUtil.showErrorToast(this@DailyFlowFormActivity, resources.getString(R.string.error_date))
                false
            }
            et_time.text.toString().trim() == "" &&  et_stime.text.toString().trim() != ""-> {
                ToastUtil.showErrorToast(this, "End time  required.")
                false
            }
            et_time.text.toString().trim() != "" &&  et_stime.text.toString().trim() == ""-> {
                ToastUtil.showErrorToast(this, "Start time required.")
                false
            }
            mEtDob.text.toString() == "" -> {
                ToastUtil.showErrorToast(this, "Patient date of birth required.")
                false
            }
            mEtDob.text.toString().length<10->{
                ToastUtil.showErrorToast(this, resources.getString(R.string.error_dd_mm_yyyy))
                false
            }
            mEtDate.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(this, "Select date.")
                false
            }
            mEtReceivedReportFrom.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(
                    this,
                    "Mention the name of person from whom you received report."
                )
                false
            }
            signatureBitmap.width == 2 -> {
                ToastUtil.showErrorToast(this, "Confirm your signature.")
                false
            }
            else -> {
                true
            }
        }
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
        mEtDailyNotes = findViewById(R.id.et_daily_flow_notes)
        mtvDailyNotes = findViewById(R.id.countdailynotes)
        mEtReceivedReportFrom = findViewById(R.id.et_received_report_from)

//        mEtDailyNotes.setText(""+AppPreferences.getInstance(this@DailyFlowFormActivity).getUserName()+"\n")

        mLlZone1 = findViewById(R.id.ll_zone_1)
        mLlZone1.visibility= View.GONE
        mLlZone2 = findViewById(R.id.ll_zone_2)
        mLlZone3 = findViewById(R.id.ll_zone_3)
        mLlZone4 = findViewById(R.id.ll_zone_4)
        mLlZone5 = findViewById(R.id.ll_zone_5)

        mViewSignature = findViewById(R.id.view_signature)
        mIvSignature = findViewById(R.id.iv_signature)

        mTvTimeZone1 = findViewById(R.id.tv_tz_1)
        mTvTimeZone2 = findViewById(R.id.tv_tz_2)
        mTvTimeZone3 = findViewById(R.id.tv_tz_3)
        mTvTimeZone4 = findViewById(R.id.tv_tz_4)
        mTvTimeZone5 = findViewById(R.id.tv_tz_5)
        mBtnSubmitLater = findViewById(R.id.btn_submit_later)
        mBtnSubmit = findViewById(R.id.btn_submit)

        mEtDate.isFocusable = false
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

        et_time.isFocusable=false;
        et_stime.isFocusable=false;
        et_duration.isFocusable=false;

        et_stime.setOnClickListener {
            selectTime(
                et_stime,
                et_stime,
                et_time,
                et_duration
            )
        }

        et_time.setOnClickListener {
            selectTime(
                et_time,
                et_stime,
                et_time,
                et_duration
            )
        }
    }


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun calculateTime(inTime: EditText, outTime: EditText, totalTime: EditText) {
        if (inTime.text.toString().trim() != "" && outTime.text.toString().trim() != "") {
            val simpleDateFormat = SimpleDateFormat("HH:mm")

            val date1 = simpleDateFormat.parse(inTime.text.toString().trim())
            val date2 = simpleDateFormat.parse(outTime.text.toString().trim())

            val difference: Long = date2.time - date1.time
            val days = (difference / (1000 * 60 * 60 * 24)).toInt()
            val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
            val min = (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)

            if (hours < 0 || min < 0) {
                et_stime.setText("")
                et_time.setText("")
                et_duration.setText("")
                ToastUtil.showErrorToast(
                    this, "Day total can not be calculated because of incorrect in and out time.")
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
}
