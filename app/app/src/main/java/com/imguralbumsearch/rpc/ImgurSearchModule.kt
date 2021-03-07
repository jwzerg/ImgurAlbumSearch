package com.imguralbumsearch.rpc

import com.imguralbumsearch.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
class ImgurSearchModule() {

    @Provides
    fun provideImgurSearchService(): ImgurSearchService {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request =
                    chain.request().newBuilder()
                        .header("Authorization", "Client-ID ${BuildConfig.CLIENT_ID}")
                        .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        return retrofit.create(ImgurSearchService::class.java)
    }
}