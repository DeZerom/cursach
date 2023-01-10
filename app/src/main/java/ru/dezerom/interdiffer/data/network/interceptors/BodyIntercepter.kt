package ru.dezerom.interdiffer.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.dezerom.interdiffer.data.utils.add
import ru.dezerom.interdiffer.data.utils.bodyToString
import timber.log.Timber
import javax.inject.Inject

class BodyIntercepter @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = when (request.method()) {
            "POST" -> {
                val body = request.body()

                request.newBuilder()
                    .post(body.add(ACCESS_TOKEN_KEY, ACCESS_TOKEN))
                    .build()
            }
            else -> request
        }

        Timber.i(newRequest.body().bodyToString())

        return chain.proceed(newRequest)
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"

        private const val ACCESS_TOKEN =
            "647fbe85647fbe85647fbe85cc676daf426647f647fbe8507d09ffa93aa3ccef8ebe8fc"
    }
}