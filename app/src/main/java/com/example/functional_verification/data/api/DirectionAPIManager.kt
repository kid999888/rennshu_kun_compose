/**
 * File Name: DirectionAPIManager.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2024/11/22
 * Copyright: © 2024 Gyoushin Ou. All rights reserved.
 * Description: Google Maps Directions API との通信を管理するオブジェクト。
 */
package com.example.functional_verification.data.api

import com.example.functional_verification.data.api.service.DirectionsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DirectionAPIManager {
    /** API のベース URL */
    private const val BASE_URL = "https://maps.googleapis.com/maps/api/"

    /**
     * HTTP通信のログを出力するためのインターセプター。
     * ボディレベルでログを出力するように設定されています。
     */
    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /**
     * カスタマイズされた OkHttpClient。
     * ログインターセプターが追加されています。
     */
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    /**
     * Retrofit インスタンスを提供します。
     *
     * @return 設定済みの Retrofit インスタンス
     */
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    /**
     * DirectionsApiService のインスタンスを提供します。
     *
     * @param retrofit 使用する Retrofit インスタンス
     * @return DirectionsApiService のインスタンス
     */
    fun provideDirectionsApiService(retrofit: Retrofit): DirectionsApiService {
        return retrofit.create(DirectionsApiService::class.java)
    }
}