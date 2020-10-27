package edu.frank.androidStudy

import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("follow")
    @FormUrlEncoded
    fun updateStatus(position: Int): BaseResponse<Boolean>


}