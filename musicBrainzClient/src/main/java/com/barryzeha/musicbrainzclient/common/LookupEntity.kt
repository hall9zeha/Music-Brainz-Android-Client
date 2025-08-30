package com.barryzeha.musicbrainzclient.common

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 24/08/25.
 * Copyright (c)  All rights reserved.
 ***/

enum class LookupEntity(val path: String) {
    AREA("area"),
    ARTIST("artist"),
    GENRE("genre"),
    EVENT("event"),
    INSTRUMENT("instrument"),
    LABEL("label"),
    PLACE("place"),
    RECORDING("recording"),
    RELEASE("release"),
    RELEASE_GROUP("release-group"),
    RATING("rating"),
    WORK("work"),
    URL("url")
}