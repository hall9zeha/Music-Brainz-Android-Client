package com.barryzeha.musicbrainzclient.data.remote

import com.barryzeha.musicbrainzclient.common.MUSIC_BRAINZ_BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 22/08/25.
 * Copyright (c)  All rights reserved.
 ***/

object HttpClientProvider {
    fun create(): HttpClient{
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
                headers.append("Accept", "application/json")
                headers.append("Content-Type", "application/json")
            }
        }
    }
}