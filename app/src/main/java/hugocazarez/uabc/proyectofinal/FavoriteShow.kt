package hugocazarez.uabc.proyectofinal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_shows")
data class FavoriteShow(
    @PrimaryKey val id: Int, // Clave primaria
    val name: String,
    val genres: String, // Debe ser un String si se almacena directamente
    val image: String?, // URL de la imagen como String
    val rating: Double? // Campo opcional
)
