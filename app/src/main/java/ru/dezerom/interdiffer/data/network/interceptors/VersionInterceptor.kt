package ru.dezerom.interdiffer.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.dezerom.interdiffer.data.utils.bodyToString
import timber.log.Timber
import javax.inject.Inject

class VersionInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .url(
                request.url().newBuilder()
                    .addQueryParameter(VERSION_KEY, VERSION)
                    .build()
            )
            .build()

        Timber.i(newRequest.body().bodyToString())

        return chain.proceed(newRequest)
    }

    companion object {
        private const val VERSION_KEY = "v"
        private const val VERSION = "5.131"
    }
}
