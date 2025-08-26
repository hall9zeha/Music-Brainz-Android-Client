package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class Genre(
    val id: String,
    val name: String,
    val disambiguation: String? = null,
    val count: Int? = null
)