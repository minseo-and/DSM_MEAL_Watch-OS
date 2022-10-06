package com.example.dsm_meal_watch_os

import android.app.Activity
import android.os.Bundle
import com.example.dsm_meal_watch_os.databinding.ActivityMainBinding
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

    }
}