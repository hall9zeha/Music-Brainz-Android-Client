package com.barryzeha.musicbrainzclient.data.repository

import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Release
import com.barryzeha.musicbrainzclient.data.model.entity.response.MusicBrainzResponse
import com.barryzeha.musicbrainzclient.data.remote.MusicBrainzService

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 22/08/25.
 * Copyright (c)  All rights reserved.
 ***/

class MbRepositoryImpl(): MbRepository {
    private val musicBrainzService by lazy { MusicBrainzService() }
    override suspend fun searchRecording(
        query: String,
        limit: Int,
        offset: Int
    ): MusicBrainzResponse {
        return musicBrainzService.searchRecording(query, limit, offset)
    }

    override suspend fun getReleaseById(id: String): Release {
        return musicBrainzService.getReleaseById(id)
    }
}