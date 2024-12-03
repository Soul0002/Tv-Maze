package hugocazarez.uabc.proyectofinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun FavoritesScreen(
    favoriteShows: List<FavoriteShow>,
    onRemoveClick: (FavoriteShow) -> Unit
) {
    if (favoriteShows.isEmpty()) {
        // Mensaje cuando no hay favoritos
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tienes favoritos.",
                style = MaterialTheme.typography.titleLarge
            )
        }
    } else {
        // Lista de favoritos
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            items(favoriteShows) { show ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Imagen del show
                        show.image?.let { imageUrl ->
                            Image(
                                painter = rememberImagePainter(imageUrl),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(end = 16.dp)
                            )
                        }

                        // Información del show
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = show.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "Géneros: ${show.genres}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            show.rating?.let { rating ->
                                Text(
                                    text = "Calificación: ${String.format("%.1f", rating)}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        // Botón para eliminar
                        IconButton(
                            onClick = { onRemoveClick(show) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar de Favoritos"
                            )
                        }
                    }
                }
            }
        }
    }
}
