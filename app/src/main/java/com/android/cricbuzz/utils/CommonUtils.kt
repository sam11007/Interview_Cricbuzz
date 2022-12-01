package com.android.cricbuzz.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream

object CommonUtils {
   suspend fun getJSON(fileName: String, context: Context): String {
        val inputStream = getInputStreamForJsonFile(fileName, context)
        return inputStream.bufferedReader().use { it.readText() }
    }
    @Throws(IOException::class)
    suspend fun getInputStreamForJsonFile(fileName: String, context: Context): InputStream {
        return context.assets.open(fileName)
    }
}