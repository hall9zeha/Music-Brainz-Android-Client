package com.barryzeha.musicbrainzclient.data.model.entity.coverentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class Thumbnails(
    @SerialName("1200") val size1200: String? = null,
    @SerialName("500") val size500: String? = null,
    @SerialName("250") val size250: String? = null,
    val large: String? = null,
    val small: String? = null
)