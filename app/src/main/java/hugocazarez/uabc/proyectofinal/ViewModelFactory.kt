package hugocazarez.uabc.proyectofinal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hugocazarez.uabc.proyectofinal.AppDatabase

class MainViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
