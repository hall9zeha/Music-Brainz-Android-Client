package com.barryzeha.musicbrainzclient.common

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 26/08/25.
 * Copyright (c)  All rights reserved.
 ***/

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