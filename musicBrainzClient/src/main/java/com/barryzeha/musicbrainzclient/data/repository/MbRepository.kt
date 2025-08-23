package com.barryzeha.musicbrainzclient.data.repository

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
}