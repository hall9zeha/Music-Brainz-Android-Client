package com.barryzeha.musicbrainzclient.common

import androidx.annotation.Keep

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 3/09/25.
 * Copyright (c)  All rights reserved.
 ***/
@Keep
enum class CoverSize(val value: String) {
    S_250("250"),
    S_500("500"),
    S_1200("1200"),
    S_SMALL("small"),
    S_LARGE("large")
}