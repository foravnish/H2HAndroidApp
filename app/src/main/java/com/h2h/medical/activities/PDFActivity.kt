package com.h2h.medical.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.h2h.medical.Volley.BuildConfig
import com.h2h.medical.R
import com.h2h.medical.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_pdf_list.*
import java.io.File

class PDFActivity : BaseActivity() {

    private lateinit var mTvEmail: TextView
    private lateinit var mTvPhone: TextView
    private lateinit var mAppPreferences: AppPreferences
    companion object {
        fun open(myActivity: Activity) {
            val intent = Intent(myActivity, PDFActivity::class.java)
            myActivity.startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pdf_list)
        val layout = findViewById<LinearLayout>(R.id.pdfURL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val path: String = "/storage/emulated/0/Android/data/com.h2h.medical/files/H2H Reports/H2H Reports/"
            Log.d("Files", "Path: $path")
            val directory = File(path)
            if(directory.listFiles()!=null){
                nodata.visibility=View.GONE
                val files: Array<File> = directory.listFiles()
                files.reverse()
                for (i in files.indices)
                {
                    Log.d("Files", "FileName:" + files[i])
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    val f = File("/storage/emulated/0/Android/data/com.h2h.medical/files/H2H Reports/H2H Reports/Pavan09-18-2022 10:20.pdf")
                    // Create TextView programmatically.
                    val textView = TextView(this)

                    // setting height and width
                    val buttonLayoutParams: LinearLayout.LayoutParams =
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    buttonLayoutParams.setMargins(10, 10, 10, 0);
                    textView.layoutParams=buttonLayoutParams

                    textView.setText(files[i].getName())
                    textView.setPadding(25,30,10,30)

                    textView.setBackgroundResource(R.drawable.pdf_back);
                    textView.gravity = Gravity.CENTER_VERTICAL or Gravity.BOTTOM


                    textView.setTextAppearance(this, R.style.WeeklyInsideContainerEditText1);

                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    textView.setTextColor(Color.BLACK)
                    // onClick the text a message will be displayed "HELLO GEEK"
                    textView.setOnClickListener()
                    {
                        val mypath= files[i]
                        val path = mypath.path;
                        val name = mypath.name;
//                        val mypath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path + "/H2H Reports/" + files[i].name
                       ///storage/emulated/0/Documents/H2H Reports/0102202303:59Pavan.pdf
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.putExtra(Intent.EXTRA_STREAM,  uriFromFile(this,mypath))
                        shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        shareIntent.type = "application/pdf"
                        startActivity(Intent.createChooser(shareIntent, "share.."))

                    }

                    // Add TextView to LinearLayout
                    layout ?.addView(textView)
                }
            }
            else
                nodata.visibility=View.VISIBLE


        }
        else{
            val path: String = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path + "/H2H Reports/"
            Log.d("Files", "Path: $path")
            val directory = File(path)
            if(directory.listFiles()!=null)
            {
                nodata.visibility=View.GONE
                val files: Array<File> = directory.listFiles()
                files.reverse()
                Log.d("Files", "Size: " + files.size)
                for (i in files.indices) {
                    Log.d("Files", "FileName:" + files[i].getName())

                    // Create TextView programmatically.
                    val textView = TextView(this)

                    val buttonLayoutParams: LinearLayout.LayoutParams =
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    buttonLayoutParams.setMargins(10, 10, 10, 0);
                    textView.layoutParams=buttonLayoutParams

                    textView.setText(files[i].getName())
                    textView.setPadding(25,30,10,30)

                    textView.setBackgroundResource(R.drawable.pdf_back);
                    textView.gravity = Gravity.CENTER_VERTICAL or Gravity.BOTTOM
                    textView.setTextAppearance(this, R.style.WeeklyInsideContainerEditText1);

                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    textView.setTextColor(Color.BLACK)
                    // onClick the text a message will be displayed "HELLO GEEK"
                    textView.setOnClickListener()
                    {
                        val mypath=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path + "/H2H Reports/" + files[i].name
                       ///storage/emulated/0/Android/data/com.h2h.medical/files/H2H Reports/H2H Reports/0102202303:59Pavan.pdf
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "application/pdf"
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$mypath").toString())
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, mypath.replace(".pdf", ""))
                        startActivity(Intent.createChooser(shareIntent, "SHARE DAILY REPORT"))
//                        val intent=Intent(this,PdfViewActivity::class.java)
//                        intent.putExtra("URI",Uri.parse("file://$mypath").toString())
//                        startActivity(intent)
                    }

                    // Add TextView to LinearLayout
                    layout ?.addView(textView)
                }
            }
            else
                nodata.visibility=View.VISIBLE


        }







       // initViews()
      //  initListeners()
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

    private fun initViews() {
        mTvEmail = findViewById(R.id.tv_email)
        mTvPhone = findViewById(R.id.tv_phone)
    }
}