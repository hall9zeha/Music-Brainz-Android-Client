package com.barryzeha.musicbrainzclient.common

import androidx.annotation.Keep

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 24/08/25.
 * Copyright (c)  All rights reserved.
 ***/
@Keep
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