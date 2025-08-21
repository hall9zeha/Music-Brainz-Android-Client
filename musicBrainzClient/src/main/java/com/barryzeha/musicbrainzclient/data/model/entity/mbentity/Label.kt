package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class Label(
    val id: String,
    val name: String,
    @SerialName("sort-name") val sortName: String? = null,
    val country: String? = null,
    val disambiguation: String? = null,
    @SerialName("life-span") val lifeSpan: LifeSpan? = null
)