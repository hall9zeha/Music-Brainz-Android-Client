package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class Media(
    val id: String? = null,
    val format: String? = null,
    val position: Int? = null,
    @SerialName("track-count") val trackCount: Int? = null,
    val track: List<Track>? = null
)