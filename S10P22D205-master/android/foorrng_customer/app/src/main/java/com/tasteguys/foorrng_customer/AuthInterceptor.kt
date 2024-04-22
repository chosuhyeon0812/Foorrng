package com.tasteguys.foorrng_customer

import android.util.Log
import com.tasteguys.foorrng_customer.presentation.base.PrefManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val TAG = "AuthInterceptor_poorrng"
class AuthInterceptor @Inject constructor(
    private val prefManager: PrefManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val accessToken = prefManager.getAccessToken()

        if (accessToken != null) {
            request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        }
        Log.d(TAG, "okhttp.after_intercept : $request")
        return chain.proceed(request)
    }
}
