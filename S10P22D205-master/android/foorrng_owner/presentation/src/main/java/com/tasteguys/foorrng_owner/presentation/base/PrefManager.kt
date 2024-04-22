package com.tasteguys.foorrng_owner.presentation.base

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

private const val TAG = "PrefManager_poorrng"
/**
 * Only Use By Hilt Injection
 *
 * 반드시 Hilt 모듈의 @Inject로 주입해 사용하세요
 */
class PrefManager(context: Context) {
    private var pref: SharedPreferences =
        context.getSharedPreferences("taste_preference", Activity.MODE_PRIVATE)

    init {
        Log.d(TAG, "Preference: ${this.pref.all}")
    }

    /**
     * @param id
     *
     * id is not idInput but server user index id
     *
     * 유저의 입력값이 아닌 카카오의 유저 인덱스 입니다.
     */
    fun setUserId(id: Long){
        pref.edit().apply {
            putLong(PREF_USERID, id)
            apply()
        }
    }

    /**
     * @return if there is no id, return -1
     */
    fun getUserId(): Long{
        return pref.getLong(PREF_USERID,-1)
    }

    fun setAccessToken(accessToken: String){
        pref.edit().apply {
            putString(PREF_ACCESSTOKEN, accessToken)
            apply()
        }
    }

    fun getAccessToken(): String? {
        return pref.getString(PREF_ACCESSTOKEN, null)
    }

    fun setRefreshToken(refreshToken: String){
        pref.edit().apply {
            putString(PREF_REFRESHTOKEN, refreshToken)
            apply()
        }
    }

    fun getRefreshToken(): String? {
        return pref.getString(PREF_REFRESHTOKEN, null)
    }

    var foodtruckId: Long
        set(value) {
            pref.edit().apply {
                putLong(PREF_FOODTRUCKID, value)
                apply()
            }
        }
        get() {
            return pref.getLong(PREF_FOODTRUCKID, -1)
        }

    fun setFcmToken(fcmToken: String){
        pref.edit().apply {
            putString(FCM_TOKEN, fcmToken)
            apply()
        }
    }

    fun getFcmToken(): String?{
        return pref.getString(FCM_TOKEN, null)
    }

    fun deleteAll(){
        val fcm = getFcmToken()
        pref.edit().let {
            it.clear()
            it.putString(FCM_TOKEN, fcm)
            it.commit()
        }
    }

    companion object{
        private const val PREF_USERID = "user_id"
        private const val PREF_FOODTRUCKID = "foodtruck_id"
        private const val PREF_ACCESSTOKEN = "access_token"
        private const val PREF_REFRESHTOKEN = "refresh_token"
        private const val FCM_TOKEN = "fcm_token"
        private const val FCM_NEW_TOKEN = "fcm_new_token"
    }
}