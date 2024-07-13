package komat.space

import komat.Converter.Companion.toDoubleArray
import komat.Converter.Companion.toNumberArray
import komat.Element
import komat.type.Padding
import kotlin.math.*

//1-Dimensional
open class Vect() {

    var column: Int = 0
    var elements = DoubleArray(0)

    companion object {
        operator fun Double.times(vect: Vect): Vect {

            for (i: Int in 0..<vect.elements.size) {
                vect.elements[i] = this * vect.elements[i]
            }

            return vect
        }
    }

/*    constructor(vararg values: Double) : this() {
        this.elements = values.map { Element(it) }.toTypedArray().toDoubleArray()
        this.column = elements.size
    }*/

    constructor(vararg values: Number) : this() {
        this.elements = values.map { Element(it) }.toTypedArray().toNumberArray().toDoubleArray()
        this.column = elements.size
    }

/*    constructor(vararg elementss: Element) : this() {
        this.elements = arrayOf(*elementss).toDoubleArray()
    }*/

    constructor(elements: DoubleArray) : this() {
        this.column = elements.size
        this.elements = elements.copyOf()
    }

/*    constructor(vararg elem: Number) : this() {
        this.column = elem.size
        elements = DoubleArray(elem.size)
        elem.mapIndexed { index, number ->
            elements[index] = number.toDouble()
        }
    }*/

/*    constructor(elem: MutableList<Double>) : this() {
        elements = elem.toDoubleArray()
    }*/

    constructor(elem: Array<Element>) : this() {
        elements = elem.toDoubleArray()
        this.column = elements.size
    }

    operator fun get(index: Int): Double {
        return elements[index]
    }

    operator fun set(index: Int, value: Double) {
        elements[index] = value
    }

    operator fun plus(vect: Vect): Vect {

        for (i: Int in elements.indices) {
            elements[i] += vect.elements[i]
        }

        return this
    }

    operator fun minus(vect: Vect): Vect {

        for (i: Int in elements.indices) {
            elements[i] -= vect.elements[i]
        }

        return this
    }


    fun Double.times(): Vect {

        for (i: Int in elements.indices) {
            elements[i] = this * elements[i]
        }
        return this@Vect
    }

    open fun isOrthogonal(vect: Vect): Boolean {
        return (dot(vect) == 0.0)
    }

    open fun print() {
        print("[")
        for (i: Int in elements.indices - 1) {
            print("${elements[i]}, ")
        }
        print(elements.last())
        println("]")
    }

    open fun pad(padding: Padding, size: Int): Vect {
        return pad(padding, size, 0.0)
    }

    open fun pad(padding: Padding, size: Int, bias: Double): Vect {

        var newBias = bias

        when (padding) {
            Padding.ZERO -> {}
            Padding.MEAN -> newBias = mean()
            Padding.MIN -> newBias = min()
            Padding.MAX -> newBias = max()
            Padding.BIAS -> {}
        }

        val vect = Vect(DoubleArray(size) { newBias })
        return concat(vect)
    }

    fun flip(): Vect {
        elements.reverse()
        return this
    }

    fun concat(vect: Vect): Vect {
        return Vect(elements + vect.elements)
    }

    fun convolve(vect: Vect, stride: Int): Vect {
        if (column < vect.column + stride) {
            throw IllegalArgumentException("Size Invalid")
        }

        val size: Int = (column - vect.column) / stride + 1
        val convolutionVect = Vect(Array(size) { Element(0.0) })

        for (i in convolutionVect.elements.indices) {
            for (j in vect.elements.indices) {
                convolutionVect[i] += vect[j] * elements[i * stride + j]
            }
        }

        return convolutionVect
    }

    fun convolve(vect: Vect, stride: Int, padding: Padding, padSize: Int): Vect {
        val newVect = pad(padding, padSize)
        return convolve(vect, stride)
    }

    fun sum(): Double {
        var sum = 0.0
        for (value in elements) {
            sum += value
        }
        return sum
    }

    fun mean(): Double {
        return sum() / column
    }

    fun max(): Double {
        return elements.maxOrNull() ?: Double.NaN
    }

    fun min(): Double {
        return elements.minOrNull() ?: Double.NaN
    }

    fun roundUp(decimalPlaces: Int): Vect {
        val factor = 10.0.pow(decimalPlaces)
        for (i: Int in elements.indices) {
            elements[i] = round(elements[i] * factor) / factor
        }
        return this
    }

    fun dot(vect: Vect): Double {
        var scalar = 0.0

        elements.forEachIndexed { index, it ->
            scalar += it * vect.elements[index]
        }
        return scalar
    }

    fun l1norm(): Double {

        var sum = 0.0

        elements.forEach {
            sum += abs(it)
        }

        return sum
    }

    fun l2norm(): Double {

        var sum = 0.0

        elements.forEach {
            sum += (it * it)
        }

        return sqrt(sum)
    }

    fun l3norm(): Double {
        var sum = 0.0

        elements.forEach {
            sum += (it * it * it)
        }

        return cbrt(sum)
    }

    fun hat(): Vect {

        val l2norm = l2norm()

        for (i: Int in elements.indices) {
            elements[i] /= l2norm
        }

        return this
    }

    //projection from this to u
    fun project(u: Vect): Vect {
        return (u.dot(this) / u.dot(u)) * u
    }

    //gramSchmidt for b
    fun gramSchmidt(b: Vect): Vect {
        val vect = this - project(b)
        return vect
    }
}