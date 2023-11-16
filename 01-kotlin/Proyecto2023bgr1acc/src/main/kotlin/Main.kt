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


    //ejemplo calculo de sueldo
    //parmetos nombrados: podemos enviar los parametros con el nombre
    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)

    calcularSueldo(sueldo = 10.00)
    calcularSueldo(sueldo = 10.00, tasa = 15.00)
    calcularSueldo(sueldo = 10.00, tasa = 12.00, bonoEspecial = 20.00)

    calcularSueldo(sueldo = 10.00, bonoEspecial = 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)

    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)





}

//Funciones
//Unit --> void en kotlin
//Double? --> ayuda al programado que la variable puede ser null, a este tipo de variable se lo llama nullable
// se puede hacer nullable a cualquier tipo de variable
// void -> Unit
fun imprimirNombre(nombre: String): Unit{
    // "Nombre: " + variable + " bienvenido";
    println("Nombre : ${nombre}")
}
fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, // Opcion null -> nullable
): Double{
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo * (100/tasa ) + bonoEspecial
    }
}

