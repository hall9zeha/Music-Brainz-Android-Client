package com.barryzeha.musicbrainzclient.data.remote

import com.barryzeha.musicbrainzclient.common.LookupEntity
import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.common.processResponse
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Release
import com.barryzeha.musicbrainzclient.data.model.entity.response.ArtistResponse
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

        val client by lazy { HttpClientProvider.create(appName,appVersion,contact) }
    // Generic search function for different entities
    suspend inline fun <reified T>searchEntity(
        entity: SearchEntity,
        query: String,
        limit: Int = 50,
        offset: Int = 0
    ): MbResponse<T> {
        return processResponse {
            client.get(entity.path){
                url {
                    parameters.append("query", query)
                    parameters.append("limit", limit.toString())
                    parameters.append("offset", offset.toString())
                }
            }
        }

    }
    // Specific search function for recordings
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
    // Specific search function for artists
    suspend fun searchArtist(
        query: String,
        limit: Int = 50,
        offset: Int = 0): MbResponse<ArtistResponse> {
        return processResponse {
            client.get("artist"){
                url {
                    parameters.append("query", query)
                    parameters.append("limit", limit.toString())
                    parameters.append("offset", offset.toString())
                }
            }
        }
    }
    // Specific search function for releases
    suspend fun searchRelease(
        query: String,
        limit: Int = 50,
        offset: Int = 0
    ): MbResponse<ReleaseResponse> {
        return processResponse {
            client.get("release"){
                url {
                    parameters.append("query", query)
                    parameters.append("limit", limit.toString())
                    parameters.append("offset", offset.toString())
                }
            }
        }

    }
    suspend fun searchReleaseGroup(query: String,
                                    limit: Int = 50,
                                    offset: Int = 0
    ): MbResponse<ReleaseResponse> {
        return processResponse {
            client.get("release-group"){
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
    suspend  inline fun<reified T> lookupEntity(
        entity: LookupEntity,
        mbId: String,
        inc: String?=null
        ): MbResponse<T> {
        return processResponse {
            client.get("${entity.path}/$mbId"){
                url {
                    inc?.let {
                        parameters.append("inc", it)
                    }
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