package com.barryzeha.musicbrainzclient.data.remote

import com.barryzeha.musicbrainzclient.common.COVER_ART_BACK
import com.barryzeha.musicbrainzclient.common.COVER_ART_BOTH_SIDES
import com.barryzeha.musicbrainzclient.common.COVER_ART_FRONT
import com.barryzeha.musicbrainzclient.common.LookupEntity
import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.common.getThumbnail
import com.barryzeha.musicbrainzclient.common.processResponse
import com.barryzeha.musicbrainzclient.data.model.entity.coverentity.Thumbnails
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Release
import com.barryzeha.musicbrainzclient.data.model.entity.response.ArtistResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtUrls
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
        val coverArtClient by lazy { HttpClientProvider.createCoverArtClient() }

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
    // Generic lookup function for different entities
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
    // End generic region

    // Specific fetches function for cover art archive
    suspend fun fetchCoverArt(mbId: String): MbResponse<CoverArtResponse>{
        return processResponse {
            coverArtClient.get("release/$mbId")
        }
    }
    // Specific request to get only thumbnails url fo cover art
    suspend fun fetchCoverArtThumbnails(mbId:String): MbResponse<List<Thumbnails>>{
        val response = processResponse<CoverArtResponse> {
            coverArtClient.get("release/$mbId")
        }
        val thumbnails =  mutableListOf<Thumbnails>()
        when(response){
            is MbResponse.Error -> {
               return MbResponse.Error(response.error)
            }
            is MbResponse.Success<CoverArtResponse> -> {
                response.response.images.forEach { image->
                    thumbnails.add(image.thumbnails)
                }
            }
        }
        return MbResponse.Success(thumbnails)
    }
    // Specific request to get covert art front or back
    suspend fun fetchCoverArt(mbId:String, side:Int, size:Int):MbResponse<CoverArtUrls>{
        return when (val response = processResponse<CoverArtResponse> {
            coverArtClient.get("release/$mbId")
        }) {
            is MbResponse.Error -> response

            is MbResponse.Success -> {
                val images = response.response.images
                val coverArt = CoverArtUrls()

                    coverArt.front = when (side) {
                        COVER_ART_FRONT, COVER_ART_BOTH_SIDES ->
                           images.firstOrNull { it.front }?.getThumbnail(size)
                        else -> null
                    }
                    coverArt.back = when (side) {
                        COVER_ART_BACK, COVER_ART_BOTH_SIDES ->
                            images.firstOrNull { it.back }?.getThumbnail(size)
                        else -> null
                    }

                MbResponse.Success(coverArt)
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

    suspend fun lookupReleaseById(id: String): MbResponse<Release> {
        return processResponse {
            client.get("release/$id")
        }

    }


}