package com.barryzeha.musicbrainzclient.data.remote

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.barryzeha.musicbrainzclient.common.COVER_ART_BACK
import com.barryzeha.musicbrainzclient.common.COVER_ART_BOTH_SIDES
import com.barryzeha.musicbrainzclient.common.COVER_ART_FRONT
import com.barryzeha.musicbrainzclient.common.LookupEntity
import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.common.SearchField
import com.barryzeha.musicbrainzclient.common.allTrim
import com.barryzeha.musicbrainzclient.common.getThumbnail
import com.barryzeha.musicbrainzclient.common.processResponse
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
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

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
    // Specific search function for cover art whit name of track
    // only get first match
    //TODO optimizar
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    suspend fun fetchCoverArtByTitleAndArtist(title:String, artist:String, side:Int, size:Int):MbResponse<CoverArtUrls>{
        var releaseId:String?=null
        val regex = Regex("[()]")
        var releaseIds: MutableList<String> = mutableListOf()
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
            val recordings = recordingResponse.body<RecordingResponse>().recordings.filter{recording->
                Log.e("FIND_ID_COVER", "${recording.title.allTrim().lowercase()} == ${title.lowercase().allTrim()}")
                val release = recording.releases?.firstOrNull {release->
                    //primero comprobamos que el artista sea el que estamos buscando
                    val artistCredit = release.artistCredit?.firstOrNull {artistCredit->artistCredit.name.lowercase().allTrim()==artist.lowercase().allTrim()  }
                    Log.e("FIND_ID_COVER", (artistCredit!=null).toString())
                    Log.e("FIND_ID_COVER", "${release.title?.allTrim()?.lowercase() }== ${title.allTrim().lowercase()}")
                    val media = release.media?.firstOrNull { media->
                        media.format?.trim()?.lowercase() == "Digital Media".trim().lowercase()
                    }
                    //if(artistCredit!=null) release.media?.allTrim()?.lowercase() == title.allTrim().lowercase()
                    if(media!=null) true
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
                Log.e("FIND_ID_COVER", releaseId.toString())
                var result: HttpResponse? = null
        return when (val response = processResponse<CoverArtResponse> {
            (releaseId?.let {
                while (releaseIds.isNotEmpty()) {
                    val resp = coverArtClient.get("release/${releaseIds.first()}")
                    if (resp.status.isSuccess()) {
                        result = resp
                        break
                    } else {
                        releaseIds.removeFirst()
                    }
                }
                result ?:MbResponse.Error(ErrorResponse(-1, "No valid cover art found"))
            }?:{
                MbResponse.Error(ErrorResponse(errorCode = -1, message = "Not Found covers"))
            }) as HttpResponse

        }) {
            is MbResponse.Error -> {
                response
            }
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
                return MbResponse.Success(coverArt)
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