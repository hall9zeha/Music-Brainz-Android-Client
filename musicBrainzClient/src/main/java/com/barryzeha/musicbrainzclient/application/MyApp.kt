package com.barryzeha.musicbrainzclient.application

import android.app.Application
import com.barryzeha.musicbrainzclient.di.networkModule
import io.ktor.http.ContentType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/****
 * Project MusicBrainz
 * Created by Barry Zea H. on 22/08/25.
 * Copyright (c)  All rights reserved.
 ***/

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                networkModule
            )
        }
    }
}