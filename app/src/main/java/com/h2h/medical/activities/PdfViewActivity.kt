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
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.h2h.medical.R
import com.h2h.medical.utils.AppPreferences
import java.io.File
import java.net.URI

class PdfViewActivity  : BaseActivity() {

    lateinit var Urii:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf_view_activity)

        Urii=Uri.parse(intent.getStringExtra("URI"))!!
        Log.e("URII",Urii.toString())
        showPdfFromUri(Urii)
    }

    private fun showPdfFromAssets(pdfName: String) {
//        pdfView.fromAsset(pdfName)
//            .password(null)
//            .defaultPage(0)
//            .onPageError { page, _ ->
//                Toast.makeText(
//                    this@PdfViewActivity,
//                    "Error at page: $page", Toast.LENGTH_LONG
//                ).show()
//            }
//            .load()
    }


    private fun showPdfFromUri(uri: Uri?) {
//        pdfView.fromUri(uri)
//            .defaultPage(0)
//            .spacing(10)
//            .load()
    }

    private fun showPdfFromFile(file: File) {
//        pdfView.fromFile(file)
//            .password(null)
//            .defaultPage(0)
//            .enableSwipe(true)
//            .swipeHorizontal(false)
//            .enableDoubletap(true)
//            .onPageError { page, _ ->
//                Toast.makeText(
//                    this@PdfViewActivity,
//                    "Error at page: $page", Toast.LENGTH_LONG
//                ).show()
//            }
//            .load()
    }





}