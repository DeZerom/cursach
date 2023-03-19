package ru.dezerom.interdiffer.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .headers(
                request.headers.newBuilder()
                    .add(TokenObj.ACCESS_TOKEN)
                    .build()
            )
            .build()

        return chain.proceed(newRequest)
    }
}