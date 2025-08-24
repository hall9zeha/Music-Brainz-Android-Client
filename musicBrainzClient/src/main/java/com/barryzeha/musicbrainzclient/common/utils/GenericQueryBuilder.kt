package com.barryzeha.musicbrainzclient.common.utils

import com.barryzeha.musicbrainzclient.common.QueryField

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/

class GenericQueryBuilder {
    private val parts = mutableListOf<String>()
    fun field(field: QueryField, value: String) = apply {
        val escaped = value.replace("\"", "\\\"")
        parts.add("${field.key}:\"$escaped\"")
    }
    fun build(): String = parts.joinToString(" AND ")
}