package hugocazarez.uabc.proyectofinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import hugocazarez.uabc.proyectofinal.MainViewModel
import hugocazarez.uabc.proyectofinal.SearchResult
import hugocazarez.uabc.proyectofinal.Show

@Composable
fun SearchScreen(
    viewModel: MainViewModel,
    onShowClick: (Show) -> Unit
) {
    val searchResults by viewModel.searchResults.collectAsState()
    val searchQuery = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Barra de búsqueda
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            placeholder = { Text("Buscar programas") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón de búsqueda
        Button(
            onClick = { viewModel.searchShows(searchQuery.value) },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Buscar")
        }

        // Lista de resultados
        LazyColumn {
            items(searchResults) { result ->
                val show = result.show
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onShowClick(show) }
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        show.image?.medium?.let {
                            Image(
                                painter = rememberImagePainter(it),
                                contentDescription = null,
                                modifier = Modifier.size(100.dp).padding(end = 8.dp)
                            )
                        }
                        Column {
                            Text(show.name, style = MaterialTheme.typography.titleLarge)
                            Text(
                                show.genres.joinToString(", "),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
