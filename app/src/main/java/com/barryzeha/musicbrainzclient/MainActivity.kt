package com.barryzeha.musicbrainzclient

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.barryzeha.musicbrainzclient.common.onError
import com.barryzeha.musicbrainzclient.common.onSuccess
import com.barryzeha.musicbrainzclient.data.model.entity.response.MbResponse
import com.barryzeha.musicbrainzclient.data.remote.MusicBrainzService
import com.barryzeha.musicbrainzclient.data.repository.MbRepositoryImpl

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
        mbService.serchRecording(
            "recording:\"I don't wanna go\" AND artist:\"Kidb\"",
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
    }
}