package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class Event(
    val id: String,
    val name: String,
    val type: String? = null,
    @SerialName("life-span") val lifeSpan: LifeSpan? = null,
    val time: String? = null,
    val cancelled: Boolean? = null
)