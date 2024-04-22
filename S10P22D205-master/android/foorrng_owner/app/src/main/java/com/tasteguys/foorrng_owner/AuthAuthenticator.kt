//package com.tasteguys.foorrng_owner
//
//import android.util.Log
//import com.tasteguys.foorrng_owner.module.NetworkModule
//import com.tasteguys.foorrng_owner.presentation.base.PrefManager
//import kotlinx.coroutines.runBlocking
//import okhttp3.Authenticator
//import okhttp3.Request
//import okhttp3.Response
//import okhttp3.Route
//import javax.inject.Inject
//
//private const val TAG = "AuthAuthenticator_poorrng"
//class AuthAuthenticator @Inject constructor(
//    @NetworkModule.AuthUserService private val userService: UserService,
//    private val prefManager: PrefManager
//) : Authenticator {
//    override fun authenticate(route: Route?, response: Response): Request? {
//        Log.d(TAG, "authenticate: ")
//        val userId = prefManager.getUserId()
//        val accessToken = prefManager.getAccessToken()
//        val refreshToken = prefManager.getRefreshToken()
//
//        if (accessToken == null || refreshToken == null){
//            return null
//        }
//
//        return runBlocking {
//            val tokenResponse = userService.reissueToken(ReissueRequest(
//                userId, accessToken, refreshToken
//            ))
//
//            if (!tokenResponse.isSuccessful || tokenResponse.body() == null) {
//                Log.d(TAG, "authenticate: Fail")
//                prefManager.deleteAll()
//                null
//            } else {
//                Log.d(TAG, "authenticate Success: ${tokenResponse.body()!!.data.accessToken}, ${tokenResponse.body()!!.data.refreshToken}")
//                prefManager.setAccessToken(tokenResponse.body()!!.data.accessToken)
//                prefManager.setRefreshToken(tokenResponse.body()!!.data.refreshToken)
//                response.request.newBuilder()
//                    .header("Authorization", "Bearer ${tokenResponse.body()!!.data.accessToken}")
//                    .build()
//            }
//        }
//    }
//}
