package com.tasteguys.retrofit_adapter

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getTimeMillis() {
        val dateformat = SimpleDateFormat("HH:mm", Locale.KOREA)
        val start = "09:00"
        val end = "18:00"
        println("start : ${dateformat.parse(start).time}")
        println("end : ${dateformat.parse(end).time}")
    }
}