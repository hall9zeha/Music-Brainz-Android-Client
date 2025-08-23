package com.barryzeha.musicbrainzclient.data.repository

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
    private val musicBrainzService by lazy { MusicBrainzService(appName,appVersion,contact) }
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
}