package com.barryzeha.musicbrainzclient.data.model.entity.response

import kotlin.reflect.KClass

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/

sealed class MbResponse<out T>{
    data class Success<out T>(val response: T): MbResponse<T>()
    data class Error(val error: ErrorResponse): MbResponse<Nothing>()
}