package komat

class Vect(init: DoubleArray) {
    var element = mutableListOf<Double>()

    init {
        element.addAll(init.asList())
    }
}