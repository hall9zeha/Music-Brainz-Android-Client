package com.barryzeha.musicbrainzclient

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.barryzeha.musicbrainzclient.common.LookupEntity
import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.common.SearchField
import com.barryzeha.musicbrainzclient.common.onError
import com.barryzeha.musicbrainzclient.common.onSuccess
import com.barryzeha.musicbrainzclient.common.utils.RecordingQueryBuilder
import com.barryzeha.musicbrainzclient.common.utils.ReleaseQueryBuilder
import com.barryzeha.musicbrainzclient.data.model.entity.mbentity.Recording
import com.barryzeha.musicbrainzclient.data.model.entity.response.RecordingLookupResponse
import com.barryzeha.musicbrainzclient.data.model.entity.response.RecordingResponse


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
                   Log.d("RESPONSE_MUZIC", SearchField.ARTIST.key)

               }
                response.onError{
                    Log.e("RESPONSE_MUZIC", "Error ${it.errorCode}: ${it.message}")
                    it.cause?.printStackTrace()

                }

        }
        mbService.searchEntity<RecordingResponse>(
            SearchEntity.RECORDING,
            query,
            1,
            1
        ) { response ->
            response.onSuccess {
                Log.d("RESPONSE_MUZIC_GENERIC", "Éxito: $it")
                Log.d("RESPONSE_MUZIC_GENERIC", SearchField.ARTIST.key)

            }
            response.onError{
                Log.e("RESPONSE_MUZIC", "Error ${it.errorCode}: ${it.message}")
                it.cause?.printStackTrace()

            }
        }
        mbService.lookupEntity<RecordingLookupResponse>(
            LookupEntity.RECORDING,
            "b9ad642e-b012-41c7-b72a-42cf4911f9ff",
            null

        ) { response ->
            response.onSuccess {
                Log.d("RESPONSE_MUZIC_LOOKUP", "Éxito: $it")
                Log.d("RESPONSE_MUZIC_LOOKUP", SearchField.ARTIST.key)

            }
            response.onError{
                Log.e("RESPONSE_MUZIC_LOOKUP", "Error ${it.errorCode}: ${it.message}")
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