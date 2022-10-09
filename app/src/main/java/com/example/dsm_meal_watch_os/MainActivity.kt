package com.example.dsm_meal_watch_os

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ScrollView
import com.example.dsm_meal_watch_os.data.ApiClient
import com.example.dsm_meal_watch_os.data.Meal
import com.example.dsm_meal_watch_os.data.RetrofitService
import com.example.dsm_meal_watch_os.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

var urlDays = getUrlDate()
var urlTimes = getUrlTime()

private fun getUrlDate(): Int {
    val currentTime: Date = Calendar.getInstance().time
    val format_yy_mm_dd = "yyyyMMdd"
    val format2 = SimpleDateFormat(format_yy_mm_dd, Locale.getDefault())
    val formatDays: String = format2.format(currentTime)
    return formatDays.toInt()
}

private fun getUrlTime(): Int {

    var count = 0
    val calendar = Calendar.getInstance()
    val time = calendar.time.hours

    if (time in 0..8) {
        count = 1
    } else if (time in 8..13) {
        count = 2
    } else count = 3

    return count
}

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealService()
        btnCheck()



        binding.btnNext.setOnClickListener {
            if (urlTimes >= 3){
                urlTimes = 1
            } else urlTimes++
            mealService()
            btnCheck()
            binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
        }

    }


    fun mealService() {

        val service = ApiClient.getInstance().create(RetrofitService::class.java)
        val call : Call<Meal> = service.getMeal("3ae2bb166e5d4d3386d552a8dcc39737","json", "G10",
            "7430310", "$urlDays", "$urlTimes")
        call.enqueue(object : Callback<Meal> {
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                if (response.body()!=null) {
                    println("성공")
                    val res = response.body()!!.mealServiceDietInfo[1].row
                    Log.d("success", response.body().toString())
                    if (response.isSuccessful) {
                        for (i in res.indices) {
                            val obj = res[i]
                            val str = obj.DDISH_NM.replace("<br/>", "\n")
                            if (str ==""){
                                binding.tet.text = "급식이 없습니다."
                            }
                            else binding.tet.text = str
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                println("실패")
                Log.d("Test", t.toString())
            }
        })
    }

    fun btnCheck() {
        when (urlTimes) {
            1 -> {
                binding.btnNext.text = "아침"
            }
            2 -> {
                binding.btnNext.text = "점심"
            }
            else -> binding.btnNext.text = "저녁"
        }
    }

}