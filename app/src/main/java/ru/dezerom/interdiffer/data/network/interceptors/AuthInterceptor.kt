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
                    .add(ACCESS_TOKEN)
                    .build()
            )
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val ACCESS_TOKEN =
            "Authorization: Bearer 647fbe85647fbe85647fbe85cc676daf426647f647fbe8507d09ffa93aa3ccef8ebe8fc"
    }
}