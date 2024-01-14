import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader
import java.io.FileWriter
import java.util.Date

data class Actor(
    val id: Int,
    var nombre: String,
    var fecha: Date,
    var ateo: Boolean,
    var altura: Float,
    val personajes: MutableList<Personaje?>
) {
    companion object {
        private val gson = Gson()
        private val actorType = object : TypeToken<List<Actor>>() {}.type
        val json : String = "actores.json"
        val actores: MutableList<Actor> = read().toMutableList()

        fun create(nombre: String,
                        fecha: Date,
                        ateo: Boolean,
                        altura: Float,
                        personajes: MutableList<Personaje?>
            ): Actor {

            val newId = if (actores.isEmpty()) 1 else actores.maxOf { it.id } + 1
            val nuevoActor = Actor(newId, nombre, fecha, ateo, altura, personajes)
            actores.add(nuevoActor)
            update()

            return nuevoActor
        }

        fun read(): List<Actor> {
            return FileReader(json).use { reader ->
                gson.fromJson(reader, actorType) ?: emptyList()
            }
        }

        fun readById(id: Int): Actor? {
            return actores.find { it.id == id }
        }

        fun findIndex(id: Int): Int{
            return actores.indexOfFirst { it.id == id }
        }

        fun findIndexPersonajes(id: Int, personajes: MutableList<Personaje?>): Int{
            return personajes.indexOfFirst { it?.id == id }
        }

        fun update() {
            FileWriter(json).use { it.write(gson.toJson(actores)) }
        }

        fun delete(id: Int) {
            actores.removeIf { it.id == id }
            update()
        }

    }

    fun imp(): String{
        return "Id: $id --> $nombre"
    }

    override fun toString(): String {

        return "Id: $id\nNombre: $nombre\nFecha: $fecha\nAteo: $ateo\nAltura: $altura\n" +
                "Personajes: $personajes\n"
    }
}
