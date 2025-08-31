package com.barryzeha.musicbrainzclient.common

import com.barryzeha.musicbrainzclient.data.model.entity.coverentity.CoverImage
import com.barryzeha.musicbrainzclient.data.model.entity.response.ErrorResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

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
fun CoverImage.getThumbnail(size: Int): String? =
    when (size) {
        1200 -> thumbnails.size1200
        500 -> thumbnails.size500
        250 -> thumbnails.size250
        else -> null
    }
fun String.allTrim():String{
    val regex = Regex("[()]")
    return this.replace(regex," ").replace(" ","")
}