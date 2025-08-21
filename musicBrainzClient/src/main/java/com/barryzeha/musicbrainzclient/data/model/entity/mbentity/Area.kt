package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class Area(
    val id: String,
    val name: String,
    @SerialName("sort-name") val sortName: String? = null,
    @SerialName("iso-3166-1-codes") val isoCodes: List<String>? = null,
    val disambiguation: String? = null
)