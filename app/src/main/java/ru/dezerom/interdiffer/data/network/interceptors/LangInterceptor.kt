package ru.dezerom.interdiffer.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LangInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .url(
                request.url().newBuilder()
                    .addQueryParameter(LANG_KEY, LANG)
                    .build()
            )
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val LANG_KEY = "lang"
        private const val LANG = "ru"
    }
}