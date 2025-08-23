package com.barryzeha.musicbrainzclient.data.remote

import com.barryzeha.musicbrainzclient.common.processResponse
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Release
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.RecordingResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.ReleaseResponse
import io.ktor.client.request.get

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
            offset: Int = 0): MbResponse<RecordingResponse> {
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
        suspend fun getReleaseById(query: String): MbResponse<ReleaseResponse> {
            return processResponse {
                client.get("release"){
                    url{
                        parameters.append("query",query)
                    }
                }
            }

        }
    suspend fun lookupReleaseById(id: String): MbResponse<Release> {
        return processResponse {
            client.get("release/$id")
        }

    }


}