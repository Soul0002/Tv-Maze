package hugocazarez.uabc.proyectofinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment




@Composable
fun HomeScreen(
    shows: List<Show>,
    onShowClick: (Show) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(shows.size) { index ->
            val show = shows[index]
            Box(modifier = Modifier.padding(8.dp)) {
                // Card para mostrar los datos del show
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onShowClick(show) },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Elevación compatible con Material3
                ) {
                    Box {
                        // Imagen del show
                        Image(
                            painter = rememberImagePainter(show.image?.medium),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )

                        // Círculo con la calificación
                        if (show.rating?.average != null) {
                            Surface(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(40.dp)
                                    .align(Alignment.TopEnd),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = String.format("%.1f", show.rating.average),
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                // Información del show debajo de la imagen
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = show.name, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = show.genres.joinToString(", "),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

