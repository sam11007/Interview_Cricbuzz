package com.android.cricbuzz.network

import android.content.Context
import com.android.cricbuzz.utils.CommonUtils
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject

class ToJSONObject @Inject constructor(private val moshi: Moshi, private val context: Context){
       fun <T : Any?> getJSONObject(json: String, type: Class<T>): T? {
        val adapter: JsonAdapter<T> = moshi.adapter(type)
        return adapter.fromJson(json)
    }

      suspend fun <T : Any?> processJsonFromFile(filename: String, type: Class<T>): T? {
        val jsonData = CommonUtils.getJSON(filename, context)
        return getJSONObject(jsonData, type)
    }
}