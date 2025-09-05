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
import kotlin.reflect.KClass

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 22/08/25.
 * Copyright (c)  All rights reserved.
 ***/

interface MbRepository {
    suspend fun  searchRecording(
        query: String,
        limit: Int,
        offset: Int
    ): MbResponse<RecordingResponse>
    suspend fun searchReleaseById(id: String): MbResponse<ReleaseResponse>
    suspend  fun <T:Any> searchEntity(
        entity: SearchEntity,
        query: String,
        limit: Int,
        offset: Int,
        clazz:KClass<T>
    ): MbResponse<T>
    suspend  fun<T: Any> lookupEntity(entity: LookupEntity,mbId: String,inc: String?=null,clazz: KClass<T>): MbResponse<T>
    suspend fun fetchCoverArt(mbId: String): MbResponse<CoverArtResponse>
    suspend fun fetchCoverArtByReleaseGroup(mbId:String): MbResponse<CoverArtResponse>
    suspend fun fetchCoverArtThumbnails(mbId:String): MbResponse<List<Thumbnails>>
    suspend fun fetchCovertArt(mbId: String, side:Int, size: CoverSize):MbResponse<CoverArtUrls>
    suspend fun fetchCoverArtByTitleAndArtist(title: String, artist:String, side:Int, size: CoverSize, firstOnly:Boolean):MbResponse<List<CoverArtUrls>>
}