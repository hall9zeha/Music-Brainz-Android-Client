package com.barryzeha.musicbrainzclient.data.model.entity.response

import com.barryzeha.musicbrainzclient.data.model.entity.coverentity.CoverImage
import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class CoverArtResponse(
    val images: List<CoverImage>,
    val release: String
)