package edu.frank.androidStudy.http

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class BaseResponse<T> {
    companion object {
        fun <T> create(error: Throwable): BaseResponse<T> {
            val response: BaseResponse<T> =
                BaseResponse()
            when (error) {
                is UnknownHostException -> {
                    response.code = 602
                    response.message = "网络连接异常,请检查您的网络状态"
                }
                is SocketTimeoutException, is ConnectException, is NoRouteToHostException -> {
                    response.code = 600
                    response.message = "网络连接超时,请检查您的网络状态"
                }
                is HttpException -> {
                    response.code = error.response()?.code()?:600
                    response.message = "网络连接异常,请检查您的网络状态"
                }
                is JsonSyntaxException -> {
                    response.code = 604
                    response.message = "数据解析异常"
                }
                else -> {
                    response.code = 601
                    response.message = error.message ?: "Unknown Error"
                }
            }
            return response
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> create(response: Response<T>): BaseResponse<T> {
            if (response.isSuccessful) {
                return response.body() as BaseResponse<T>
            } else {
                val baseResponse = BaseResponse<T>()
                baseResponse.code = response.code()
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                baseResponse.message = errorMsg
                return baseResponse
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> create(response: Response<T>,uniqueId:String = ""): BaseResponse<T> {
            if (response.isSuccessful) {
                val baseResponse = response.body() as BaseResponse<T>
                baseResponse.uniqueId = uniqueId
                return response.body() as BaseResponse<T>
            } else {
                val baseResponse = BaseResponse<T>()
                baseResponse.code = response.code()
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                baseResponse.message = errorMsg
                baseResponse.uniqueId = uniqueId
                return baseResponse
            }
        }
    }

    var code: Int = 404
    var message: String = ""
    var data: T? = null
    var uniqueId:String? = ""
}