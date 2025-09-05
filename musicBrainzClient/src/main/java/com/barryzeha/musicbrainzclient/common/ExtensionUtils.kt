package com.barryzeha.musicbrainzclient.common

import androidx.annotation.Keep
import com.barryzeha.musicbrainzclient.data.model.entity.coverentity.CoverImage
import com.barryzeha.musicbrainzclient.data.model.entity.response.ErrorResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.util.reflect.TypeInfo
import kotlin.reflect.KClass

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 23/08/25.
 * Copyright (c)  All rights reserved.
 ***/


@Suppress("DEPRECATION")
@Keep
suspend fun <T:Any> processResponse(clazz: KClass<T>, block: suspend () -> HttpResponse): MbResponse<T>{
   return try{
       val response = block()
       if (response.status.isSuccess()) {
           val typeInfo = TypeInfo(
               type = clazz ,
               reifiedType = clazz.java,
               kotlinType = null)
           val body = response.body(typeInfo) as T
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
@Keep
suspend inline fun <reified T> processResponses(block:suspend()-> List<HttpResponse>): MbResponse<List<T>>{
    var responsesProcessed : MutableList<T> = mutableListOf()
    return try{
        val responses = block()
        responsesProcessed = responses
            .filter{it.status.isSuccess()}
            .map{it.body<T>()}
            .toMutableList()
        if (responsesProcessed.isEmpty()) {
           return  MbResponse.Error(ErrorResponse(-1, "No valid responses"))
        } else {
            MbResponse.Success(responsesProcessed)
        }

       return MbResponse.Success(responsesProcessed)
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
@Keep
inline fun <T> MbResponse<T>.onSuccess(action:(T)->Unit): MbResponse<T>{
    if(this is MbResponse.Success) action(response)
    return this
}
@Keep
inline fun <T> MbResponse<T>.onError(action:(ErrorResponse)->Unit): MbResponse<T>{
    if(this is MbResponse.Error) action(error)
    return this
}
fun CoverImage.getThumbnail(size: String): String? =
    when (size) {
        "1200" -> thumbnails.size1200
        "500" -> thumbnails.size500
        "250" -> thumbnails.size250
        "small"->thumbnails.small
        "large"->thumbnails.large
        else -> null
    }
fun String.allTrim():String{
    val regex = Regex("[()]")
    return this.replace(regex," ").replace(" ","")
}