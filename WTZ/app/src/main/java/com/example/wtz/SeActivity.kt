package com.example.wtz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.example.wtz.databinding.ActivitySettingBinding

class SeActivity : AppCompatActivity() {

    internal var x1: Float = 0.toFloat()
    internal var x2: Float = 0.toFloat()
    internal var y1: Float = 0.toFloat()
    internal var y2: Float = 0.toFloat()

    lateinit var bindingClass : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        bindingClass = ActivitySettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindingClass.root)
        supportActionBar?.hide()
        bindingClass.tvCheckEnglish.setOnClickListener {
            bindingClass.tvCheckRussian.isChecked = false
        }
        bindingClass.tvCheckRussian.setOnClickListener {
            bindingClass.tvCheckEnglish.isChecked = false
        }

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
                if (x1 < x2) {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                }
            }
        }
        return false
    }
}