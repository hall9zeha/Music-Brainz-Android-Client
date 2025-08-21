package com.barryzeha.musicbrainzclient.data.model.entity.coverentity

import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class CoverImage(
    val approved: Boolean,
    val back: Boolean,
    val comment: String,
    val edit: Long,
    val front: Boolean,
    val id: Long,
    val image: String,
    val thumbnails: Thumbnails,
    val types: List<String>
)