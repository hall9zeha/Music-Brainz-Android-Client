package com.barryzeha.musicbrainzclient.data.repository

import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.data.model.entity.coverentity.Thumbnails
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtUrls
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.RecordingResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.ReleaseResponse

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 22/08/25.
 * Copyright (c)  All rights reserved.
 ***/

interface MbRepository {
    suspend fun searchRecording(
        query: String,
        limit: Int,
        offset: Int
    ): MbResponse<RecordingResponse>
    suspend fun searchReleaseById(id: String): MbResponse<ReleaseResponse>
    suspend  fun <T> searchEntity(
        entity: SearchEntity,
        query: String,
        limit: Int,
        offset: Int
    ): MbResponse<T>

    suspend fun fetchCoverArt(mbId: String): MbResponse<CoverArtResponse>
    suspend fun fetchCoverArtThumbnails(mbId:String): MbResponse<List<Thumbnails>>
    suspend fun fetchCovertArt(mbId: String, side:Int, size:Int):MbResponse<CoverArtUrls>
    suspend fun fetchCoverArtByTitleAndArtist(title: String,artist:String, side:Int, size:Int):MbResponse<CoverArtUrls>
}