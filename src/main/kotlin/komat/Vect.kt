package komat

import kotlin.math.exp
import kotlin.math.sqrt

class Vect() {
    companion object {
        operator fun Double.times(vect: Vect): Vect {

            for (i: Int in 0..<vect.element.size) {
                vect.element[i] = this * vect.element[i]
            }

            return vect
        }
    }
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

    fun Double.times() : Vect{

        val vect = copy()

        for (i: Int in 0..<element.size) {
            element[i] = this * element[i]
        }
        return vect
    }

    fun isOrthogonal(vect: Vect) : Boolean {
        return (dot(vect) == 0.0)
    }

    fun dot(vect: Vect): Double {
        var scalar = 0.0

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

    //projection from this to u
    fun project(u : Vect) : Vect{
        return (u.dot(this) / u.dot(u))*u
    }

    fun gramSchmidt(b : Vect) : Vect{
        val vect = this - project(b)

        return vect
    }
}