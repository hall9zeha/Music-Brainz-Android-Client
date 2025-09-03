package com.barryzeha.musicbrainzclient.data.repository

import com.barryzeha.musicbrainzclient.common.CoverSize
import com.barryzeha.musicbrainzclient.common.LookupEntity
import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.data.model.entity.coverentity.Thumbnails
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtUrls
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.RecordingResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.ReleaseResponse
import com.barryzeha.musicbrainzclient.data.remote.MusicBrainzService
import kotlin.reflect.KClass

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
        offset: Int,
        ): MbResponse<RecordingResponse> {
        return musicBrainzService.searchRecording(query, limit, offset)
    }

    override suspend fun searchReleaseById(id: String): MbResponse<ReleaseResponse> {
        return musicBrainzService.getReleaseById(id)
    }



    override suspend  fun <T:Any> searchEntity(
        entity: SearchEntity,
        query: String,
        limit: Int,
        offset: Int,
        clazz: KClass<T>
    ): MbResponse<T> {
        return musicBrainzService.searchEntity(entity, query, limit, offset, clazz)
    }

    override suspend  fun<T:Any> lookupEntity(
        entity: LookupEntity,
        id: String,
        inc: String?,
        clazz: KClass<T>
    ): MbResponse<T> {
        return musicBrainzService.lookupEntity(entity, id, inc, clazz)
    }

    override suspend fun fetchCoverArt(mbId: String): MbResponse<CoverArtResponse> {
        return musicBrainzService.fetchCoverArt(mbId)
    }

    override suspend fun fetchCoverArtThumbnails(mbId: String): MbResponse<List<Thumbnails>> {
        return musicBrainzService.fetchCoverArtThumbnails(mbId)
    }

    override  suspend  fun  fetchCovertArt(
        mbId: String,
        side: Int,
        size: Int
    ): MbResponse<CoverArtUrls> {
        return musicBrainzService.fetchCoverArt(mbId,side,size)
    }

    override suspend fun fetchCoverArtByTitleAndArtist(
        title: String,
        artist:String,
        side: Int,
        size: CoverSize,
        firstOnly: Boolean
    ): MbResponse<List<CoverArtUrls>> {
        return musicBrainzService.fetchCoverArtByTitleAndArtist(title,artist,side,size,firstOnly)
    }
}