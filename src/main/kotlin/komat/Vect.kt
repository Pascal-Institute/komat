package komat

import kotlin.math.exp
import kotlin.math.sqrt

open class Vect() {
    companion object {
        operator fun Double.times(vect: Vect): Vect {

            for (i: Int in 0..<vect.element.size) {
                vect.element[i] = this * vect.element[i]
            }

            return vect
        }
    }

    var element = mutableListOf<Double>()

    operator fun get(index: Int): Double {
        return element[index]
    }

    operator fun set(index: Int, value: Double) {
        element[index] = value
    }

    constructor(vararg elem: Number) : this() {
        element.addAll(elem.map { it.toDouble() })
    }

    constructor(elem: MutableList<Double>) : this() {
        element = elem
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

        for (i: Int in 0..<element.size) {
            element[i] = this * element[i]
        }
        return this@Vect
    }

    open fun isOrthogonal(vect: Vect) : Boolean {
        return (dot(vect) == 0.0)
    }

    fun sum(): Double {
        var sum = 0.0
        for (value in element) {
            sum += value
        }
        return sum
    }

    fun mean(): Double {
        return sum() / (element.size)
    }

    fun max(): Double {
        return element.maxOrNull() ?: Double.NaN
    }

    fun min(): Double {
        return element.minOrNull() ?: Double.NaN
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

    //gramSchmidt for b
    fun gramSchmidt(b : Vect) : Vect{
        val vect = this - project(b)
        return vect
    }
}