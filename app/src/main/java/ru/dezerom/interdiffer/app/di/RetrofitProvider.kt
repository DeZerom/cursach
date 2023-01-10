package ru.dezerom.interdiffer.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dezerom.interdiffer.data.network.apis.UsersApiService
import ru.dezerom.interdiffer.data.network.interceptors.BodyIntercepter

@Module
@InstallIn(SingletonComponent::class)
class RetrofitProvider {

    @Provides
    fun provideOkHttpClient(interceptor: BodyIntercepter): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)

    @Provides
    fun provideUsersApiService(builder: Retrofit.Builder): UsersApiService =
        builder
            .baseUrl(VK_BASE_URL)
            .build()
            .create(UsersApiService::class.java)

    companion object {
        private const val VK_BASE_URL = "https://api.vk.com/method/"
    }

}