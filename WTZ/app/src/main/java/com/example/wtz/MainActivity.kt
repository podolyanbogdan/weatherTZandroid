package com.example.wtz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import com.example.wtz.SeActivity
import com.example.wtz.databinding.ActivityMainBinding
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass : ActivityMainBinding

    var city : String = "Kirovohrad"
    val api : String = "ff6cf1dad81791b1f5f63c8f3a16a32c"

    internal var x1: Float = 0.toFloat()
    internal var x2: Float = 0.toFloat()
    internal var y1: Float = 0.toFloat()
    internal var y2: Float = 0.toFloat()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        supportActionBar?.hide()
        weatherTask().execute()
    }

    override fun onResume() {
        super.onResume()
        bindingClass.spCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                if (adapterView != null)
                {
                    adapterView.getItemAtPosition(1)
                    bindingClass.tvCity.text = adapterView.getItemAtPosition(position).toString()
                    city = adapterView.getItemAtPosition(position).toString()
                    weatherTask().execute()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?){

            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class weatherTask() : AsyncTask<String, Void, String>()
    {

        override fun doInBackground(vararg params: String?) : String? {
            var response:String?
            var response2:String?
            try {//zapros na api
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&lang=ru&appid=$api").readText(Charsets.UTF_8)
            }
            catch(e: Exception)
            {
                response = null
            }
            return response
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                //json returns from api
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updateAt: Long = jsonObj.getLong("dt")
                val updateAtText = "Обновлено: " +
                        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH)
                            .format(Date(updateAt*1000))
                val temp = main.getString("temp") + "°C"
                // val dayToday = main.getString("dt_txt")
                val tempMin = "Мин темп: " + main.getString("temp_min") + "°C"
                val tempMax = "Макс темп: " + main.getString("temp_max") + "°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")
                ///////////////add value to textview

                bindingClass.tvCity.text = address
                bindingClass.tvUpdateTime.text = updateAtText
                bindingClass.tvCondition.text = weatherDescription.capitalize()
                bindingClass.tvGradusi.text = temp
                bindingClass.tvMinGradus.text = tempMin
                bindingClass.tvMaxGradus.text = tempMax
                // bindingClass.tvDayToday.text = dayToday
                bindingClass.tvSunriseValue.text = SimpleDateFormat("HH:mm", Locale.ENGLISH)
                    .format(Date(sunrise*1000))
                bindingClass.tvSunsetValue.text = SimpleDateFormat("HH:mm", Locale.ENGLISH)
                    .format(Date(sunset*1000))
                bindingClass.tvWinterValue.text = "$windSpeed м/с"
                bindingClass.tvPressureValue.text = "$pressure мм"
                bindingClass.tvWetValue.text = "$humidity%"
                val sdf = SimpleDateFormat("EEEE")
                val d = Date()
                val dayOfTheWeek = sdf.format(d)
                bindingClass.tvDayToday.text = dayOfTheWeek.capitalize()

                /*
                if(bindingClass.tvCondition.text == "Пасмурно" || bindingClass.tvCondition.text == "Туман")
                {
                    bindingClass.mainLayout.setBackgroundResource(R.drawable.backgroundpasmurno)
                }

                if(bindingClass.tvCondition.text == "Снег" || bindingClass.tvCondition.text == "Небольшой снег")
                {
                    bindingClass.mainLayout.setBackgroundResource(R.drawable.backgroundsnow)
                }

                if(bindingClass.tvCondition.text == "Ясно")
                {
                    bindingClass.mainLayout.setBackgroundResource(R.drawable.backgroundyasno)
                }

                if(bindingClass.tvCondition.text == "Облачно с прояснениями" || bindingClass.tvCondition.text == "Небольшой дождь")
                {
                    bindingClass.mainLayout.setBackgroundResource(R.drawable.backgroundmain)
                }
                if(bindingClass.tvCondition.text == "Небольшая облачность")
                {
                    bindingClass.mainLayout.setBackgroundResource(R.drawable.backgroundnebolshayaobla)
                }

                 */
            }
            catch (e: Exception)
            {
            }
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
                    val i = Intent(this, InfoActivity::class.java)
                    startActivity(i)
                } else if(x2 < x1)
                {
                    val i = Intent(this, SeActivity::class.java)
                    startActivity(i)
                }
            }
        }
        return false
    }
}