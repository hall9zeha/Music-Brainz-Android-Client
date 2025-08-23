package com.barryzeha.musicbrainzclient.data.model.entity.response

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/

data class ErrorResponse(
    val errorCode:Int,
    val message:String,
    val cause: Throwable?= null
)
