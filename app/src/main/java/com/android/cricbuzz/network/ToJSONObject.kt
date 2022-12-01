package com.android.cricbuzz.network

import android.content.Context
import com.android.cricbuzz.utils.CommonUtils
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject

class ToJSONObject @Inject constructor(private val moshi: Moshi, private val context: Context){
    /**
     * @param jsonString: String
     * @param classtype : Class<T>
     * @return adapter Object
     */
    fun <T : Any?> getJSONObject(json: String, type: Class<T>): T? {
        val adapter: JsonAdapter<T> = moshi.adapter(type)
        return adapter.fromJson(json)
    }
    /**
     * @param filename: String
     * @param type : Class<T>
     * @return json Object
     */
      suspend fun <T : Any?> processJsonFromFile(filename: String, type: Class<T>): T? {
        val jsonData = CommonUtils.getJSON(filename, context)
        return getJSONObject(jsonData, type)
    }
}