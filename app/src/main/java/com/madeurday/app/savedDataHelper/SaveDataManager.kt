package com.madeurday.app.savedDataHelper

import android.content.Context
import android.content.SharedPreferences
import com.aryahi.eventtow.pojo.LoginData
import android.text.TextUtils
import com.aryahi.eventtow.pojo.SearchObject
import com.google.gson.Gson

class SaveDataManager(context: Context) {
    init {
        var mContext : Context = context
    }
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "event_tow"
    private val FCM_TOKEN = "fcm_token"
    private val LOGIN_TOKEN = "login_token"
    private val LOGIN_DATA = "login_data"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    companion object {
        private val serialVersionUid: Long = 1
        var mInstance: SaveDataManager? = null
        fun getInstance(context: Context): SaveDataManager {
            if (mInstance == null)
                mInstance = SaveDataManager(context.applicationContext)
            return mInstance as SaveDataManager
        }
    }

    fun getFCMToken() : String? {
        if (TextUtils.isEmpty(sharedPref.getString(FCM_TOKEN, null)))
            return null

        val sharedNameValue = sharedPref.getString(FCM_TOKEN, null) as String
        return sharedNameValue
    }

    fun setFCMToken(token : String){
        val editor:SharedPreferences.Editor =  sharedPref.edit()
        editor.putString(FCM_TOKEN,token)
        editor.apply()
        editor.commit()
    }

    fun  setLoginData(loginData: String){
        val editor:SharedPreferences.Editor =  sharedPref.edit()
        editor.putString(LOGIN_DATA,loginData)
        editor.apply()
        editor.commit()
    }

    fun getLoginData() : LoginData? {
        val sharedNameValue = sharedPref.getString(LOGIN_DATA, null)
        if (!TextUtils.isEmpty(sharedNameValue)) {
            val loginData = Gson().fromJson(sharedNameValue, LoginData::class.java)
            return loginData
        } else {
            return null
        }
    }

    fun setLoginToken(token : String){
        val editor:SharedPreferences.Editor =  sharedPref.edit()
        editor.putString(LOGIN_TOKEN,token)
        editor.apply()
        editor.commit()
    }

    fun getLoginToken() : String? {
        val loginToken = sharedPref.getString(LOGIN_TOKEN, null)
       return loginToken;
    }
}