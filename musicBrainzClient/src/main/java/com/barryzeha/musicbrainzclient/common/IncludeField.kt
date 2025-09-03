package com.barryzeha.musicbrainzclient.common

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 26/08/25.
 * Copyright (c)  All rights reserved.
 ***/

/**
 * Enum class that defines the available `inc` parameters for MusicBrainz lookups.
 *
 * These values are used to extend the amount of data returned by the MusicBrainz WS/2 API.
 * They correspond to the `inc` query parameter, which accepts a `+` separated list.
 *
 * Example usage:
 * ```
 * // Lookup an artist including aliases and tags:
 * /ws/2/artist/{mbid}?inc=aliases+tags
 * ```
 */
enum class IncludeField(val path:String) {
    ALIASES("aliases"),
    ARTIST_CREDITS("artist-credits"),
    LABELS("labels"),
    RECORDINGS("recordings"),
    RELEASE_GROUPS("release-groups"),
    MEDIA("media"),
    DISCIDS("discids"),
    ISRCs("isrcs"),
    USER_TAGS("user-tags"),
    USER_RATINGS("user-ratings"),
    USER_GENRES("user-genres"),
    USER_REATINGS("user-ratings"),
    GENRES("genres"),
    TAGS("tags"),
    WORKS("works"),


}