package com.barryzeha.musicbrainzclient.common.utils

import androidx.annotation.Keep

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/
@Keep
class RecordingQueryBuilder {

    private val parts = mutableListOf<String>()

    fun arid(id: String) = apply { parts.add("arid:$id") }
    fun artist(name: String) = apply { parts.add("artist:\"$name\"") }
    fun artistName(name: String) = apply { parts.add("artistname:\"$name\"") }
    fun creditName(name: String) = apply { parts.add("creditname:\"$name\"") }
    fun comment(comment: String) = apply { parts.add("comment:\"$comment\"") }
    fun country(country: String) = apply { parts.add("country:$country") }
    fun date(date: String) = apply { parts.add("date:$date") }
    fun duration(ms: Int) = apply { parts.add("dur:$ms") }
    fun format(format: String) = apply { parts.add("format:\"$format\"") }
    fun isrc(code: String) = apply { parts.add("isrc:$code") }
    fun number(number: String) = apply { parts.add("number:$number") }
    fun position(pos: Int) = apply { parts.add("position:$pos") }
    fun primaryType(type: String) = apply { parts.add("primarytype:\"$type\"") }
    fun qdur(ms: Int) = apply { parts.add("qdur:$ms") }
    fun title(name: String) = apply { parts.add("recording:\"$name\"") }
    fun titleAccent(name: String) = apply { parts.add("recordingaccent:\"$name\"") }
    fun releaseId(id: String) = apply { parts.add("reid:$id") }
    fun release(name: String) = apply { parts.add("release:\"$name\"") }
    fun releaseGroupId(id: String) = apply { parts.add("rgid:$id") }
    fun recordingId(id: String) = apply { parts.add("rid:$id") }
    fun secondaryType(type: String) = apply { parts.add("secondarytype:\"$type\"") }
    fun status(status: String) = apply { parts.add("status:$status") }
    fun trackId(id: String) = apply { parts.add("tid:$id") }
    fun trackNumber(num: Int) = apply { parts.add("tnum:$num") }
    fun tracks(count: Int) = apply { parts.add("tracks:$count") }
    fun tracksRelease(count: Int) = apply { parts.add("tracksrelease:$count") }
    fun tag(tag: String) = apply { parts.add("tag:\"$tag\"") }
    fun type(type: String) = apply { parts.add("type:$type") }
    fun video(flag: Boolean) = apply { parts.add("video:$flag") }

    fun build(): String = parts.joinToString(" AND ")

}