package hugocazarez.uabc.proyectofinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.compose.ui.Alignment

@Composable
fun ShowDetailsScreen(
    viewModel: MainViewModel,
    showId: Int
) {
    val showDetails by viewModel.showDetails.collectAsState()

    LaunchedEffect(showId) {
        viewModel.fetchShowDetails(showId)
    }

    showDetails?.let { details ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Imagen del programa
                details.image?.medium?.let { imageUrl ->
                    Image(
                        painter = rememberImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

                // Nombre del programa
                Text(
                    text = details.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Calificación
                details.rating?.average?.let { rating ->
                    Text(
                        text = "Calificación: $rating",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Géneros
                Text(
                    text = "Géneros: ${details.genres.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Fecha de inicio
                details.premiered?.let { premiered ->
                    Text(
                        text = "Fecha de inicio: $premiered",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Región y Zona Horaria
                details.network?.country?.let { country ->
                    Text(
                        text = "Región: ${country.name} (${country.code})",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Idioma
                details.language?.let { language ->
                    Text(
                        text = "Idioma: $language",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Descripción
                Text(
                    text = details.summary.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Botón para añadir a favoritos
            Button(
                onClick = {
                    viewModel.addFavorite(
                        FavoriteShow(
                            id = details.id,
                            name = details.name,
                            genres = details.genres.joinToString(", "),
                            image = details.image?.medium,
                            rating = details.rating?.average
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Agregar a Favoritos")
            }
        }
    } ?: Text(
        text = "Cargando...",
        modifier = Modifier.fillMaxSize().wrapContentSize()
    )
}
