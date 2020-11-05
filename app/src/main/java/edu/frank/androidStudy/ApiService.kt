package edu.frank.androidStudy

import androidx.lifecycle.LiveData
import edu.frank.androidStudy.http.BaseResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("follow")
    @FormUrlEncoded
    suspend fun updateStatus(@Field("position") position: Int): BaseResponse<String>

    @POST("follow")
    @FormUrlEncoded
    fun updateStatus2(@Field("position") position: Int): LiveData<BaseResponse<String>>
}