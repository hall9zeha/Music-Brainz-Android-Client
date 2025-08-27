package com.barryzeha.musicbrainzclient.data.model.entity.mbentity

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/
import kotlinx.serialization.Serializable

@Serializable
data class Annotation(
    val type:String?,
    val score:String?,
    val entity:String?,
    val name:String?,
    val text:String?

)
@Serializable
data class CdStub(
    val id:String?,
    val score:String?,
    val count:String?,
    val title:String?,
    val artist:String?,
    val barcode:String?,
    val comment:String?
)
@Serializable
data class Series(
    val id: String,
    val name: String,
    val disambiguation: String? = null,
    val type: String? = null,
    val typeId: String? = null,
    val orderingType: String? = null,
    val annotation: String? = null,
    val tags: List<Tag>? = null,
    val aliases: List<Alias>? = null,
    val relations: List<Relation>? = null
)
@Serializable
data class Serie(
    val id:String?,
    val type:String?,
    val score: String?,
    val name:String?,
    val disambiguation:String?
)

@Serializable
data class Work(
    val id: String,
    val title: String,
    val disambiguation: String? = null,
    val type: String? = null,
    val typeId: String? = null,
    val language: String? = null,
    val languages: List<String>? = null,
    val iswcs: List<String>? = null,
    val attributes: List<WorkAttribute>? = null,
    val relations: List<Relation>? = null,
    val aliases: List<Alias>? = null,
    val tags: List<Tag>? = null
)

@Serializable
data class WorkAttribute(
    val type: String? = null,
    val typeId: String? = null,
    val value: String? = null
)

@Serializable
data class Url(
    val id: String,
    val resource: String,
    val relationType: String? = null,
    val relations: List<Relation>? = null
)

@Serializable
data class Tag(
    val name: String,
    val count: Int? = null
)

@Serializable
data class Alias(
    val name: String,
    val sortName: String? = null,
    val locale: String? = null,
    val type: String? = null,
    val primary: Boolean? = null,
    val beginDate: String? = null,
    val endDate: String? = null
)

@Serializable
data class Relation(
    val type: String,
    val typeId: String? = null,
    val direction: String? = null,
    val targetType: String? = null,
    val url: Url? = null,
    val artist: Artist? = null,
    val release: Release? = null,
    val recording: Recording? = null,
    val work: Work? = null,
    val series: Series? = null
)
