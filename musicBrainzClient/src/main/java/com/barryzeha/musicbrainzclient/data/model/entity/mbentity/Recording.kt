package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class Recording(
    val id: String,
    val title: String,
    val length: Long? = null,
    val video: Boolean? = null,
    @SerialName("artist-credit") val artistCredit: List<ArtistCredit>? = null,
    @SerialName("first-release-date") val firstReleaseDate: String? = null,
    val releases: List<Release>? = null
)