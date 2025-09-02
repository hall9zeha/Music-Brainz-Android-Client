package com.barryzeha.musicbrainzclient

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.barryzeha.musicbrainzclient.common.COVER_ART_BOTH_SIDES
import com.barryzeha.musicbrainzclient.common.SearchEntity
import com.barryzeha.musicbrainzclient.common.SearchField
import com.barryzeha.musicbrainzclient.common.onError
import com.barryzeha.musicbrainzclient.common.onSuccess
import com.barryzeha.musicbrainzclient.common.utils.GenericIncludeBuilder
import com.barryzeha.musicbrainzclient.common.utils.GenericQueryBuilder
import com.barryzeha.musicbrainzclient.common.utils.RecordingQueryBuilder
import com.barryzeha.musicbrainzclient.common.utils.ReleaseQueryBuilder
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
        val mbService = MusicBrainzClient(appName = "Test app", appVersion = "1.0.0", contact = "mail@mail.com")

        val query = RecordingQueryBuilder()
            .title("I don't wanna go")
            .artist("Kidburn")
            .build()
        val queryGeneric = GenericQueryBuilder()
            .field(SearchField.RECORDING,"I Don't Wanna Go")
            .field(SearchField.ARTIST,"Kidburn")
            .build()

        val releaseQuery= ReleaseQueryBuilder()
            .releaseId("4ad149df-86f9-47c5-96f3-8db9ffd66da4")
            .build()

        val includeFields = GenericIncludeBuilder()
            .incArtistCredits()
            .build()

       /* mbService.serchRecording(
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

        }*/
       /* mbService.searchEntity<RecordingResponse>(
            SearchEntity.RECORDING,
            queryGeneric,
            1,
            1
        ) { response ->
            response.onSuccess {
                Log.d("RESPONSE_MUZIC_GENERIC", "Éxito: $it")
                }
            response.onError{
                Log.e("RESPONSE_MUZIC", "Error ${it.errorCode}: ${it.message}")
                it.cause?.printStackTrace()

            }
        }*/
        // Lookup
       /* mbService.lookupEntity<RecordingLookupResponse>(
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
        }*/

       /* mbService.getReleaseById(releaseQuery) {
            it.onSuccess {
                Log.d("RESPONSE_MUZIC_RELEASE", "Éxito: $it")
            }
            it.onError{
                Log.e("RESPONSE_MUZIC_RELEASE", "Error ${it.errorCode}: ${it.message}")
                it.cause?.printStackTrace()

            }
        }*/
        // Cover Art
       /* mbService.fetchCoverArt(mbId = "99b09d02-9cc9-3fed-8431-f162165a9371") {
            it.onSuccess { coverArtResponse ->
                Log.d("RESPONSE_MUZIC_COVER_ART", "Éxito: $coverArtResponse")

            }
            it.onError { error ->
                Log.e("RESPONSE_MUZIC_COVER_ART", "Error ${error.errorCode}: ${error.message}")
                error.cause?.printStackTrace()
            }
        }*/
        // CovertArt thumbnails
       /* mbService.fetchCoverArtThumbnail(mbId = "99b09d02-9cc9-3fed-8431-f162165a9371") {
            it.onSuccess { coverArtResponse ->
                Log.d("RESPONSE_MUZIC_COVER_THUMBNAIL", "Éxito: $coverArtResponse")

            }
            it.onError { error ->
                Log.e("RESPONSE_MUZIC_COVER_THUMBNAIL", "Error ${error.errorCode}: ${error.message}")
                error.cause?.printStackTrace()
            }
        }*/
        // CovertArt front or back
       /* mbService.fetchCoverArtSide(mbId = "99b09d02-9cc9-3fed-8431-f162165a9371", side= COVER_ART_BOTH_SIDES,size = 500) {
            it.onSuccess { coverArtResponse ->
                Log.d("RESPONSE_MUZIC_COVER_FRONT", "${coverArtResponse.front}")
                Log.d("RESPONSE_MUZIC_COVER_BACK", "${coverArtResponse.back}")

            }
            it.onError { error ->
                Log.e("RESPONSE_MUZIC_COVER_FRONT", "Error ${error.errorCode}: ${error.message}")
                error.cause?.printStackTrace()
            }
        }*/
        // FetchCovert art by track name

        mbService.fetchCoverArtByTitleAndArtist("¿Sabes?","Álex ubago", side= COVER_ART_BOTH_SIDES,size = 500) {
            it.onSuccess { coverArtResponse ->
                coverArtResponse.forEach { coverArtUrl->
                    Log.d("RESPONSE_MUZIC_COVER_BY_NAME", "${coverArtUrl.front}")
                    //Log.d("RESPONSE_MUZIC_COVER_BY_NAME", "${coverArtResponse.back}")
                }
            }
            it.onError { error ->
                Log.e("RESPONSE_MUZIC_COVER_BY_NAME", "Error ${error.errorCode}: ${error.message}")
                error.cause?.printStackTrace()
            }
        }
        // End


        // Lookup with include field
       /* mbService.lookupEntity<RecordingLookupResponse>(LookupEntity.RECORDING,"b9ad642e-b012-41c7-b72a-42cf4911f9ff","artist-credits") {
            it.onSuccess { recordingLookupResponse ->
                Log.d("RESPONSE_MUZIC_LOOKUP_INC", "Éxito: $recordingLookupResponse")

            }
            it.onError { error ->
                Log.e("RESPONSE_MUZIC_LOOKUP_INC", "Error ${error.errorCode}: ${error.message}")
                error.cause?.printStackTrace()
            }
        }*/

    }
}