package com.barryzeha.musicbrainzclient.common.utils

import androidx.annotation.Keep
import com.barryzeha.musicbrainzclient.common.IncludeField

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 26/08/25.
 * Copyright (c)  All rights reserved.
 ***/

/**
 * Builder class for constructing the `inc` parameter for MusicBrainz lookups.
 *
 * This class provides convenience methods for including specific fields in the query.
 * Internally, it accumulates the selected fields and joins them with `+` as required
 * by the MusicBrainz WS/2 API.
 *
 * Example usage:
 * ```
 * val inc = GenericIncludeBuilder()
 *     .incAliases()
 *     .incTags()
 *     .incRecordings()
 *     .build()
 * // Produces: aliases+tags+recordings
 * ```
 */
@Keep
class GenericIncludeBuilder{

    private val parts = mutableListOf<String>()
    fun incAliases() = apply { parts.add(IncludeField.ALIASES.path) }
    fun incArtistCredits() = apply { parts.add(IncludeField.ARTIST_CREDITS.path) }
    fun incLabels() = apply { parts.add(IncludeField.LABELS.path) }
    fun incRecordings() = apply { parts.add(IncludeField.RECORDINGS.path)}
    fun incReleaseGroups() = apply { parts.add(IncludeField.RELEASE_GROUPS.path) }
    fun incMedia() = apply { parts.add(IncludeField.MEDIA.path) }
    fun incDiscIds() = apply { parts.add(IncludeField.DISCIDS.path) }
    fun incIsrcs() = apply { parts.add(IncludeField.ISRCs.path) }
    fun incUserTags() = apply { parts.add(IncludeField.USER_TAGS.path) }
    fun incUserRatings() = apply { parts.add(IncludeField.USER_RATINGS.path) }
    fun incUserGenres() = apply { parts.add(IncludeField.USER_GENRES.path) }
    fun incGenres() = apply { parts.add(IncludeField.GENRES.path) }
    fun incTags() = apply { parts.add(IncludeField.TAGS.path) }
    fun incWorks() = apply { parts.add(IncludeField.WORKS.path) }
    fun build(): String = parts.joinToString("+")
}