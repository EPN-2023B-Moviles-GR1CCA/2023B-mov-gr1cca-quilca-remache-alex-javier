import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader
import java.io.FileWriter
import java.util.Date

data class Personaje(
    val id: Int,
    var nombre: String,
    var fecha: Date,
    var pelicula: String,
    var famoso: Boolean
) {
    companion object {
        private val gson = Gson()
        private val actorType = object : TypeToken<List<Personaje>>() {}.type
        val json : String = "/home/alexquilca/Documentos/personajes.json"
        val personajes: MutableList<Personaje> = read().toMutableList()

        fun create(nombre: String,
                   fecha: Date,
                   pelicula: String,
                   famoso: Boolean
        ): Personaje {

            val newId = if (personajes.isEmpty()) 1 else personajes.maxOf { it.id } + 1
            val nuevoPersonaje = Personaje(newId, nombre, fecha, pelicula, famoso)
            personajes.add(nuevoPersonaje)
            update()

            return nuevoPersonaje
        }

        fun read(): List<Personaje> {
            return FileReader(json).use { reader ->
                gson.fromJson(reader, actorType) ?: emptyList()
            }
        }

        fun readById(id: Int): Personaje? {
            return personajes.find { it.id == id }
        }

        fun findIndex(id: Int): Int{
            return personajes.indexOfFirst { it.id == id }
        }

        fun update() {
            FileWriter(json).use { it.write(gson.toJson(personajes)) }
        }

        fun delete(id: Int) {
            personajes.removeIf { it.id == id }
            update()
        }
    }

    fun nombre(): String{
        return "Nombre: $nombre"
    }

    fun imp(): String{
        return "Id: $id --> $nombre"
    }

    override fun toString(): String {
        return "Id: $id\nNombre: $nombre\nFecha de creación: $fecha\nPelícula: $pelicula\n¿Es famoso?: $famoso\n"
    }
}
