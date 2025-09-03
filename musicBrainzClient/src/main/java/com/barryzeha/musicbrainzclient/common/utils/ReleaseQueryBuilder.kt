package com.barryzeha.musicbrainzclient.common.utils

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/

/*
* Generic class
* */
class ReleaseQueryBuilder {
    private val parts = mutableListOf<String>()

    fun artistId(id: String) = apply { parts.add("arid:$id") }
    fun artist(name: String) = apply { parts.add("artist:\"$name\"") }
    fun artistName(name: String) = apply { parts.add("artistname:\"$name\"") }
    fun asin(code: String) = apply { parts.add("asin:$code") }
    fun barcode(code: String) = apply { parts.add("barcode:$code") }
    fun catalogNumber(num: String) = apply { parts.add("catno:$num") }
    fun comment(comment: String) = apply { parts.add("comment:\"$comment\"") }
    fun country(country: String) = apply { parts.add("country:$country") }
    fun creditName(name: String) = apply { parts.add("creditname:\"$name\"") }
    fun date(date: String) = apply { parts.add("date:$date") }
    fun discIds(count: Int) = apply { parts.add("discids:$count") }
    fun format(format: String) = apply { parts.add("format:\"$format\"") }
    fun labelId(id: String) = apply { parts.add("laid:$id") }
    fun label(name: String) = apply { parts.add("label:\"$name\"") }
    fun language(lang: String) = apply { parts.add("lang:$lang") }
    fun mediums(count: Int) = apply { parts.add("mediums:$count") }
    fun primaryType(type: String) = apply { parts.add("primarytype:\"$type\"") }
    fun quality(quality: String) = apply { parts.add("quality:$quality") }
    fun releaseId(id: String) = apply { parts.add("reid:$id") }
    fun release(name: String) = apply { parts.add("release:\"$name\"") }
    fun releaseGroupId(id: String) = apply { parts.add("rgid:$id") }
    fun script(script: String) = apply { parts.add("script:$script") }
    fun secondaryType(type: String) = apply { parts.add("secondarytype:\"$type\"") }
    fun status(status: String) = apply { parts.add("status:$status") }
    fun tag(tag: String) = apply { parts.add("tag:\"$tag\"") }
    fun tracks(count: Int) = apply { parts.add("tracks:$count") }
    fun tracksMedium(count: Int) = apply { parts.add("tracksmedium:$count") }
    fun type(type: String) = apply { parts.add("type:$type") }

    fun build(): String = parts.joinToString(" AND ")
}
