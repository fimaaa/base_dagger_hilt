package com.network.crypto.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject

class JSONObjectAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): JSONObject? {
        return (reader.readJsonValue() as? Map<*, *>)?.let { data ->
            try {
                JSONObject(data)
            } catch (_: JSONException) {
                return null
            }
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: JSONObject?) {
        value?.let { writer.value(Buffer().writeUtf8(value.toString())) }
    }
}