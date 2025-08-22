package com.barryzeha.musicbrainzclient

import com.barryzeha.musicbrainzclient.data.model.entity.response.MusicBrainzResponse
import com.barryzeha.musicbrainzclient.data.repository.MbRepository
import com.barryzeha.musicbrainzclient.data.repository.MbRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 22/08/25.
 * Copyright (c)  All rights reserved.
 ***/

class MusicBrainzClient{
    private val mainScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private val repository: MbRepository  by lazy { MbRepositoryImpl() }

    fun serchRecording(
        query: String,
        limit: Int,
        offset: Int,
        callback:(MusicBrainzResponse)-> Unit

    ){ mainScope.launch {
            val response= repository.searchRecording(query, limit, offset)
            callback(response)
        }
    }
}