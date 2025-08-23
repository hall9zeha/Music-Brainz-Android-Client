package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class Release(
    val id: String?,
    val title: String?,
    val status: String? = null,
    val date: String? = null,
    val country: String? = null,
    @SerialName("release-group") val releaseGroup: ReleaseGroup? = null,
    @SerialName("track-count") val trackCount: Int? = null,
    val media: List<Media>? = null
)