package com.barryzeha.musicbrainzclient.data.model.entity.response

import kotlinx.serialization.Serializable


/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 27/08/25.
 * Copyright (c)  All rights reserved.
 ***/

@Serializable
class CoverArtUrls {
    var front:String?=null
    var back:String?=null
}