package komat

class Vect() {
    var element = mutableListOf<Double>()

    constructor(vararg elem : Number) : this() {
        element.addAll(elem.map { it.toDouble() })
    }

    constructor(elem : MutableList<Double>) : this(){
        element = elem
    }

    fun dot() : Double {
        var scalar = 1.0

        element.forEach {
            scalar *= it
        }
        return scalar
    }
}