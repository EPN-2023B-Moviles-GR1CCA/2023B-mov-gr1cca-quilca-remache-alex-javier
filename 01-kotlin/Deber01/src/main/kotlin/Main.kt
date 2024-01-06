import java.util.*
import kotlin.collections.*

fun main() {

    while (true) {
        println()
        println("Seleccione una opción:")
        println("1. Gestionar Actor")
        println("2. Gestionar Personaje")
        println("3. Salir")
        print("*Opción: ")

        when (readLine()?.toInt()) {
            1 -> {
                while (true) {
                    println()
                    println("Seleccione una opción para el Actor:")
                    println("1. Crear actor")
                    println("2. Consultar actor")
                    println("3. Actualizar actor")
                    println("4. Eliminar actor")
                    println("5. Regresar al menú principal")
                    print("*Opción: ")

                    when (readLine()?.toInt()) {
                        1 -> {
                            println()
                            println("Ingrese los datos del actor: ")
                            print("Nombre: ")
                            val nombre: String = readLine().toString()
                            println("Fecha de Nacimiento: ")
                            print("Año: ")
                            val año : Int = readLine()?.toInt() ?: 0
                            print("Mes: ")
                            val mes : Int = readLine()?.toInt()?.minus(1) ?: 0
                            print("Dia: ")
                            val dia : Int = readLine()?.toInt() ?: 1
                            val calendario = Calendar.getInstance()
                            calendario.set(Calendar.YEAR, año)
                            calendario.set(Calendar.MONTH, mes)
                            calendario.set(Calendar.DAY_OF_MONTH, dia)
                            val fecha: Date = Date(calendario.timeInMillis)
                            print("¿Es Ateo? Escriba 'true' si es ateo, caso contrario escriba 'false': ")
                            val ateo: Boolean = readLine().toBoolean()
                            print("Ingrese la altura que tiene: ")
                            val altura: Float = readLine()?.toFloat() ?: 0.0f
                            val personajes: MutableList<Personaje?> = mutableListOf()
                            var continuar = true
                            while (continuar){
                                val personaje: Personaje?
                                println()
                                println("Que Quieres Hacer: ")
                                println("1. Agregar al actor un personaje de la base de datos")
                                println("2. Crear un nuevo personaje y agregarlo al actor")
                                print("*Opción: ")
                                when (readLine()?.toInt()){
                                    1 -> {
                                        val consulta = Personaje.read()
                                        if(consulta.isEmpty()){
                                            println()
                                            println("NO hay personajes en la base de datos!!")
                                        }else{
                                            println()
                                            println("Ingrese el id del personaje que quiere agregar al actor:")
                                            consulta.forEach { println(it.imp()) }
                                            print("*Opción: ")
                                            val id = readLine()?.toInt() ?: 0
                                            personaje = Personaje.readById(id)
                                            personajes.add(personaje)
                                            print("¿Quieres agregar otro personaje? (s/n)")
                                            val respuesta = readlnOrNull()
                                            continuar = respuesta?.lowercase() == "s"
                                        }
                                    }
                                    2 -> {
                                        println()
                                        println("Ingrese los datos del personaje: ")
                                        print("Nombre: ")
                                        val nombre: String = readLine().toString()
                                        println("Fecha de creación: ")
                                        print("Año: ")
                                        val año : Int = readLine()?.toInt() ?: 0
                                        print("Mes: ")
                                        val mes : Int = readLine()?.toInt()?.minus(1) ?: 0
                                        print("Dia: ")
                                        val dia : Int = readLine()?.toInt() ?: 1
                                        val calendario = Calendar.getInstance()
                                        calendario.set(Calendar.YEAR, año)
                                        calendario.set(Calendar.MONTH, mes)
                                        calendario.set(Calendar.DAY_OF_MONTH, dia)
                                        val fecha: Date = Date(calendario.timeInMillis)
                                        print("Ingrese la película: ")
                                        val pelicula: String = readLine().toString()
                                        print("¿Es famoso? Escriba 'true' si es famoso, caso contrario escriba 'false': ")
                                        val famoso: Boolean = readLine().toBoolean()
                                        println()
                                        personaje = Personaje.create(nombre, fecha, pelicula, famoso)
                                        personajes.add(personaje)
                                        print("¿Quieres agregar otro personaje? (s/n)")
                                        val respuesta = readlnOrNull()
                                        continuar = respuesta?.lowercase() == "s"
                                    }
                                }
                            }
                            println()
                            val actor: Actor = Actor.create(nombre, fecha, ateo, altura, personajes)
                            print(actor)
                        }
                        2 -> {
                            val consulta = Actor.read()
                            if(consulta.isEmpty()){
                                println()
                                println("NO hay actores!!")
                            }else{
                                println()
                                println("Ingrese el id del actor que quiere consultar:")
                                consulta.forEach { println(it.imp()) }
                                print("*Opción: ")
                                val id = readLine()?.toInt() ?: 0
                                val actor = Actor.readById(id)
                                println()
                                print(actor)
                            }
                        }
                        3 -> {
                            var consulta = Actor.read()
                            if(consulta.isEmpty()){
                                println()
                                println("NO hay actores para actualizar!!")
                            }else{
                                println()
                                println("Elija el Id del actor que desea actualizar")
                                consulta.forEach { println(it.imp()) }
                                print("*Opción: ")
                                val id = readLine()?.toInt() ?:0
                                val index = Actor.findIndex(id)
                                println("¿Que dato desea actualizar?")
                                println("1. Nombre")
                                println("2. Fecha")
                                println("3. ¿Es Ateo?")
                                println("4. Altura")
                                println("5. Personajes")
                                print("*Opción: ")
                                when (readLine()?.toInt()){
                                    1 -> {
                                        print("Ingrese el nuevo Nombre: ")
                                        Actor.actores[index].nombre = readLine().toString()
                                        print(Actor.actores[index])
                                        Actor.update()
                                    }
                                    2 -> {
                                        println("Ingrese la nueva Fecha")
                                        print("Año: ")
                                        val año : Int = readLine()?.toInt() ?: 0
                                        print("Mes: ")
                                        val mes : Int = readLine()?.toInt()?.minus(1) ?: 0
                                        print("Dia: ")
                                        val dia : Int = readLine()?.toInt() ?: 1
                                        val calendario = Calendar.getInstance()
                                        calendario.set(Calendar.YEAR, año)
                                        calendario.set(Calendar.MONTH, mes)
                                        calendario.set(Calendar.DAY_OF_MONTH, dia)
                                        Actor.actores[index].fecha = Date(calendario.timeInMillis)
                                        println()
                                        print(Actor.actores[index])
                                        Actor.update()
                                    }
                                    3 -> {
                                        print("¿Es Ateo? Escriba 'true' si es ateo, caso contrario escriba 'false': ")
                                        Actor.actores[index].ateo = readLine().toBoolean()
                                        print(Actor.actores[index])
                                        Actor.update()
                                    }
                                    4 -> {
                                        print("Ingrese la altura que posee: ")
                                        Actor.actores[index].altura = readLine()?.toFloat() ?: 0.0f
                                        print(Actor.actores[index])
                                        Actor.update()
                                    }
                                    5 -> {
                                        val consulta = Actor.readById(id)?.personajes

                                        if(consulta.isNullOrEmpty()){
                                            println()
                                            println("NO hay personajes!!")
                                        }else{
                                            println()
                                            println("Ingrese el id del personaje que quiere editar:")
                                            consulta.forEach { println(it?.imp())}
                                            print("*Opción: ")
                                            val id = readLine()?.toInt() ?:0
                                            val index = Personaje.findIndex(id)
                                            val index2 = Actor.findIndexPersonajes(id, consulta)

                                            //println("index: $index")
                                            //println("index2: $index2")

                                            println("¿Que dato desea actualizar?")
                                            println("1. Nombre")
                                            println("2. Fecha de creación")
                                            println("3. Pelicula")
                                            println("4. ¿Es famoso?")
                                            print("*Opción: ")
                                            when (readLine()?.toInt()){
                                                1 -> {
                                                    print("Ingrese el nuevo Nombre: ")
                                                    var nombre = readLine().toString()
                                                    Personaje.personajes[index].nombre = nombre
                                                    consulta[index2]?.nombre = nombre
                                                    Personaje.update()
                                                    Actor.update()
                                                }
                                                2 -> {
                                                    println("Ingrese la nueva Fecha de creación")
                                                    print("Año: ")
                                                    val año : Int = readLine()?.toInt() ?: 0
                                                    print("Mes: ")
                                                    val mes : Int = readLine()?.toInt()?.minus(1) ?: 0
                                                    print("Dia: ")
                                                    val dia : Int = readLine()?.toInt() ?: 1
                                                    val calendario = Calendar.getInstance()
                                                    calendario.set(Calendar.YEAR, año)
                                                    calendario.set(Calendar.MONTH, mes)
                                                    calendario.set(Calendar.DAY_OF_MONTH, dia)
                                                    Personaje.personajes[index].fecha = Date(calendario.timeInMillis)
                                                    consulta[index2]?.fecha = Date(calendario.timeInMillis)
                                                    Personaje.update()
                                                    Actor.update()
                                                }
                                                3 -> {
                                                    print("Ingrese la nueva película: ")
                                                    val pelicula = readLine().toString()
                                                    Personaje.personajes[index].pelicula = pelicula
                                                    consulta[index2]?.pelicula = pelicula
                                                    Personaje.update()
                                                    Actor.update()
                                                }
                                                4 -> {
                                                    print("¿Es famoso? Escriba 'true' si es famoso, caso contrario escriba 'false': ")
                                                    val famoso = readLine().toBoolean()
                                                    Personaje.personajes[index].famoso = famoso
                                                    consulta[index2]?.famoso = famoso
                                                    Personaje.update()
                                                    Actor.update()
                                                }
                                            }
                                        }
                                        print(Actor.actores[index])
                                    }
                                }
                            }
                        }
                        4 -> {
                            val consulta = Actor.read()
                            if(consulta.isEmpty()){
                                println()
                                println("NO hay personajes para eliminar!!")
                            }else{
                                println()
                                println("Ingrese el id del actor que desea eliminar:")
                                consulta.forEach { println(it.imp()) }
                                print("*Opción: ")
                                val id : Int = readLine()?.toInt() ?: 0
                                val index = Actor.findIndex(id)
                                val nombre_actor = Actor.actores[index].nombre
                                println("Actor --> {Id: $id | Nombre: $nombre_actor} ha sido eliminado")
                                Actor.delete(id)
                            }
                        }
                        5 -> break
                    }
                }
            }
            2 -> {
                while (true) {
                    println()
                    println("Seleccione una opción para el Personaje:")
                    println("1. Crear personaje")
                    println("2. Consultar personaje")
                    println("3. Actualizar personaje")
                    println("4. Eliminar personaje")
                    println("5. Regresar al menú principal")
                    print("*Opción: ")

                    when (readLine()?.toInt()) {
                        1 -> {
                            println()
                            println("Ingrese los datos del personaje: ")
                            print("Nombre: ")
                            val nombre: String = readLine().toString()
                            println("Fecha de creación: ")
                            print("Año: ")
                            val año : Int = readLine()?.toInt() ?: 0
                            print("Mes: ")
                            val mes : Int = readLine()?.toInt()?.minus(1) ?: 0
                            print("Dia: ")
                            val dia : Int = readLine()?.toInt() ?: 1
                            val calendario = Calendar.getInstance()
                            calendario.set(Calendar.YEAR, año)
                            calendario.set(Calendar.MONTH, mes)
                            calendario.set(Calendar.DAY_OF_MONTH, dia)
                            val fecha: Date = Date(calendario.timeInMillis)
                            print("Ingrese la película: ")
                            val pelicula: String = readLine().toString()
                            print("¿Es famoso? Escriba 'true' si es famoso, caso contrario escriba 'false': ")
                            val famoso: Boolean = readLine().toBoolean()
                            println()
                            val personaje: Personaje = Personaje.create(nombre, fecha, pelicula, famoso)
                            print(personaje)
                        }
                        2 -> {
                            var consulta = Personaje.read()
                            if(consulta.isEmpty()){
                                println()
                                println("NO hay personajes!!")
                            }else{
                                println()
                                println("Ingrese el id del personaje que quiere consultar:")
                                consulta.forEach { println(it.imp()) }
                                print("*Opción: ")
                                val id = readLine()?.toInt() ?: 0
                                val personaje = Personaje.readById(id)
                                println()
                                print(personaje)
                            }

                        }
                        3 -> {
                            var consulta = Personaje.read()

                            if(consulta.isEmpty()){
                                println()
                                println("NO hay personajes para Actualizar!!")
                            }else{
                                println()
                                println("Elija el Id del personaje que desea actualizar")
                                consulta.forEach { println(it.imp()) }
                                print("*Opción: ")
                                val id = readLine()?.toInt() ?: 0
                                val index = Personaje.findIndex(id)

                                println("¿Que dato desea actualizar?")
                                println("1. Nombre")
                                println("2. Fecha de creación")
                                println("3. Pelicula")
                                println("4. ¿Es famoso?")
                                print("*Opción: ")
                                when (readLine()?.toInt()){
                                    1 -> {
                                        print("Ingrese el nuevo Nombre: ")
                                        Personaje.personajes[index].nombre = readLine().toString()
                                        print(Personaje.personajes[index])
                                        Personaje.update()
                                    }
                                    2 -> {
                                        println("Ingrese la nueva Fecha de creación")
                                        print("Año: ")
                                        val año : Int = readLine()?.toInt() ?: 0
                                        print("Mes: ")
                                        val mes : Int = readLine()?.toInt()?.minus(1) ?: 0
                                        print("Dia: ")
                                        val dia : Int = readLine()?.toInt() ?: 1
                                        val calendario = Calendar.getInstance()
                                        calendario.set(Calendar.YEAR, año)
                                        calendario.set(Calendar.MONTH, mes)
                                        calendario.set(Calendar.DAY_OF_MONTH, dia)
                                        Personaje.personajes[index].fecha = Date(calendario.timeInMillis)
                                        print(Personaje.personajes[index])
                                        Personaje.update()
                                    }
                                    3 -> {
                                        print("Ingrese la nueva película: ")
                                        Personaje.personajes[index].pelicula = readLine().toString()
                                        print(Personaje.personajes[index])
                                        Personaje.update()
                                    }
                                    4 -> {
                                        print("¿Es famoso? Escriba 'true' si es famoso, caso contrario escriba 'false': ")
                                        Personaje.personajes[index].famoso = readLine().toBoolean()
                                        print(Personaje.personajes[index])
                                        Personaje.update()
                                        println()
                                    }
                                }
                            }
                        }
                        4 -> {
                            val consulta = Personaje.read()
                            if(consulta.isEmpty()){
                                println()
                                println("NO hay personajes para eliminar!!")
                            }else{
                                println()
                                println("Ingrese el id del personaje que desea eliminar:")
                                consulta.forEach { println(it.imp()) }
                                print("*Opción: ")
                                val id : Int = readLine()?.toInt() ?: 0
                                val index = Personaje.findIndex(id)
                                val nombre_personaje = Personaje.personajes[index].nombre
                                println("Personaje --> {Id: $id | Nombre: $nombre_personaje} ha sido eliminado")
                                Personaje.delete(id)
                            }
                        }
                        5 -> break
                    }
                }
            }
            3 -> return
        }
    }
}