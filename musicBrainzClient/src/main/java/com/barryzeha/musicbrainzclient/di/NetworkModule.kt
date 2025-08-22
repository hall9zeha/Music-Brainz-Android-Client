package com.barryzeha.musicbrainzclient.di

import com.barryzeha.musicbrainzclient.data.remote.HttpClientProvider
import com.barryzeha.musicbrainzclient.data.remote.MusicBrainzService
import org.koin.dsl.module

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 22/08/25.
 * Copyright (c)  All rights reserved.
 ***/

val networkModule = module {
    single{ HttpClientProvider.create() }
    single{ MusicBrainzService(get()) }
}