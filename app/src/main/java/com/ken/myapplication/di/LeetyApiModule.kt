package com.ken.myapplication.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.ken.myapplication.api.LeetyApi
import com.ken.myapplication.api.LeetyApiRepository
import com.ken.myapplication.api.LeetyApiRepositoryImpli
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LeetyApiModule {
    @Provides
    @Singleton
    fun provideLeetyApi(app : Application) : LeetyApi{
        val cacheSize = (10*1024*1024).toLong()
        val cache = Cache(app.cacheDir, cacheSize)
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .cache(cache)
            .addInterceptor{
                var request = it.request()
                request = if (hasNetwork(app) ==true)
                    request.newBuilder().header("Cache-Control","public, max-age="+5).build()
                else
                    request.newBuilder().header("Cache-Control","public, only-if-cached, max-stale="+60*60*24*7).build()
                it.proceed(request)
            }
            .build()
        return Retrofit.Builder()
            .baseUrl("https://leetcode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(LeetyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLeetyApiRepository(api: LeetyApi) : LeetyApiRepository{
        return LeetyApiRepositoryImpli(api)
    }

    private fun hasNetwork(app : Application): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}