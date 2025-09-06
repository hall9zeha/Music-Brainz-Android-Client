# MusicBrainz Android Client

<a href="https://jitpack.io/#hall9zeha/Music-Brainz-Android-Client"><img alt="License" src="https://jitpack.io/v/hall9zeha/Music-Brainz-Android-Client.svg"/></a>
[![Leer en Español](https://img.shields.io/badge/🌐-Leer%20en%20Español-red)](README_es.md)

A Kotlin library to interact with the [MusicBrainz
API](https://musicbrainz.org/doc/MusicBrainz_API) on Android.\
It allows performing search, lookup, and fetching cover art in a simple
and structured way.

### Currently supports

- Search
- Direct lookups
- Relationship options (Include)
- Album cover art retrieval (Cover Art)

⚠️ The library is a work in progress and currently only includes support for
search, lookups, relationships, and cover art.

## Status

![Status](https://img.shields.io/badge/status-in%20progress-yellow)

## Requirements

- minSdk 24
- Java compile version: 21 (configure `compileOptions` and `kotlinOptions` to use `JavaVersion.VERSION_21` and `jvmTarget = "21"`)


## How to install

In the archive `settings.gradle.kts` add the JitPack repository:

``` kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Add JitPack repository
        maven(url = "https://jitpack.io")
    }
}
```

In the archive `build.gradle.kts` add the dependency:

``` kotlin
implementation("com.github.hall9zeha:Music-Brainz-Android-Client:1.0.0")
```

Remember to add the internet access permission in your application's manifest:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```


## How to use

This project implements two specific queries for MusicBrainz: one for Recording (using search) and another for Release (using lookup), as a proof of concept. However, by utilizing the generic Search and Lookup queries, along with dynamic query generators for search and relations or includes parameters for lookup, almost any request can be made to the MusicBrainz API.

### Client initialization

``` kotlin
// It is not mandatory to pass arguments, but it is recommended to include appName, appVersion,
// and contact information. This helps MusicBrainz identify your client, optimize requests,
// and avoid being flagged as spam or abusive usage.
val mbClient = MusicBrainzClient(
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

mbClient.searchRecording(
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

#### Specific search and query from Release

``` kotlin
val releaseQuery = ReleaseQueryBuilder()
    .releaseId("4ad149df-86f9-47c5-96f3-8db9ffd66da4")
    .build()

mbClient.getReleaseById(releaseQuery) {
    it.onSuccess {
        Log.d("RESPONSE_MUZIC_RELEASE", "Success: $it")
    }
    it.onError {
        Log.e("RESPONSE_MUZIC_RELEASE", "Error ${it.errorCode}: ${it.message}")
        it.cause?.printStackTrace()
    }
}
```


#### Generic Search with a generic query from Recording

``` kotlin
val genericQuery = GenericQueryBuilder()
    .field(SearchField.RECORDING, "I Don't Wanna Go")
    .field(SearchField.ARTIST, "Kidburn")
    .build()

mbClient.searchEntity<RecordingResponse>(
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
mbClient.lookupEntity<RecordingLookupResponse>(
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

mbClient.lookupEntity<RecordingLookupResponse>(
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



### Cover Art

#### By MBID (full object)

``` kotlin
mbClient.fetchCoverArt(mbId = "99b09d02-9cc9-3fed-8431-f162165a9371") {
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
mbClient.fetchCoverArtThumbnail(mbId = "99b09d02-9cc9-3fed-8431-f162165a9371") {
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
mbClient.fetchCoverArtSide(
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
mbClient.fetchCoverArtByTitleAndArtist(
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
## Supported Entities
------------------------------------------------------------------------
### 🔎 SearchEntity (for generic searches)

| Enum          | Path           |
|---------------|----------------|
| AREA          | `area`         |
| ARTIST        | `artist`       |
| EVENT         | `event`        |
| INSTRUMENT    | `instrument`   |
| LABEL         | `label`        |
| PLACE         | `place`        |
| RECORDING     | `recording`    |
| RELEASE       | `release`      |
| RELEASE_GROUP | `release-group`|
| SERIES        | `series`       |
| WORK          | `work`         |

---

### 🔍 LookupEntity (for direct lookups)

| Enum          | Path           |
|---------------|----------------|
| AREA          | `area`         |
| ARTIST        | `artist`       |
| GENRE         | `genre`        |
| EVENT         | `event`        |
| INSTRUMENT    | `instrument`   |
| LABEL         | `label`        |
| PLACE         | `place`        |
| RECORDING     | `recording`    |
| RELEASE       | `release`      |
| RELEASE_GROUP | `release-group`|
| RATING        | `rating`       |
| WORK          | `work`         |
| URL           | `url`          |

---

### 📑 SearchField (available fields in generic queries)

| Category   | Fields |
|------------|--------|
| **Common** | alias, tag, comment |
| **Artist** | artist, artistname, arid, sortname |
| **Recording** | recording, recordingaccent, isrc, rid, reid, rgid, dur, qdur, video, creditname, tid |
| **Release** | release, releasegroup, country, format, status, primarytype, secondarytype, date, firstreleasedate, label, catno, barcode, lang |
| **Work** | work, iswc, type |
| **Series** | series |
| **URL** | url |
| **Others** | place, event, instrument, genre |

---

### 📦 Available Search Responses

| Entity        | Response               |
|---------------|------------------------|
| Annotation    | `AnnotationResponse`   |
| Area          | `AreaResponse`         |
| CdStub        | `CdStubsResponse`      |
| Event         | `EventResponse`        |
| Instrument    | `InstrumentResponse`   |
| Label         | `LabelResponse`        |
| Recording     | `RecordingResponse`    |
| Release       | `ReleaseResponse`      |
| Artist        | `ArtistResponse`       |
| Release Group | `ReleaseGroupResponse` |
| Series        | `SerieResponse`        |
| Tag           | `TagResponse`          |
| Url           | `UrlResponse`          |
| Work          | `WorkResponse`         |
| Place         | `PlaceResponse`        |

---

### 📦 Available Lookup Responses

| Entity        | Response                     |
|---------------|------------------------------|
| Area          | `AreaLookupResponse`         |
| Artist        | `ArtistLookupResponse`       |
| Event         | `EventLookupResponse`        |
| Genre         | `GenreLookupResponse`        |
| Instrument    | `InstrumentLookupResponse`   |
| Label         | `LabelLookupResponse`        |
| Place         | `PlaceLookupResponse`        |
| Recording     | `RecordingLookupResponse`    |
| Release       | `ReleaseLookupResponse`      |
| Release Group | `ReleaseGroupLookupResponse` |
| Url           | `UrlLookupResponse`          |
| Work          | `WorkLookupResponse`         |


## Licence

MIT License
This project is not officially affiliated with
[MetaBrainz](https://metabrainz.org/).
