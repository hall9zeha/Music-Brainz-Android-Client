package com.barryzeha.musicbrainzclient.data.repository

import com.barryzeha.musicbrainzclient.common.LookupEntity
import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.RecordingResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.ReleaseResponse
import com.barryzeha.musicbrainzclient.data.remote.MusicBrainzService

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 22/08/25.
 * Copyright (c)  All rights reserved.
 ***/

class MbRepositoryImpl(private val appName:String?=null,private val appVersion:String?=null, private val contact:String?=null ): MbRepository {
    val musicBrainzService by lazy { MusicBrainzService(appName,appVersion,contact) }
    override suspend fun searchRecording(
        query: String,
        limit: Int,
        offset: Int
    ): MbResponse<RecordingResponse> {
        return musicBrainzService.searchRecording(query, limit, offset)
    }

    override suspend fun searchReleaseById(id: String): MbResponse<ReleaseResponse> {
        return musicBrainzService.getReleaseById(id)
    }


    override suspend  fun <T> searchEntity(
        entity: SearchEntity,
        query: String,
        limit: Int,
        offset: Int
    ): MbResponse<T> {
        return genericSearchEntity(entity,query,limit,offset)
    }
    suspend inline fun <reified T : Any> genericSearchEntity(
        entity: SearchEntity,
        query: String,
        limit: Int = 50,
        offset: Int = 0
    ): MbResponse<T> {
        return musicBrainzService.searchEntity(entity, query, limit, offset)
    }
    suspend inline fun<reified T:Any> genericLookupEntity(
        entity: LookupEntity,
        id: String,
        inc: String?
    ): MbResponse<T> {
        return musicBrainzService.lookupEntity(entity, id, inc)
    }

    override suspend fun fetchCoverArt(mbId: String): MbResponse<CoverArtResponse> {
        return musicBrainzService.fetchCoverArt(mbId)
    }
}