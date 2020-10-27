package edu.frank.androidStudy

class BaseResponse<T> {
    val code: Int = 0
    val message: String = "未知错误"
    val data: T? = null
}