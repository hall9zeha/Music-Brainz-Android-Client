package com.barryzeha.musicbrainzclient.data.remote

import com.barryzeha.musicbrainzclient.common.COVER_ART_BACK
import com.barryzeha.musicbrainzclient.common.COVER_ART_BOTH_SIDES
import com.barryzeha.musicbrainzclient.common.COVER_ART_FRONT
import com.barryzeha.musicbrainzclient.common.CoverSize
import com.barryzeha.musicbrainzclient.common.LookupEntity
import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.common.SearchField
import com.barryzeha.musicbrainzclient.common.allTrim
import com.barryzeha.musicbrainzclient.common.getThumbnail
import com.barryzeha.musicbrainzclient.common.processResponse
import com.barryzeha.musicbrainzclient.common.processResponses
import com.barryzeha.musicbrainzclient.common.utils.GenericQueryBuilder
import com.barryzeha.musicbrainzclient.data.model.entity.coverentity.Thumbnails
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Release
import com.barryzeha.musicbrainzclient.data.model.entity.response.ArtistResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtUrls
import com.barryzeha.musicbrainzclient.data.model.entity.response.ErrorResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.RecordingResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.ReleaseResponse
import com.barryzeha.musicbrainzclient.data.repository.MbRepository
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlin.reflect.KClass

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

