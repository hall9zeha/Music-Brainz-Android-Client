package com.barryzeha.musicbrainzclient

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.barryzeha.musicbrainzclient.common.onError
import com.barryzeha.musicbrainzclient.common.onSuccess
import com.barryzeha.musicbrainzclient.common.utils.RecordingQueryBuilder
import com.barryzeha.musicbrainzclient.common.utils.ReleaseQueryBuilder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mbService = MusicBrainzClient()

        val query = RecordingQueryBuilder()
            .title("I don't wanna go")
            .artist("Kidburn")
            .build()
        val releaseQuery= ReleaseQueryBuilder()
            .releaseId("4ad149df-86f9-47c5-96f3-8db9ffd66da4")
            .build()

        mbService.serchRecording(
            query,
            1,
            1
        ) { response ->
               response.onSuccess {
                   Log.d("RESPONSE_MUZIC", "Éxito: $it")
               }
                response.onError{
                    Log.e("RESPONSE_MUZIC", "Error ${it.errorCode}: ${it.message}")
                    it.cause?.printStackTrace()

                }

        }
        mbService.getReleaseById(releaseQuery) {
            it.onSuccess {
                Log.d("RESPONSE_MUZIC_RELEASE", "Éxito: $it")
            }
            it.onError{
                Log.e("RESPONSE_MUZIC_RELEASE", "Error ${it.errorCode}: ${it.message}")
                it.cause?.printStackTrace()

            }
        }
    }
}