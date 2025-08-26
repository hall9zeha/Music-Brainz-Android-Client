package com.barryzeha.musicbrainzclient

import com.barryzeha.musicbrainzclient.common.LookupEntity
import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.data.model.entity.response.CoverArtResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.RecordingResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.ReleaseResponse
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
 * @param appName Nombre de la aplicación que utiliza la API de MusicBrainz. Recomendado para identificar tu app y aprovechar ventajas como mayor límite de peticiones.
 * @param appVersion Versión de la aplicación. Ayuda a MusicBrainz a gestionar el acceso y soporte.
 * @param contact Información de contacto (email o URL). Permite a MusicBrainz comunicarse contigo en caso de problemas o para soporte.
 *
 * Es recomendable proporcionar estos argumentos para usar la API de MusicBrainz con todas sus ventajas y evitar restricciones.
 */
class MusicBrainzClient(private val appName:String?=null,private val appVersion:String?=null, private val contact:String?=null) {
    @PublishedApi
    internal val mainScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    @PublishedApi
    internal val repository: MbRepositoryImpl  by lazy { MbRepositoryImpl(appName,appVersion,contact) }

    fun serchRecording(
        query: String,
        limit: Int,
        offset: Int,
        callback:(MbResponse<RecordingResponse>)-> Unit

    ){ mainScope.launch {
            val response= repository.searchRecording(query, limit, offset)
            callback(response)
        }
    }
    inline fun <reified T: Any> searchEntity(
        entity: SearchEntity,
        query: String,
        limit: Int,
        offset: Int,
        crossinline callback:(MbResponse<T>)-> Unit

    ){ mainScope.launch {
            val response= repository.genericSearchEntity<T>(entity, query, limit, offset)
            callback(response)
        }
    }
    inline fun <reified T:Any> lookupEntity(
        entity:LookupEntity,
        id: String,
        inc: String?,
        crossinline callback:(MbResponse<T>)-> Unit

    ){ mainScope.launch {
            val response= repository.genericLookupEntity<T>(entity, id, inc)
            callback(response)
        }
    }
    fun fetchCoverArt(
        mbId: String,
        callback:(MbResponse<CoverArtResponse>)-> Unit

    ){ mainScope.launch {
            val response= repository.fetchCoverArt(mbId)
            callback(response)
        }
    }
    fun getReleaseById(
        id: String,
        callback:(MbResponse<ReleaseResponse>)-> Unit

    ){ mainScope.launch {
            val response= repository.searchReleaseById(id)
            callback(response)
        }
    }
}