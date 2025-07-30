package com.h2h.medical.helper

import android.content.Context
import android.graphics.*
import android.os.SystemClock
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class SignatureView : View {

    private lateinit var mPath: Path
    private lateinit var mPaint: Paint

    private var bgPaint = Paint(Color.WHITE)

    private var mBitmap: Bitmap? = null
    private lateinit var mCanvas: Canvas

    private var curX: Float = 0.toFloat()
    private var curY: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        isFocusable = true
        mPath = Path()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = STROKE_WIDTH.toFloat()
    }

    fun setSigColor(color: Int) {
        mPaint.color = color
    }

    fun setSigColor(a: Int, red: Int, green: Int, blue: Int) {
        mPaint.setARGB(a, red, green, blue)
    }

    fun clearSignature(): Boolean {
        if (mBitmap != null)
            createFakeMotionEvents()
        if (mCanvas != null) {
            mCanvas.drawColor(Color.WHITE)
            mCanvas.drawPaint(bgPaint)
            mPath.reset()
            invalidate()
        } else {
            return false
        }
        return true
    }

    fun getImage(): Bitmap? {
        return mBitmap!!
    }

    fun setImage(bitmap: Bitmap) {
        this.mBitmap = bitmap
        this.invalidate()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        var bitmapWidth: Int = if (mBitmap != null) mBitmap!!.getWidth() else 0
        var bitmapHeight: Int = if (mBitmap != null) mBitmap!!.getWidth() else 0
        if (bitmapWidth >= width && bitmapHeight >= height)
            return
        if (bitmapWidth < width)
            bitmapWidth = width
        if (bitmapHeight < height)
            bitmapHeight = height
        val newBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
        val newCanvas = Canvas()
        newCanvas.setBitmap(newBitmap)
        if (mBitmap != null)
            newCanvas.drawBitmap(mBitmap!!, 0.toFloat(), 0.toFloat(), null)
        mBitmap = newBitmap
        mCanvas = newCanvas
    }

    private fun createFakeMotionEvents() {
        val downEvent = MotionEvent.obtain(
            SystemClock.uptimeMillis(),
            SystemClock.uptimeMillis() + 100,
            MotionEvent.ACTION_DOWN,
            1f,
            1f,
            0
        )
        val upEvent = MotionEvent.obtain(
            SystemClock.uptimeMillis(),
            SystemClock.uptimeMillis() + 100,
            MotionEvent.ACTION_UP,
            1f,
            1f,
            0
        )
        onTouchEvent(downEvent)
        onTouchEvent(upEvent)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(mBitmap!!, 0.toFloat(), 0.toFloat(), mPaint)
        canvas.drawPath(mPath, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.getX()
        val y = event.getY()
        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> touchDown(x, y)
            MotionEvent.ACTION_MOVE -> touchMove(x, y)
            MotionEvent.ACTION_UP -> touchUp()
        }
        invalidate()
        return true
    }

    private fun touchDown(x: Float, y: Float) {
        mPath.reset()
        mPath.moveTo(x, y)
        curX = x
        curY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - curX)
        val dy = Math.abs(y - curY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(curX, curY, (x + curX) / 2, (y + curY) / 2)
            curX = x
            curY = y
        }
    }

    private fun touchUp() {
        mPath.lineTo(curX, curY)
        if (mCanvas == null) {
            mCanvas = Canvas()
            mCanvas.setBitmap(mBitmap)
        }
        mCanvas.drawPath(mPath, mPaint)
        mPath.reset()
    }

    companion object {
        private val TOUCH_TOLERANCE = 4
        private val STROKE_WIDTH = 4
    }
}