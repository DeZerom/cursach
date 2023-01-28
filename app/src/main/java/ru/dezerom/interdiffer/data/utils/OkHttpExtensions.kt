package ru.dezerom.interdiffer.data.utils

import okhttp3.RequestBody
import okio.Buffer

fun RequestBody?.bodyToString(): String {
    this ?: return ""

    val buffer = Buffer()
    writeTo(buffer)

    return buffer.readUtf8()
}

fun RequestBody?.add(key: String, value: String): RequestBody {
    val requestString = bodyToString()

    val newRequestBody = StringBuilder(requestString)
        .append("&\"$key\"=\"$value\"")
        .toString()

    return RequestBody.create(this?.contentType(), newRequestBody)
}