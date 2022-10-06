package com.example.dsm_meal_watch_os.data

import com.example.dsm_meal_watch_os.data.Meal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {



    @GET("mealServiceDietInfo")
    fun getMeal(@Query("KEY") key : String, @Query("Type") Type:String, @Query("ATPT_OFCDC_SC_CODE") areaCode : String,
                @Query("SD_SCHUL_CODE") schoolCode : String, @Query("MLSV_YMD") urlDays : String, @Query("MMEAL_SC_CODE") MMEAL_SC_CODE:String)
    : Call<Meal>


}