package edu.frank.androidStudy

import javax.inject.Inject

class ListRequestModel @Inject constructor(private val service: ApiService){

    fun updateStatus(position:Int):BaseResponse<Boolean>{
       return service.updateStatus(position)
    }
}