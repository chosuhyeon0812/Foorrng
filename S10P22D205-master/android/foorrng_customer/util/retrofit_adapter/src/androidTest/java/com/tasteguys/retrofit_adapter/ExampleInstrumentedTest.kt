package com.tasteguys.retrofit_adapter

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.json.JSONObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.tasteguys.retrofit_adapter.test", appContext.packageName)
    }

    @Test
    fun json_errorbody_converter_test() {
        val errorBody = "{\n" +
                "  \"dataHeader\": {\n" +
                "    \"successCode\": 1,\n" +
                "    \"resultCode\": \"U-001\",\n" +
                "    \"resultMessage\": \"존재하지 않는 회원입니다.\"\n" +
                "  },\n" +
                "  \"dataBody\": null\n" +
                "}"
        val eMsg = JSONObject(errorBody).getJSONObject("dataHeader").getString("resultMessage")
        assertEquals("존재하지 않는 회원입니다.", eMsg)
    }
}