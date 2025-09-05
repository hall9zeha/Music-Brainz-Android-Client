package com.barryzeha.musicbrainzclient.common

import androidx.annotation.Keep

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 24/08/25.
 * Copyright (c)  All rights reserved.
 ***/
interface QueryField{ val key:String}

/**
 * Enum class that defines all supported query fields for MusicBrainz search endpoints.
 *
 * Each constant maps to the corresponding field name expected by the MusicBrainz WS/2 API.
 * These fields can be combined when building a query using [GenericQueryBuilder].
 *
 * Example usage:
 * ```
 * val query = GenericQueryBuilder()
 *     .field(SearchField.ARTIST, "Daft Punk")
 *     .field(SearchField.RELEASE, "Discovery")
 *     .build()
 * // Produces: artist:"Daft Punk" AND release:"Discovery"
 * ```
 */
@Keep
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