package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class Instrument(
    val id: String,
    val name: String,
    val description: String? = null,
    val type: String? = null,
    val disambiguation: String? = null
)