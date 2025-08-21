package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

import kotlinx.serialization.Serializable

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
data class ArtistCredit(
    val name: String,
    val joinphrase: String? = null,
    val artist: Artist
)