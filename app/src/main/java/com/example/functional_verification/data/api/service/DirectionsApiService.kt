/**
 * File Name: DirectionsApiService.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2024/11/22
 * Copyright: © 2024 Gyoushin Ou. All rights reserved.
 * Description: Google Maps Directions API のサービスインターフェース
 */
package com.example.functional_verification.data.api.service

import com.google.maps.model.DirectionsResult
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Google Maps Directions API のサービスインターフェース。
 *
 * このインターフェースは、出発地から目的地までの経路情報を取得するための
 * Google Maps Directions API とのやり取りを定義します。
 */
interface DirectionsApiService {

    /**
     * 緯度経度を使用して経路情報を取得します。
     *
     * @param origin 出発地の緯度経度（例：「34.7128,135.4602」）
     * @param destination 目的地の緯度経度（例：「35.6895,139.6917」）
     * @param waypoints 経由地点のリスト（オプション）。複数の場合はパイプ「|」で区切ります
     * @param apiKey Google Maps API キー
     * @return [DirectionsResult] 経路情報を含むレスポンス
     * @throws IOException ネットワークエラーが発生した場合
     * @throws HttpException APIがエラーレスポンスを返した場合
     */
    @GET("directions/json")
    suspend fun getDirectionsByLatLng(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("waypoints") waypoints: String? = null,
        @Query("key") apiKey: String,
    ): DirectionsResult
}