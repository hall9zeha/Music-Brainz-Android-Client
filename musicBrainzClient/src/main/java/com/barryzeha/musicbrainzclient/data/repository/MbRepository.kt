package com.barryzeha.musicbrainzclient.data.repository

import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Release
import com.barryzeha.musicbrainzclient.data.model.entity.response.MusicBrainzResponse

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
    ): MusicBrainzResponse
    suspend fun getReleaseById(id: String): Release
}