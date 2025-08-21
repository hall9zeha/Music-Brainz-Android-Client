package com.barryzeha.musicbrainzclient.data.model.entity.response

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Recording
import kotlinx.serialization.Serializable

@Serializable
data class MusicBrainzResponse(
    val created: String,
    val count: Int=0,
    val offset: Int=0,
    val recordings: List<Recording>?
)
