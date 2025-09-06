package com.barryzeha.musicbrainzclient

import androidx.annotation.Keep
import com.barryzeha.musicbrainzclient.common.COVER_ART_FRONT
import com.barryzeha.musicbrainzclient.common.CoverSize
import com.barryzeha.musicbrainzclient.common.LookupEntity
import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.data.model.entity.coverentity.Thumbnails
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtUrls
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.RecordingResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.ReleaseResponse
import com.barryzeha.musicbrainzclient.data.repository.MbRepository
import com.barryzeha.musicbrainzclient.data.repository.MbRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 22/08/25.
 * Copyright (c)  All rights reserved.
 ***/

/**
 * @param appName Name of the application using the MusicBrainz API. Recommended to identify your app
 *                and take advantage of benefits such as higher request limits.
 * @param appVersion Version of the application. Helps MusicBrainz manage access and provide support.
 * @param contact Contact information (email or URL). Allows MusicBrainz to reach you in case of issues
 *                or for support purposes.
 *
 * It is recommended to provide these arguments in order to use the MusicBrainz API with all its benefits
 * and to avoid restrictions.
 */

@Keep
class MusicBrainzClient(private val appName:String?=null,private val appVersion:String?=null, private val contact:String?=null) {
    @PublishedApi
    internal val mainScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    @PublishedApi
    internal val repository: MbRepository  by lazy { MbRepositoryImpl(appName,appVersion,contact) }

    /**
     * A Specific request from Recording
     * @param query The search query string to find recordings.
     * @param limit The maximum number of results to return. Defines how many results will be fetched per query.
     * @param offset The offset from which to start fetching results, useful for paginated queries.
     * @param callback Function to handle the response, which contains either a [RecordingResponse] object
     *                 with the results or an error wrapped in [MbResponse].
     */
    fun searchRecording(
        query: String,
        limit: Int,
        offset: Int,
        callback:(MbResponse<RecordingResponse>)-> Unit

    ){ mainScope.launch {
            val response= repository.searchRecording(query, limit, offset)
            callback(response)
        }
    }
    // Generic requests for all entities with  search mode support
    // Search mode not support include fields
    /**
     * @param entity The type of entity to search for, such as [SearchEntity.RECORDING], [SearchEntity.ARTIST], etc.
     * @param query The search query string to search within the specified entity.
     * @param limit The maximum number of results to return. Defines how many results will be fetched per query.
     * @param offset The offset from which to start fetching results, useful for paginated queries.
     * @param callback Function to handle the response, which contains either the results in a specific type [T] (such as [RecordingResponse], [ArtistResponse], etc.)
     *                 or an error wrapped in [MbResponse].
     */
    inline fun <reified T: Any> searchEntity(
        entity: SearchEntity,
        query: String,
        limit: Int,
        offset: Int,
        crossinline callback:(MbResponse<T>)-> Unit

    ){ mainScope.launch {
            val response= repository.searchEntity<T>(entity, query, limit, offset, T::class)
            callback(response)
        }
    }
    // Generic lookup for all entities with  lookup mode support
    /**
     * @param entity The type of entity to look up, such as [LookupEntity.RECORDING], [LookupEntity.ARTIST], etc.
     * @param mbId The MusicBrainz ID (MBID) of the entity to look up.
     * @param inc Additional fields to include in the response, such as relationships (e.g., artist credits, aliases). Can be null if no additional data is needed.
     * @param callback Function to handle the response, which contains either the looked-up entity in the specified type [T] (such as [RecordingLookupResponse], [ArtistLookupResponse], etc.)
     *                 or an error wrapped in [MbResponse].
     */
    inline fun <reified T:Any> lookupEntity(
        entity:LookupEntity,
        mbId: String,
        inc: String?,
        crossinline callback:(MbResponse<T>)-> Unit

    ){ mainScope.launch {
            val response= repository.lookupEntity<T>(entity, mbId, inc,T::class)
            callback(response)
        }
    }
    /**
     * @param mbId Release MBID used for the search.
     * @param callback Function to handle the response, which contains either a complete object of [CoverArtResponse]
     *                 or an error wrapped in [MbResponse].
     */
    fun fetchCoverArt(
        mbId: String,
        callback:(MbResponse<CoverArtResponse>)-> Unit

    ){ mainScope.launch {
            val response= repository.fetchCoverArt(mbId)
            callback(response)
        }
    }
    /**
     * @param mbId Release MBID used for the search.
     * @param callback Function to handle the response, which contains either a complete object of [CoverArtResponse]
     *                 or an error wrapped in [MbResponse].
     */
    fun fetchCoverArtByReleaseGroup(
        mbId: String,
        callback:(MbResponse<CoverArtResponse>)->Unit
    ){
        mainScope.launch {
            val response=repository.fetchCoverArtByReleaseGroup(mbId)
            callback(response)
        }
    }
    /**
     * @param mbId Release MBID used for the search.
     * @param callback Function to handle the response, which contains either a list of Urls
     *                 or an error wrapped in [MbResponse].
     */
    fun fetchCoverArtThumbnail(
        mbId: String,
        callback:(MbResponse<List<Thumbnails>>)-> Unit
    ){
        mainScope.launch {
            val response = repository.fetchCoverArtThumbnails(mbId)
            callback(response)
        }
    }
    /**
    * @param mbId Release MBID used for the search.
    * @param side Which cover art side(s) to fetch (front, back, or both). Defaults to [COVER_ART_FRONT].
    * @param size Size of the cover art image. Defaults to 250px. Available sizes include [CoverSize.S_250],[CoverSize.S_500],[CoverSize.S_1200].
    * @param callback Function to handle the response, which contains a object [CoverArtUrls] with urls to front or back image
    *                 or an error wrapped in [MbResponse].
    */
    fun fetchCoverArtSide(
        mbId:String,
        side:Int= COVER_ART_FRONT,
        size: CoverSize= CoverSize.S_250,
        callback:(MbResponse<CoverArtUrls>)->Unit
    ){
        mainScope.launch {
            val response = repository.fetchCovertArt(mbId,side,size)
            callback(response)
        }
    }

