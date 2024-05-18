package komat

import kotlin.math.exp
import kotlin.math.sqrt

class Vect() {
    var element = mutableListOf<Double>()

    constructor(vararg elem: Number) : this() {
        element.addAll(elem.map { it.toDouble() })
    }

    constructor(elem: MutableList<Double>) : this() {
        element = elem
    }

    fun copy(): Vect {
        val vect = Vect()
        vect.element.addAll(this.element)
        return vect
    }

    operator fun plus(vect: Vect): Vect {

        for (i: Int in 0..<element.size) {
                element[i] += vect.element[i]
        }

        return this
    }

    operator fun minus(vect: Vect): Vect {

        for (i: Int in 0..<element.size) {
            element[i] -= vect.element[i]
        }

        return this
    }

    operator fun times(vect: Vect): Vect {

        for (i: Int in 0..<element.size) {
            element[i] *= vect.element[i]
        }

        return this
    }

    fun dot(vect: Vect): Double {
        var scalar = 1.0

        element.forEachIndexed { index, it ->
            scalar += it * vect.element[index]
        }
        return scalar
    }

    fun l2norm(): Double {

        var sum = 0.0

        element.forEach {
            sum += (it * it)
        }

        return sqrt(sum)
    }

    fun softmax() : Vect{
        var denominator = 0.0

        element.forEach {
            denominator += exp(it)
        }

        for(i : Int in 0..<element.size){
            val numerator = exp(element[i])
            element[i] = numerator/denominator
        }

        return this
    }
}