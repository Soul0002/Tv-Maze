package hugocazarez.uabc.proyectofinal

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Base URL para todas las peticiones
const val BASE_URL = "https://api.tvmaze.com/"

// Modelo de datos para el Show y sus detalles relacionados
data class Show(
    val id: Int,
    val url: String?,
    val name: String,
    val type: String?,
    val language: String?,
    val genres: List<String>,
    val status: String?,
    val runtime: Int?,
    val averageRuntime: Int?,
    val premiered: String?,
    val ended: String?,
    val officialSite: String?,
    val schedule: Schedule?,
    val rating: Rating?,
    val image: Image?,
    val summary: String?,
    val network: Network? // Incluye información de la cadena y el país
)

data class Network(
    val id: Int?,
    val name: String?,
    val country: Country?
)


data class Schedule(
    val time: String?,
    val days: List<String>
)

data class Rating(
    val average: Double?
)

data class Image(
    val medium: String?,
    val original: String?
)

data class SearchResult(
    val show: Show
)

data class ShowDetails(
    val id: Int,
    val name: String,
    val genres: List<String>,
    val summary: String?,
    val image: Image?,
    val rating: Rating?,
    val language: String?,
    val premiered: String?,
    val schedule: Schedule?,
    val network: Network? // Incluye información de la cadena y el país
)

data class Country(
    val name: String,
    val code: String,
    val timezone: String
)


data class CastResponse(
    val person: Person,
    val character: Character
)

data class Cast(
    val person: Person,
    val character: Character
)

data class Person(
    val name: String,
    val image: Image?
)

data class Character(
    val name: String,
    val image: Image?
)


// Interfaz para las peticiones API
interface TvMazeApi {
    @GET("shows")
    suspend fun getShows(@Query("page") page: Int): List<Show>

    @GET("search/shows")
    suspend fun searchShows(@Query("q") name: String): List<SearchResult>

    @GET("shows/{id}?embed=cast")
    suspend fun getShowDetails(@Path("id") id: Int): ShowDetails



}

// Singleton para la instancia de Retrofit
object RetrofitInstance {
    val api: TvMazeApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvMazeApi::class.java)
    }
}
