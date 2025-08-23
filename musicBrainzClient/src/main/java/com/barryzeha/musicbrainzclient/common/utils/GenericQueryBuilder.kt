package com.barryzeha.musicbrainzclient.common.utils

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/

class GenericQueryBuilder {
    private val parts = mutableListOf<String>()
    fun field(field: String, value: String)= apply {
        parts.add("$field:\"$value\"")
    }
    fun build(): String = parts.joinToString(" AND ")
}