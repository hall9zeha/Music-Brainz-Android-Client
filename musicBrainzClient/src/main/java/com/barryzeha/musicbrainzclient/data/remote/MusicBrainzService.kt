package com.barryzeha.musicbrainzclient.data.remote

import com.barryzeha.musicbrainzclient.common.processResponse
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Release
import com.barryzeha.musicbrainzclient.data.model.entity.response.ErrorResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.MusicBrainzResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

class MusicBrainzService(private val appName:String?=null,
                         private val appVersion:String?=null,
                         private val contact:String?=null ) {

        private val client by lazy { HttpClientProvider.create(appName,appVersion,contact) }

        suspend fun searchRecording(
            query: String,
            limit: Int = 50,
            offset: Int = 0): MbResponse<MusicBrainzResponse> {
            return processResponse {
                client.get("recording"){
                    url {
                        parameters.append("query", query)
                        parameters.append("limit", limit.toString())
                        parameters.append("offset", offset.toString())
                    }
                }
            }

        }
        suspend fun getReleaseById(id: String): MbResponse<Release> {
            return processResponse {
                client.get("release/$id")
            }

        }


}