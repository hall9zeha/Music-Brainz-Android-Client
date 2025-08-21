package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class ReleaseGroup(
    val id: String,
    val title: String,
    @SerialName("primary-type") val primaryType: String? = null,
    @SerialName("secondary-types") val secondaryTypes: List<String>? = null
)