    /**
     * Fetches cover art images by matching a release title and artist name.
     *
     * By default, only the **front** cover art of the first matching release is returned
     * at a size of **250px**. The behavior can be customized with the parameters:
     *
     * - `side`: Defines which side(s) of the cover art to fetch.
     *   Options are:
     *   - [COVER_ART_FRONT] = 1 → front cover (default)
     *   - [COVER_ART_BACK] = 2 → back cover
     *   - [COVER_ART_BOTH_SIDES] = 3 → both front and back
     *
     * - `size`: Size of the cover art image.
     *   Supported values: **250 (default)**, 500, 1200, `small`, `large`.
     *
     * - `firstOnly`: If `true` (default), only the first matching cover art is returned.
     *   If `false`, all matching images for the given title and artist are returned, but the
     *   request will be slower.
     *
     * @param title Release title used for the search.
     * @param artist Artist name used for the search.
     * @param side Which cover art side(s) to fetch (front, back, or both). Defaults to [COVER_ART_FRONT].
     * @param size Size of the cover art image. Defaults to 250px.
     * @param firstOnly If `true`, returns only the first matching cover art (faster). Defaults to `true`.
     * @param callback Function to handle the response, which contains either a list of [CoverArtUrls]
     *                 or an error wrapped in [MbResponse].
     */
    fun fetchCoverArtByTitleAndArtist(
        title:String,
        artist:String,
        side:Int= COVER_ART_FRONT,
        size: CoverSize= CoverSize.S_250,
        firstOnly:Boolean = true,
        callback:(MbResponse<List<CoverArtUrls>>)->Unit
    ){
        mainScope.launch {
            val response = repository.fetchCoverArtByTitleAndArtist(title,artist,side,size, firstOnly)
            callback(response)
        }
    }
    /**
    * @param mbId Release MBID used for the search.
    */
    fun getReleaseById(
        mbId: String,
        callback:(MbResponse<ReleaseResponse>)-> Unit

    ){ mainScope.launch {
            val response= repository.searchReleaseById(mbId)
            callback(response)
        }
    }
}