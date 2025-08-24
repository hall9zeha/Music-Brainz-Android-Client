package com.barryzeha.musicbrainzclient.common

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 24/08/25.
 * Copyright (c)  All rights reserved.
 ***/
interface QueryField{ val key:String}
enum class SearchField(override val key: String): QueryField {
    // ---- Comunes ----
    ALIAS("alias"),
    TAG("tag"),
    COMMENT("comment"),

    // ---- Artist ----
    ARTIST("artist"),
    ARTIST_NAME("artistname"),
    ARID("arid"),
    SORTNAME("sortname"),

    // ---- Recording ----
    RECORDING("recording"),
    RECORDING_ACCENT("recordingaccent"),
    ISRC("isrc"),
    RID("rid"),
    REID("reid"),
    RGID("rgid"),
    DURATION("dur"),
    QDUR("qdur"),
    VIDEO("video"),
    CREDIT_NAME("creditname"),
    TID("tid"),

    // ---- Release ----
    RELEASE("release"),
    RELEASE_GROUP("releasegroup"),
    COUNTRY("country"),
    FORMAT("format"),
    STATUS("status"),
    PRIMARY_TYPE("primarytype"),
    SECONDARY_TYPE("secondarytype"),
    DATE("date"),
    FIRST_RELEASE_DATE("firstreleasedate"),
    LABEL("label"),
    CATALOG_NUMBER("catno"),
    BARCODE("barcode"),
    LANGUAGE("lang"),

    // ---- Work ----
    WORK("work"),
    ISWC("iswc"),
    TYPE("type"),

    // ---- Series ----
    SERIES("series"),

    // ---- URL ----
    URL("url"),

    // ---- Others ----
    PLACE("place"),
    EVENT("event"),
    INSTRUMENT("instrument"),
    GENRE("genre");

}