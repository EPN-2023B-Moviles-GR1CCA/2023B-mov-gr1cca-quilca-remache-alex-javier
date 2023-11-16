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

    //Instanciar la clase suma
    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)

}


//Clases
//COnstructor primario se pone despues de poner la clase
//init --> bloque de codigo del constructor primario

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){ // Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}
abstract class Numeros( // Constructor PRIMARIO
    // Ejemplo:
    // unoProp: Int, // (Parametro (sin modificador de acceso))
    // private var uno: Int, // Propiedad Publica Clase numeros.uno
    // var uno: Int, // Propiedad de la clase (por defecto es PUBLIC)
    // public var uno: Int,
    // Propiedad de la clase protected numeros.numeroUno
    protected val numeroUno: Int,
    // Propiedad de la clase protected numeros.numeroDos
    protected val numeroDos: Int,
){
    // var cedula: string = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)
    init { // Bloque codigo constructor primario
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno; numeroDos; // sin el "this", es lo mismo
        println("Inicializando")
    }
}

class Suma( // Constructor Primario Suma
    uno: Int, // Parametro
    dos: Int // Parametro
): Numeros(uno, dos) { // <- Constructor del Padre
    init { // Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor(//  Segundo constructor
        uno: Int?, // parametros
        dos: Int // parametros
    ) : this(  // llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    ) { // si necesitamos bloque de codigo lo usamos
        numeroUno;
    }

    constructor(//  tercer constructor
        uno: Int, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        uno,
        if (dos == null) 0 else uno
    )
    // Si no lo necesitamos al bloque de codigo "{}" lo omitimos
}

//FuncionesSS
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

