package com.barryzeha.musicbrainzclient.data.model.entity.response

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 21/08/25.
 * Copyright (c)  All rights reserved.
 ***/

import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Alias
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Annotation
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Area
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Artist
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.ArtistCredit
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.CdStub
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Event
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Genre
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Instrument
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Label
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.LifeSpan
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Place
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Recording
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Relation
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Release
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.ReleaseGroup
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Serie
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Series
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Tag
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Track
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Url
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Work
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Search Responses
@Serializable
data class AnnotationResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val annotations: List<Annotation> = emptyList()
)
@Serializable
data class AreaResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val areas: List<Area> = emptyList()
)
@Serializable
data class CdStubsResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val cdstubs: List<CdStub> = emptyList()
)
@Serializable
data class EventResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val events: List<Event> = emptyList()
)
@Serializable
data class InstrumentResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val instruments: List<Instrument> = emptyList()
)
@Serializable
data class LabelResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val labels: List<Label> = emptyList()
)

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
data class SerieResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val series: List<Serie> = emptyList()
)
@Serializable
data class TagResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val tags: List<Tag> = emptyList()
)
@Serializable
data class UrlResponse(
    val created: String,
    val count: Int = 0,
    val offset: Int = 0,
    val urls: List<Url> = emptyList()
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

@Serializable
data class AreaLookupResponse(
    val id: String,
    val name: String,
    val type: String? = null,
    val disambiguation: String? = null,
    val iso31661Codes: List<String> = emptyList(),
    val iso31662Codes: List<String> = emptyList(),
    val iso31663Codes: List<String> = emptyList(),
    val lifeSpan: LifeSpan? = null
): LookupIncludes()

@Serializable
data class ArtistLookupResponse(
    val id: String,
    val name: String,
    val sortName: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val country: String? = null,
    val disambiguation: String? = null,
    val lifeSpan: LifeSpan? = null
): LookupIncludes()

@Serializable
data class EventLookupResponse(
    val id: String,
    val name: String,
    val type: String? = null,
    val time: String? = null,
    val cancelled: Boolean? = null,
    val disambiguation: String? = null,
    val lifeSpan: LifeSpan? = null
): LookupIncludes()

@Serializable
data class GenreLookupResponse(
    val id: String,
    val name: String,
    val disambiguation: String? = null
): LookupIncludes()

@Serializable
data class InstrumentLookupResponse(
    val id: String,
    val name: String,
    val type: String? = null,
    val description: String? = null,
    val disambiguation: String? = null,
): LookupIncludes()

@Serializable
data class LabelLookupResponse(
    val id: String,
    val name: String,
    val type: String? = null,
    val country: String? = null,
    val disambiguation: String? = null,
    val lifeSpan: LifeSpan? = null,
    val labelCode: Int? = null,
): LookupIncludes()

@Serializable
data class PlaceLookupResponse(
    val id: String,
    val name: String,
    val type: String? = null,
    val address: String? = null,
    val coordinates: Coordinates? = null,
    val area: Area? = null,
    val disambiguation: String? = null,
    val lifeSpan: LifeSpan? = null
): LookupIncludes()

@Serializable
data class RecordingLookupResponse(
    val id: String,
    val title: String,
    val length: Int? = null,
    val disambiguation: String? = null,
    val video: Boolean? = null,
    @SerialName("artist-credit")
    val artistCredits: List<ArtistCredit> = emptyList(),
): LookupIncludes()

@Serializable
data class ReleaseLookupResponse(
    val id: String,
    val title: String,
    val status: String? = null,
    val date: String? = null,
    val country: String? = null,
    val disambiguation: String? = null,
    val releaseGroup: ReleaseGroup? = null,
    val trackCount: Int? = null,
    val media: List<Medium> = emptyList(),
    val labelInfo: List<LabelInfo> = emptyList()
): LookupIncludes()

@Serializable
data class ReleaseGroupLookupResponse(
    val id: String,
    val title: String,
    val primaryType: String? = null,
    val secondaryTypes: List<String> = emptyList(),
    val firstReleaseDate: String? = null,
    val artistCredits: List<ArtistCredit> = emptyList(),
): LookupIncludes()

@Serializable
data class UrlLookupResponse(
    val id: String,
    val resource: String,
    val relationList: List<Relation> = emptyList()
): LookupIncludes()

@Serializable
data class WorkLookupResponse(
    val id: String,
    val title: String,
    val type: String? = null,
    val disambiguation: String? = null,
    val languages: List<String> = emptyList(),
    val iswcs: List<String> = emptyList(),
    @SerialName("artist-credit")
    val artistCredits: List<ArtistCredit> = emptyList()
): LookupIncludes()
@Serializable
data class Medium(
    val position: Int? = null,
    val format: String? = null,
    val trackCount: Int? = null,
    val title: String? = null,
    val discs: List<Disc> = emptyList(),
    val tracks: List<Track> = emptyList()
): LookupIncludes()
@Serializable
data class Disc(
    val id: String,
    val sectors: Int? = null,
    val offsetCount: Int? = null
): LookupIncludes()
@Serializable
data class Coordinates(
    val latitude: Double? = null,
    val longitude: Double? = null
): LookupIncludes()
@Serializable
data class LabelInfo(
    val catalogNumber: String? = null,
    val label: Label? = null
): LookupIncludes()

// Include support
@Serializable
open class LookupIncludes(
    val recordings: List<Recording>? = null,
    val releases: List<Release>? = null,
    val releaseGroups: List<ReleaseGroup>? = null,
    val works: List<Work>? = null,
    val artists: List<Artist>? = null,
    val labels: List<Label>? = null,
    val aliases: List<Alias>? = null,
    val tags: List<Tag>? = null,
    val relations: List<Relation>? = null,
    val userGenres: List<Genre>? = null,
    val userTags: List<Tag>? = null,
    val genres: List<Genre>? = null

)