# MusicBrainz Android Client

[![Leer en Español](https://img.shields.io/badge/🌐-Leer%20en%20Español-red)](README_es.md)

A Kotlin library to interact with the [MusicBrainz
API](https://musicbrainz.org/doc/MusicBrainz_API) on Android.\
It allows performing search, lookup, and fetching cover art in a simple
and structured way.

Currently supports:\

Search\
Direct lookups\
Relationship options (Include)\
Album cover art retrieval (Cover Art)

⚠️ The library is a work in progress and currently only includes support for
search, lookups, relationships, and cover art.

## Status

![Status](https://img.shields.io/badge/status-in%20progress-yellow)

## Instalación

En el archivo `settings.gradle.kts` agrega el repositorio de JitPack:

``` kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Add JitPack repository
        maven(url = "https://jitpack.io")
    }
}
```

En tu archivo `build.gradle.kts` agrega la dependencia:

``` kotlin
implementation("com.github.hall9zeha:Music-Brainz-Android-Client:1.0.0")
```

## Ejemplo de uso

### Inicialización del cliente

``` kotlin
// It is not mandatory to pass arguments, but it is recommended to include appName, appVersion,
// and contact information. This helps MusicBrainz identify your client, optimize requests,
// and avoid being flagged as spam or abusive usage.
val mbService = MusicBrainzClient(
    appName = "Test app",
    appVersion = "1.0.0",
    contact = "mail@mail.com"
)
```

### Queries

#### Specific query by Recording

``` kotlin
val recordingQuery = RecordingQueryBuilder()
    .title("I don't wanna go")
    .artist("Kidburn")
    .build()

mbService.searchRecording(
    query = recordingQuery,
    limit = 1,
    offset = 0
) { response ->
    response.onSuccess {
        Log.d("RESPONSE_MUZIC", "Success: $it")
    }
    response.onError {
        Log.e("RESPONSE_MUZIC", "Error ${it.errorCode}: ${it.message}")
        it.cause?.printStackTrace()
    }
}
```

#### Generic query by fields

``` kotlin
val genericQuery = GenericQueryBuilder()
    .field(SearchField.RECORDING, "I Don't Wanna Go")
    .field(SearchField.ARTIST, "Kidburn")
    .build()

mbService.searchEntity<RecordingResponse>(
    entity = SearchEntity.RECORDING,
    query = genericQuery,
    limit = 1,
    offset = 0
) { response ->
    response.onSuccess {
        Log.d("RESPONSE_MUZIC_GENERIC", "Success: $it")
    }
    response.onError {
        Log.e("RESPONSE_MUZIC", "Error ${it.errorCode}: ${it.message}")
        it.cause?.printStackTrace()
    }
}
```

#### Lookup by MBID

``` kotlin
mbService.lookupEntity<RecordingLookupResponse>(
    entity = LookupEntity.RECORDING,
    mbId = "b9ad642e-b012-41c7-b72a-42cf4911f9ff",
    inc = null // Pass null if you don’t want relations
) { response ->
    response.onSuccess {
        Log.d("RESPONSE_MUZIC_LOOKUP", "Success: $it")
    }
    response.onError {
        Log.e("RESPONSE_MUZIC_LOOKUP", "Error ${it.errorCode}: ${it.message}")
        it.cause?.printStackTrace()
    }
}
```

#### Lookup with relations

``` kotlin
val includeFields = GenericIncludeBuilder()
    .incArtistCredits()
    .incIsrcs()
    .incAliases()
    .build()

mbService.lookupEntity<RecordingLookupResponse>(
    entity = LookupEntity.RECORDING,
    mbId = "b9ad642e-b012-41c7-b72a-42cf4911f9ff",
    inc = includeFields
) { response ->
    response.onSuccess {
        Log.d("RESPONSE_MUZIC_LOOKUP_INC", "Success: $it")
    }
    response.onError {
        Log.e("RESPONSE_MUZIC_LOOKUP_INC", "Error ${it.errorCode}: ${it.message}")
        it.cause?.printStackTrace()
    }
}
```

#### Release query

``` kotlin
val releaseQuery = ReleaseQueryBuilder()
    .releaseId("4ad149df-86f9-47c5-96f3-8db9ffd66da4")
    .build()

mbService.getReleaseById(releaseQuery) {
    it.onSuccess {
        Log.d("RESPONSE_MUZIC_RELEASE", "Success: $it")
    }
    it.onError {
        Log.e("RESPONSE_MUZIC_RELEASE", "Error ${it.errorCode}: ${it.message}")
        it.cause?.printStackTrace()
    }
}
```

### Cover Art

#### By MBID (full object)

``` kotlin
mbService.fetchCoverArt(mbId = "99b09d02-9cc9-3fed-8431-f162165a9371") {
    it.onSuccess { coverArtResponse ->
        Log.d("RESPONSE_MUZIC_COVER_ART", "Success: $coverArtResponse")
    }
    it.onError { error ->
        Log.e("RESPONSE_MUZIC_COVER_ART", "Error ${error.errorCode}: ${error.message}")
        error.cause?.printStackTrace()
    }
}
```

#### Thumbnails

``` kotlin
mbService.fetchCoverArtThumbnail(mbId = "99b09d02-9cc9-3fed-8431-f162165a9371") {
    it.onSuccess { coverArtResponse ->
        Log.d("RESPONSE_MUZIC_COVER_THUMBNAIL", "Success: $coverArtResponse")
    }
    it.onError { error ->
        Log.e("RESPONSE_MUZIC_COVER_THUMBNAIL", "Error ${error.errorCode}: ${error.message}")
        error.cause?.printStackTrace()
    }
}
```

#### Front or Back

``` kotlin
mbService.fetchCoverArtSide(
    mbId = "99b09d02-9cc9-3fed-8431-f162165a9371",
    side = COVER_ART_BOTH_SIDES, // Default: COVER_ART_FRONT
    size = CoverSize.S_500 // Default: CoverSize.S_250
) {
    it.onSuccess { coverArtResponse ->
        Log.d("RESPONSE_MUZIC_COVER_FRONT", "${coverArtResponse.front}")
        Log.d("RESPONSE_MUZIC_COVER_BACK", "${coverArtResponse.back}")
    }
    it.onError { error ->
        Log.e("RESPONSE_MUZIC_COVER_FRONT", "Error ${error.errorCode}: ${error.message}")
        error.cause?.printStackTrace()
    }
}
```

#### By track title and artist

``` kotlin
// Fetches cover art URLs by track title and artist name.
// Both parameters are required to improve accuracy and reduce extra results.
// If `onlyFirst` is enabled, returns the first matching cover art; otherwise,
// returns all available URLs, which may take longer due to the more complex query.
mbService.fetchCoverArtByTitleAndArtist(
    title = "¿Sabes?",
    artist = "Álex ubago",
    side = COVER_ART_BOTH_SIDES, // Default: COVER_ART_FRONT
    size = CoverSize.S_500 // Default: CoverSize.S_250
) {
    it.onSuccess { coverArtResponse ->
        coverArtResponse.forEach { coverArtUrl ->
            Log.d("RESPONSE_MUZIC_COVER_BY_NAME", "${coverArtUrl.front}")
        }
    }
    it.onError { error ->
        Log.e("RESPONSE_MUZIC_COVER_BY_NAME", "Error ${error.errorCode}: ${error.message}")
        error.cause?.printStackTrace()
    }
}
```

------------------------------------------------------------------------

## Licencia

MIT License
This project is not officially affiliated with
[MetaBrainz](https://metabrainz.org/).
