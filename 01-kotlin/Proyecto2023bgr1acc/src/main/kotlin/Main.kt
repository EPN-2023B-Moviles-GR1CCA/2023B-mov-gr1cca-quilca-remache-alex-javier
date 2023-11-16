import java.util.*

fun main(){
    //Variables inmutables (no se reasignan "=")
    val inmutable: String = "Adrian";
    //inmutable = "Vicente"; //esto no se puede hacer

    //Variables mutables (no se reasignan "=")
    var mutable: String = "Vicente";
    mutable = "Adrian";

    // variables primitiva
    val nombreProfesor: String = "Alex QUilca"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Switch (no existen dentro del lenguaje de kotlin) WHEN
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    //Classes Java
    val fechaNacimiento: Date = Date()
}