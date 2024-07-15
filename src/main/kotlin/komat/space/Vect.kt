package komat.space

import komat.Element
import komat.Utility.Companion.eq
import komat.type.ElementType
import komat.type.Padding
import kotlin.math.*

//1-Dimensional
open class Vect() {

    var elementType = ElementType.ANY
    var column: Int = 0
    var elements = Array(0) { Element(0.0) }

    companion object {

        operator fun Double.times(vect: Vect): Vect {
            return Element(this) * vect
        }

        operator fun Element.times(vect: Vect): Vect {

            for (i: Int in 0..<vect.column) {
                vect[i] = this.getValue() as Double * (vect[i].getValue() as Double)
            }

            return vect
        }
    }

    constructor(values: Array<Element>) : this() {
        elements = values
        this.column = elements.size
    }

    constructor(vararg values: Number) : this() {
        this.elementType = ElementType.DOUBLE
        this.elements = values.map { Element(it) }.toTypedArray()
        this.column = elements.size
    }

    constructor(vararg values: Double) : this() {
        this.elementType = ElementType.DOUBLE
        this.elements = values.map { Element(it) }.toTypedArray()
        this.column = this.elements.size
    }

    constructor(vararg values: Byte) : this() {
        this.elementType = ElementType.BYTE
        this.elements = values.map { Element(it) }.toTypedArray()
        this.column = this.elements.size
    }

    operator fun get(index: Int): Element {
        return elements[index]
    }

    operator fun set(index: Int, value: Element) {
        elements[index] = value
    }

    operator fun set(index: Int, value: Number) {
        elements[index] = Element(value)
    }

    operator fun set(index: Int, value: Double) {
        elements[index] = Element(value)
    }

    operator fun set(index: Int, value: Byte) {
        elements[index] = Element(value)
    }

    operator fun plus(vect: Vect): Vect {

        for (i: Int in elements.indices) {
            this[i] += vect[i]
        }

        return this
    }

    operator fun minus(vect: Vect): Vect {

        for (i: Int in elements.indices) {
            this[i] -= vect[i]
        }

        return this
    }

    open fun isOrthogonal(vect: Vect): Boolean {
        return dot(vect) eq 0.0
    }

    open fun print() {
        print("[")
        for (i: Int in elements.indices - 1) {
            print("${this[i].getValue()}, ")
        }
        print(elements.last().getValue())
        println("]")
    }

    open fun pad(padding: Padding, size: Int): Vect {
        return pad(padding, size, 0.0)
    }

    open fun pad(padding: Padding, size: Int, bias: Any): Vect {

        var newBias = Element(0.0)

        when(elementType){
            ElementType.DOUBLE ->{ newBias = Element(bias as Double) }
            ElementType.BYTE->{ newBias = Element(bias as Byte) }
            else->{}
        }

        when (padding) {
            Padding.ZERO -> {}
            Padding.MEAN -> newBias = mean()
            Padding.MIN -> newBias = min()
            Padding.MAX -> newBias = max()
            Padding.BIAS -> {}
        }

        val vect = Vect(Array(size) { newBias })
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
        if (elements.size < vect.elements.size + stride) {
            throw IllegalArgumentException("Size Invalid")
        }

        val size: Int = (elements.size - vect.elements.size) / stride + 1
        var convolutionVect = Vect(Array(size) { Element(0.0) })

        when(elementType){
            ElementType.DOUBLE ->{}
            ElementType.BYTE->{ convolutionVect = Vect(Array(size) { Element((0).toByte())})}
            else->{}
        }


        for (i in convolutionVect.elements.indices) {
            for (j in vect.elements.indices) {
                convolutionVect[i] += vect[j] * this[i * stride + j]
            }
        }

        return convolutionVect
    }

    fun convolve(vect: Vect, stride: Int, padding: Padding, padSize: Int): Vect {
        val newVect = pad(padding, padSize)
        return convolve(newVect, stride)
    }

    fun sum(): Element {
        var sum = Element(0.0)

        when(elementType){
            ElementType.DOUBLE ->{}
            ElementType.BYTE->{ sum = Element(0)}
            else->{}
        }

        for (value in elements) {
            sum += value
        }
        return sum
    }

    //TODO Need to fix for Byte
    fun mean(): Element {
        return sum() / Element(elements.size.toDouble())
    }

    fun max(): Element {

        var max = elements.first()

        when(elementType){
            ElementType.DOUBLE ->{
                elements.forEach {
                    if ((it.getValue() as Double) > max.getValue() as Double) {
                        max = it
                    }
                }
            }
            ElementType.BYTE->{
                elements.forEach {
                    if ((it.getValue() as Byte) > max.getValue() as Byte) {
                        max = it
                    }
                }
            }
            else->{}
        }

        return max
    }

    fun min(): Element {

        var min = elements.first()

        when(elementType){
            ElementType.DOUBLE ->{
                elements.forEach {
                    if ((it.getValue() as Double) < min.getValue() as Double) {
                        min = it
                    }
                }
            }
            ElementType.BYTE->{
                elements.forEach {
                    if ((it.getValue() as Byte) < min.getValue() as Byte) {
                        min = it
                    }
                }
            }
            else->{}
        }

        return min
    }

    //TODO Need to fix for Byte
    fun roundUp(decimalPlaces: Int): Vect {
        val factor = Element(10.0.pow(decimalPlaces))
        for (i: Int in elements.indices) {
            this[i] = Element(round((this[i] as Double) * (factor as Double))) / factor
        }
        return this
    }

    fun dot(vect: Vect): Element {
        var scalar = Element(0.0)

        when(elementType){
            ElementType.DOUBLE ->{}
            ElementType.BYTE->{ scalar = Element((0).toByte())}
            else->{}
        }

        elements.forEachIndexed { index, it ->
            scalar += it * vect[index]
        }
        return scalar
    }

    //TODO Need to fix for Byte
    fun l1norm(): Element {

        var sum = Element(0.0)

        elements.forEach {
            sum += Element(abs(it as Double))
        }

        return sum
    }

    //TODO Need to fix for Byte
    fun l2norm(): Element {

        var sum = Element(0.0)

        elements.forEach {
            sum += (it * it)
        }

        return Element(sqrt(sum as Double))
    }

    //TODO Need to fix for Byte
    fun l3norm(): Element {
        var sum = Element(0.0)

        elements.forEach {
            sum += (it * it * it)
        }

        return Element(cbrt(sum as Double))
    }

    fun hat(): Vect {

        val l2norm = l2norm()

        for (i: Int in elements.indices) {
            this[i] = this[i] / l2norm
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