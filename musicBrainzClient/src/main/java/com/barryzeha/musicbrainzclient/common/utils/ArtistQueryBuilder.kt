package com.barryzeha.musicbrainzclient.common.utils

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/

class ArtistQueryBuilder {
    private val parts = mutableListOf<String>()

    fun id(id: String) = apply { parts.add("arid:$id") }
    fun name(name: String) = apply { parts.add("artist:\"$name\"") }
    fun nameAccent(name: String) = apply { parts.add("artistaccent:\"$name\"") }
    fun alias(alias: String) = apply { parts.add("alias:\"$alias\"") }
    fun area(area: String) = apply { parts.add("area:\"$area\"") }
    fun begin(date: String) = apply { parts.add("begin:$date") }
    fun beginArea(area: String) = apply { parts.add("beginarea:\"$area\"") }
    fun comment(comment: String) = apply { parts.add("comment:\"$comment\"") }
    fun country(code: String) = apply { parts.add("country:$code") }
    fun end(date: String) = apply { parts.add("end:$date") }
    fun endArea(area: String) = apply { parts.add("endarea:\"$area\"") }
    fun gender(gender: String) = apply { parts.add("gender:$gender") }
    fun ipi(ipi: String) = apply { parts.add("ipi:$ipi") }
    fun isni(isni: String) = apply { parts.add("isni:$isni") }
    fun sortName(name: String) = apply { parts.add("sortname:\"$name\"") }
    fun tag(tag: String) = apply { parts.add("tag:\"$tag\"") }
    fun type(type: String) = apply { parts.add("type:$type") }

    fun build(): String = parts.joinToString(" AND ")
}