class MusicBrainzService(private val appName:String?=null,
                         private val appVersion:String?=null,
                         private val contact:String?=null ){

        val client by lazy { HttpClientProvider.create(appName,appVersion,contact) }
        val coverArtClient by lazy { HttpClientProvider.createCoverArtClient() }

    // Generic search function for different entities
    suspend fun <T: Any>searchEntity(
        entity: SearchEntity,
        query: String,
        limit: Int = 50,
        offset: Int = 0,
        clazz: KClass<T>
    ): MbResponse<T> {
        return processResponse(clazz) {
            client.get(entity.path) {
                url {
                    parameters.append("query", query)
                    parameters.append("limit", limit.toString())
                    parameters.append("offset", offset.toString())
                }
            }
        }

    }
    // Generic lookup function for different entities
   suspend  fun<T: Any> lookupEntity(
        entity: LookupEntity,
        mbId: String,
        inc: String?=null,
        clazz: KClass<T>
    ): MbResponse<T> {
        return processResponse(clazz) {
            client.get("${entity.path}/$mbId") {
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
        return processResponse(CoverArtResponse::class) {
            coverArtClient.get("release/$mbId")
        }
    }
    // Specific request to get only thumbnails url fo cover art
    suspend fun fetchCoverArtThumbnails(mbId:String): MbResponse<List<Thumbnails>>{
        val response = processResponse(CoverArtResponse::class) {
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
    suspend fun  fetchCoverArt(mbId:String, side:Int, size: CoverSize):MbResponse<CoverArtUrls>{
        return when (val response = processResponse(CoverArtResponse::class) {
            coverArtClient.get("release/$mbId")
        }) {
            is MbResponse.Error -> response

            is MbResponse.Success -> {
                val images = response.response.images
                val coverArt = CoverArtUrls()

                    coverArt.front = when (side) {
                        COVER_ART_FRONT, COVER_ART_BOTH_SIDES ->
                           images.firstOrNull { it.front }?.getThumbnail(size.value)
                        else -> null
                    }
                    coverArt.back = when (side) {
                        COVER_ART_BACK, COVER_ART_BOTH_SIDES ->
                            images.firstOrNull { it.back }?.getThumbnail(size.value)
                        else -> null
                    }

                MbResponse.Success(coverArt)
            }
        }
    }
    // Specific search function for cover art whit name of track
    // only get first match for default
    suspend fun fetchCoverArtByTitleAndArtist(title:String, artist:String, side:Int, size: CoverSize, firstOnly:Boolean=true):MbResponse<List<CoverArtUrls>>{
        var releaseId:String?=null
        var releaseIds: MutableList<String> = mutableListOf()
        var coverArtUrls: MutableList<CoverArtUrls> = mutableListOf()
        var responses:MutableList<HttpResponse> = mutableListOf()
        val queryWithName = GenericQueryBuilder()
            .field(SearchField.RECORDING,title)
            .field(SearchField.ARTIST,artist)
            .build()

        val recordingResponse = client.get("recording"){
            url {
                parameters.append("query", queryWithName)
                parameters.append("limit", 10.toString())
                parameters.append("offset", 0.toString())
            }
        }
        if(recordingResponse.status.isSuccess()){
            // Filter the list of recordings that contain the song title field in order to later
            // filter the child objects of each recording
            val recordings = recordingResponse.body<RecordingResponse>().recordings.filter{recording->

                //
                val release = recording.releases?.firstOrNull {release->
                    // First, check that the artist matches the one we are searching for
                    val artistCredit = release.artistCredit?.firstOrNull {artistCredit->artistCredit.name.lowercase().allTrim()==artist.lowercase().allTrim()  }

                    // Check if the media object inside the release has the field "Digital Media"
                    // because if it does, it's more likely that the related release will also have cover art available
                    val media = release.media?.firstOrNull { media->
                        media.format?.trim()?.lowercase() == "Digital Media".trim().lowercase()
                    }
                    // If it's not null, return true so we keep the current object
                    if(artistCredit !=null && media!=null) true
                    // Otherwise, continue filtering until the condition is met or the list ends
                    else false
                }

                if(release !=null)recording.title.allTrim().lowercase() == title.lowercase().allTrim()
                else false
            }

            if(recordings.isNotEmpty()){
                releaseId = recordings[0].releases?.get(0)?.id
                recordings.forEach { r->
                    r.releases?.forEach { release->
                        releaseIds.add(release.id.toString())
                    }
                }

        return when (val response = processResponses<CoverArtResponse> {
            (releaseId?.let {
                while (releaseIds.isNotEmpty()) {
                    val resp = coverArtClient.get("release/${releaseIds[0]}")
                    if (resp.status.isSuccess()) {
                        responses?.add(resp)
                        //result = resp
                        releaseIds.removeAt(0)
                        if(firstOnly) break
                    } else {
                        releaseIds.removeAt(0)
                    }
                }
                responses ?:MbResponse.Error(ErrorResponse(-1, "No valid cover art found"))
                //result ?:MbResponse.Error(ErrorResponse(-1, "No valid cover art found"))
            }?:{
                MbResponse.Error(ErrorResponse(errorCode = -1, message = "Not Found covers"))
            }) as List<HttpResponse>

        }) {
            is MbResponse.Error -> {
                response
            }
            is MbResponse.Success -> {
                response.response.forEach{resp->
                    val images = resp.images
                    val coverArt = CoverArtUrls()
                    coverArt.front = when (side) {
                        COVER_ART_FRONT, COVER_ART_BOTH_SIDES ->
                            images.firstOrNull { it.front }?.getThumbnail(size.value)

                        else -> null
                    }
                    coverArt.back = when (side) {
                        COVER_ART_BACK, COVER_ART_BOTH_SIDES ->
                            images.firstOrNull { it.back }?.getThumbnail(size.value)

                        else -> null
                    }
                    coverArtUrls.add(coverArt)
                }
                return MbResponse.Success(coverArtUrls)
            }
        }
            }
        }
        return MbResponse.Error(ErrorResponse(-1,"Title or artist not is a correct"))
    }

    // Specific search function for recordings
        suspend fun searchRecording(
            query: String,
            limit: Int = 50,
            offset: Int = 0): MbResponse<RecordingResponse> {
            return processResponse(RecordingResponse::class) {
                client.get("recording") {
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
        return processResponse(ArtistResponse::class) {
            client.get("artist") {
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
        return processResponse(ReleaseResponse::class) {
            client.get("release") {
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
        return processResponse(ReleaseResponse::class) {
            client.get("release-group") {
                url {
                    parameters.append("query", query)
                    parameters.append("limit", limit.toString())
                    parameters.append("offset", offset.toString())
                }
            }
        }
    }
        suspend fun getReleaseById(query: String): MbResponse<ReleaseResponse> {
            return processResponse(ReleaseResponse::class) {
                client.get("release") {
                    url {
                        parameters.append("query", query)
                    }
                }
            }

        }

    suspend fun lookupReleaseById(id: String): MbResponse<Release> {
        return processResponse(Release::class) {
            client.get("release/$id")
        }

    }


}