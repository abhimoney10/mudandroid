package com.madeurday.app.network

import org.json.JSONObject

public interface NetworkCallBack {
    fun successResponse(response : JSONObject)
    abstract fun error()
    abstract fun error(errorMsg : String)
}