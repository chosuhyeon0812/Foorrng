package com.tasteguys.retrofit_adapter

class FoorrngException(
    val code: String,
    override val message: String
):Exception(message)