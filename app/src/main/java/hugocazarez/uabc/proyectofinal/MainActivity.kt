package hugocazarez.uabc.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import hugocazarez.uabc.proyectofinal.AppDatabase
import hugocazarez.uabc.proyectofinal.ui.theme.ProyectoFinalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database: AppDatabase
        val viewModel: MainViewModel

        try {
            // Crear la instancia de la base de datos con manejo de migraciones
            database = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "favorite_shows_db"
            )
                .fallbackToDestructiveMigration()
                .build()

            // Crear el ViewModel usando un Factory
            viewModel = ViewModelProvider(this, MainViewModelFactory(database))[MainViewModel::class.java]
        } catch (e: Exception) {
            setContent {
                ProyectoFinalTheme {
                    Text("Error al inicializar: ${e.message}")
                }
            }
            return
        }

        // Configuración de la UI
        setContent {
            ProyectoFinalTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}

