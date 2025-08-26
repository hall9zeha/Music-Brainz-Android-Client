package com.barryzeha.musicbrainzclient.data.remote

import com.barryzeha.musicbrainzclient.common.COVER_ARCHIVE_BASE_URL
import com.barryzeha.musicbrainzclient.common.MUSIC_BRAINZ_BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 22/08/25.
 * Copyright (c)  All rights reserved.
 ***/

object HttpClientProvider {
    fun create(appName:String?=null, appVersion:String?=null, contact:String?=null): HttpClient{
        return HttpClient{
            install(ContentNegotiation){
                json(
                    Json{
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(Logging){
                level = LogLevel.INFO
                logger = Logger.DEFAULT
            }
            install(DefaultRequest){
                url(MUSIC_BRAINZ_BASE_URL)
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json"
                if(appName !=null && appVersion !=null && contact !=null){
                    headers["User-Agent"] = "$appName/$appVersion ($contact)"
                }
            }
        }
    }
    fun createCoverArtClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        isLenient = true
                    })
            }
            install(Logging) {
                level = LogLevel.INFO
                logger = Logger.DEFAULT
            }
            install(DefaultRequest) {
                url(COVER_ARCHIVE_BASE_URL)
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json"
            }
        }
    }
}