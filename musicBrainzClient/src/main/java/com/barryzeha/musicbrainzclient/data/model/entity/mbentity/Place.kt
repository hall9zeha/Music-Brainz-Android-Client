package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/
@Serializable
data class Place(
    val id: String,
    val name: String,
    val type: String? = null,
    val address: String? = null,
    val area: Area? = null,
    val disambiguation: String? = null
)