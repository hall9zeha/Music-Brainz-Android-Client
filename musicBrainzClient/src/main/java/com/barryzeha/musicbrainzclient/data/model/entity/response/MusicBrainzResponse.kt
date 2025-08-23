package com.barryzeha.musicbrainzclient.data.model.entity.response

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Artist
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Place
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Recording
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Release
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.ReleaseGroup
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Work
import kotlinx.serialization.Serializable

// Search Responses
@Serializable
data class RecordingResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val recordings: List<Recording> = emptyList()
)

@Serializable
data class ReleaseResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val releases: List<Release> = emptyList()
)

@Serializable
data class ArtistResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val artists: List<Artist> = emptyList()
)

@Serializable
data class ReleaseGroupResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val releaseGroups: List<ReleaseGroup> = emptyList()
)

@Serializable
data class WorkResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val works: List<Work> = emptyList()
)

@Serializable
data class PlaceResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val places: List<Place> = emptyList()
)
// Lookup Responses

