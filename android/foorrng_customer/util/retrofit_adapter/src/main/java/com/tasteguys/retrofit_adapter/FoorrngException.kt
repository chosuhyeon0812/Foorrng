package com.tasteguys.retrofit_adapter

class FoorrngException(
    val code: String,
    override val message: String
):Exception(message){

    companion object{
        const val NOT_EXIST_USER = "U-001"
        const val ALREADY_DELETED = "D-001"
        const val ALREADY_REVIEW = "R-001"
    }
}