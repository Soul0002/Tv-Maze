package hugocazarez.uabc.proyectofinal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import hugocazarez.uabc.proyectofinal.AppDatabase
import hugocazarez.uabc.proyectofinal.FavoriteShow

class MainViewModel(private val database: AppDatabase) : ViewModel() {

    // Estado para los shows principales
    private val _shows = MutableStateFlow<List<Show>>(emptyList())
    val shows: StateFlow<List<Show>> get() = _shows

    // Estado para los favoritos
    private val _favoriteShows = MutableStateFlow<List<FavoriteShow>>(emptyList())
    val favoriteShows: StateFlow<List<FavoriteShow>> get() = _favoriteShows

    // Estado para los resultados de búsqueda
    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> get() = _searchResults

    // Estado para los detalles de un show
    private val _showDetails = MutableStateFlow<ShowDetails?>(null)
    val showDetails: StateFlow<ShowDetails?> get() = _showDetails

    init {
        loadFavorites()
        fetchShows()
    }

    // Obtiene los shows principales (limitados a 48)
    private fun fetchShows() {
        viewModelScope.launch {
            try {
                val fetchedShows = RetrofitInstance.api.getShows(page = 0) // Solicita la primera página
                _shows.value = fetchedShows.take(48)
            } catch (e: Exception) {
                e.printStackTrace() // Manejo de errores
            }
        }
    }

    // Cargar favoritos desde la base de datos
    private fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val favorites = database.favoriteShowDao().getAll().map { entity ->
                FavoriteShow(
                    id = entity.id,
                    name = entity.name,
                    genres = entity.genres,
                    image = entity.image,
                    rating = entity.rating
                )
            }
            withContext(Dispatchers.Main) {
                _favoriteShows.value = favorites
            }
        }
    }

    // Añadir un favorito a la base de datos
    fun addFavorite(show: FavoriteShow) {
        viewModelScope.launch(Dispatchers.IO) {
            database.favoriteShowDao().insertAll(
                FavoriteShow(
                    id = show.id,
                    name = show.name,
                    genres = show.genres,
                    image = show.image,
                    rating = show.rating
                )
            )
            loadFavorites()
        }
    }

    // Eliminar un favorito de la base de datos
    fun removeFavorite(show: FavoriteShow) {
        viewModelScope.launch(Dispatchers.IO) {
            database.favoriteShowDao().delete(
                FavoriteShow(
                    id = show.id,
                    name = show.name,
                    genres = show.genres,
                    image = show.image,
                    rating = show.rating
                )
            )
            loadFavorites()
        }
    }

    // Buscar shows por nombre
    fun searchShows(query: String) {
        viewModelScope.launch {
            try {
                val results = RetrofitInstance.api.searchShows(query)
                _searchResults.value = results
            } catch (e: Exception) {
                e.printStackTrace() // Manejo de errores
            }
        }
    }

    // Obtener los detalles de un show
    fun fetchShowDetails(id: Int) {
        viewModelScope.launch {
            try {
                val details = RetrofitInstance.api.getShowDetails(id)
                _showDetails.value = details
            } catch (e: Exception) {
                e.printStackTrace() // Manejo de errores
            }
        }
    }
}
