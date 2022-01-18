package com.example.wtz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent

class InfoActivity : AppCompatActivity() {

    internal var x1: Float = 0.toFloat()
    internal var x2: Float = 0.toFloat()
    internal var y1: Float = 0.toFloat()
    internal var y2: Float = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        supportActionBar?.hide()
    }

    override fun onTouchEvent(tochevent: MotionEvent): Boolean {
        when (tochevent.action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = tochevent.x
                y1 = tochevent.y
            }
            MotionEvent.ACTION_UP -> {
                x2 = tochevent.x
                y2 = tochevent.y
                if (x2 < x1) {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                }
            }
        }
        return false
    }
}