package ru.dezerom.interdiffer.app.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dezerom.interdiffer.data.network.apis.UsersApiService
import ru.dezerom.interdiffer.data.network.interceptors.AuthInterceptor
import ru.dezerom.interdiffer.data.network.interceptors.LangInterceptor
import ru.dezerom.interdiffer.data.network.interceptors.VersionInterceptor

@Module
@InstallIn(SingletonComponent::class)
class RetrofitProvider {

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        versionInterceptor: VersionInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        langInterceptor: LangInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(versionInterceptor)
            .addInterceptor(langInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()

    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context)
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