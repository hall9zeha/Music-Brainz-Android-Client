# MusicBrainz Android Client
<a href="https://jitpack.io/#hall9zeha/Music-Brainz-Android-Client"><img alt="License" src="https://jitpack.io/v/hall9zeha/Music-Brainz-Android-Client.svg"/></a>
[![Read in English](https://img.shields.io/badge/🌐-Read%20in%20English-blue)](README.md)

Un cliente ligero para **Android** que simplifica el uso de la
[MusicBrainz API](https://musicbrainz.org/doc/MusicBrainz_API).\
Esta librería realiza todas las llamadas a la API de manera interna y
solo expone métodos sencillos para que los desarrolladores puedan
integrarla fácilmente en sus aplicaciones.

Actualmente soporta:\
- **Búsquedas (Search)**\
- **Consultas directas (Lookup)**\
- **Opciones de relaciones (Include)**\
- **Obtención de carátulas (Cover Art)**

> ⚠️ La librería está en progreso y por ahora solo incluye soporte para
> `search`, `lookups`, relaciones y carátulas.


## Estado del proyecto

![Status](https://img.shields.io/badge/status-in%20progress-yellow)



## Instalación

1. Abre tu archivo **`settings.gradle.kts`** y asegúrate de añadir el repositorio de **JitPack** dentro de
`dependencyResolutionManagement`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // Agregar JitPack
        maven(url = "https://jitpack.io")
    }
}

```
En tu archivo `build.gradle.kts` del módulo (por lo general app/build.gradle.kts), agrega la dependencia:

```kotlin
implementation("com.github.hall9zeha:Music-Brainz-Android-Client:1.0.0")

```

------------------------------------------------------------------------

## Inicialización del Cliente

``` kotlin
val mbService = MusicBrainzClient(
    appName = "Test app",
    appVersion = "1.0.0",
    contact = "mail@mail.com"
)
```

> No es obligatorio pasar estos parámetros, pero **sí es recomendable**
> para identificar tu aplicación, evitar limitaciones, y cumplir con las
> políticas de MusicBrainz. Esto ayuda a que tu aplicación no sea
> considerada como spam.

------------------------------------------------------------------------

## Ejemplos de Uso

### 1. Búsqueda específica por *Recording*

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
        Log.d("RESPONSE_MUZIC", "Éxito: $it")
    }
    response.onError {
        Log.e("RESPONSE_MUZIC", "Error ${it.errorCode}: ${it.message}")
        it.cause?.printStackTrace()
    }
}
```

### 2. Búsqueda genérica (Search con múltiples campos)

``` kotlin
val genericQuery = GenericQueryBuilder()
    .field(SearchField.RECORDING,"I Don't Wanna Go")
    .field(SearchField.ARTIST,"Kidburn")
    .build()

mbService.searchEntity<RecordingResponse>(
    entity = SearchEntity.RECORDING,
    query = genericQuery,
    limit = 1,
    offset = 0
) { response ->
    response.onSuccess {
        Log.d("RESPONSE_MUZIC_GENERIC", "Éxito: $it")
    }
    response.onError {
        Log.e("RESPONSE_MUZIC", "Error ${it.errorCode}: ${it.message}")
        it.cause?.printStackTrace()
    }
}
```

### 3. Lookup por *Recording*

``` kotlin
mbService.lookupEntity<RecordingLookupResponse>(
    entity = LookupEntity.RECORDING,
    mbId = "b9ad642e-b012-41c7-b72a-42cf4911f9ff",
    inc = null // Si no se quiere obtener relaciones, se puede pasar null
) { response ->
    response.onSuccess {
        Log.d("RESPONSE_MUZIC_LOOKUP", "Éxito: $it")
    }
    response.onError {
        Log.e("RESPONSE_MUZIC_LOOKUP", "Error ${it.errorCode}: ${it.message}")
        it.cause?.printStackTrace()
    }
}
```

### 4. Lookup con relaciones (Includes)

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
        Log.d("RESPONSE_MUZIC_LOOKUP_INC", "Éxito: $it")
    }
    response.onError {
        Log.e("RESPONSE_MUZIC_LOOKUP_INC", "Error ${it.errorCode}: ${it.message}")
        it.cause?.printStackTrace()
    }
}
```

### 5. Obtener Cover Art por MBID

``` kotlin
mbService.fetchCoverArt(mbId = "99b09d02-9cc9-3fed-8431-f162165a9371") {
    it.onSuccess { coverArtResponse ->
        Log.d("RESPONSE_MUZIC_COVER_ART", "Éxito: $coverArtResponse")
    }
    it.onError { error ->
        Log.e("RESPONSE_MUZIC_COVER_ART", "Error ${error.errorCode}: ${error.message}")
        error.cause?.printStackTrace()
    }
}
```

### 6. Obtener Cover Art (solo thumbnails)

``` kotlin
mbService.fetchCoverArtThumbnail(mbId = "99b09d02-9cc9-3fed-8431-f162165a9371") {
    it.onSuccess { coverArtResponse ->
        Log.d("RESPONSE_MUZIC_COVER_THUMBNAIL", "Éxito: $coverArtResponse")
    }
    it.onError { error ->
        Log.e("RESPONSE_MUZIC_COVER_THUMBNAIL", "Error ${error.errorCode}: ${error.message}")
        error.cause?.printStackTrace()
    }
}
```

### 7. Cover Art frontal o trasero

``` kotlin
mbService.fetchCoverArtSide(
    mbId = "99b09d02-9cc9-3fed-8431-f162165a9371",
    side = COVER_ART_BOTH_SIDES, // Por defecto es COVER_ART_FRONT
    size = CoverSize.S_500        // Por defecto es CoverSize.S_250
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

### 8. Cover Art por título y artista

``` kotlin
mbService.fetchCoverArtByTitleAndArtist(
    title = "¿Sabes?",
    artist = "Álex ubago",
    side = COVER_ART_BOTH_SIDES, // Por defecto es COVER_ART_FRONT
    size = CoverSize.S_500       // Por defecto es CoverSize.S_250
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

## Entidades Soportadas
### 🔎 SearchEntity (para búsquedas genéricas)

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

### 🔍 LookupEntity (para consultas directas)

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

### 📑 SearchField (campos disponibles en queries genéricas)

| Categoría | Campos |
|-----------|--------|
| **Comunes** | alias, tag, comment |
| **Artist** | artist, artistname, arid, sortname |
| **Recording** | recording, recordingaccent, isrc, rid, reid, rgid, dur, qdur, video, creditname, tid |
| **Release** | release, releasegroup, country, format, status, primarytype, secondarytype, date, firstreleasedate, label, catno, barcode, lang |
| **Work** | work, iswc, type |
| **Series** | series |
| **URL** | url |
| **Otros** | place, event, instrument, genre |

---

### 📦 Search Responses disponibles

| Entidad       | Response               |
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
| Serie         | `SerieResponse`        |
| Tag           | `TagResponse`          |
| Url           | `UrlResponse`          |
| Work          | `WorkResponse`         |
| Place         | `PlaceResponse`        |

---

### 📦 Lookup Responses disponibles

| Entidad       | Response                     |
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




## Licencia

MIT License.\
Este proyecto no está afiliado oficialmente a
[MetaBrainz](https://metabrainz.org/).
