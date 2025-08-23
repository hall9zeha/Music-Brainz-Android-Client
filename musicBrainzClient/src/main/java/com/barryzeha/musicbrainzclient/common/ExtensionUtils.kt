package com.barryzeha.musicbrainzclient.common

import com.barryzeha.musicbrainzclient.data.model.entity.response.ErrorResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.MusicBrainzResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/

suspend inline fun <reified T> processResponse(block:suspend()-> HttpResponse): MbResponse<T>{
   return try{
       val response = block()
       if (response.status.isSuccess()) {
           val body = response.body<T>()
           MbResponse.Success(body)
       } else {
           MbResponse.Error(
               ErrorResponse(
                   errorCode = response.status.value,
                   message = response.status.description
               )
           )
       }
   }catch(e:Exception){
       MbResponse.Error(
           ErrorResponse(
               errorCode = -1,
               message = e.message ?: "Unknown error",
               cause = e
           )
       )
   }

}

inline fun <T> MbResponse<T>.onSuccess(action:(T)->Unit): MbResponse<T>{
    if(this is MbResponse.Success) action(response)
    return this
}

inline fun <T> MbResponse<T>.onError(action:(ErrorResponse)->Unit): MbResponse<T>{
    if(this is MbResponse.Error) action(error)
    return this
}