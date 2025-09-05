package com.barryzeha.musicbrainzclient.common.utils

import androidx.annotation.Keep
import com.barryzeha.musicbrainzclient.common.QueryField

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/

/**
 * Builder class for constructing search queries for MusicBrainz endpoints.
 *
 * This class allows you to progressively add fields and values that will be joined
 * using the `AND` operator. Values are automatically quoted and escaped.
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
class GenericQueryBuilder {
    private val parts = mutableListOf<String>()
    fun field(field: QueryField, value: String) = apply {
        val escaped = value.replace("\"", "\\\"")
        parts.add("${field.key}:\"$escaped\"")
    }
    fun build(): String = parts.joinToString(" AND ")
}