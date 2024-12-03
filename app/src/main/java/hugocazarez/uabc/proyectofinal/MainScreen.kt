package hugocazarez.uabc.proyectofinal

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.filled.Search
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()

    // Observar los datos desde el ViewModel
    val shows by viewModel.shows.collectAsState()
    val favoriteShows by viewModel.favoriteShows.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = navController.currentDestination?.route == "Home",
                    onClick = { navController.navigate("Home") },
                    label = { Text("Inicio") },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") }
                )
                NavigationBarItem(
                    selected = navController.currentDestination?.route == "Favorites",
                    onClick = { navController.navigate("Favorites") },
                    label = { Text("Favoritos") },
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") }
                )
                NavigationBarItem(
                    selected = navController.currentDestination?.route == "Search",
                    onClick = { navController.navigate("Search") },
                    label = { Text("Buscar") },
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Pantalla de Inicio
            composable("Home") {
                HomeScreen(
                    shows = shows,
                    onShowClick = { selectedShow ->
                        navController.navigate("Details/${selectedShow.id}")
                    }
                )
            }
            // Pantalla de Favoritos
            composable("Favorites") {
                FavoritesScreen(
                    favoriteShows = favoriteShows,
                    onRemoveClick = { showToRemove ->
                        viewModel.removeFavorite(showToRemove) // Lógica para eliminar un favorito
                    }
                )
            }
            // Pantalla de Búsqueda
            composable("Search") {
                SearchScreen(
                    viewModel = viewModel,
                    onShowClick = { selectedShow ->
                        navController.navigate("Details/${selectedShow.id}")
                    }
                )
            }
            // Pantalla de Detalles
            composable(
                route = "Details/{showId}",
                arguments = listOf(navArgument("showId") { type = NavType.IntType })
            ) { backStackEntry ->
                val showId = backStackEntry.arguments?.getInt("showId") ?: return@composable
                if (showId != null) {
                    ShowDetailsScreen(viewModel = viewModel, showId = showId)
                }
            }
        }
    }
}
