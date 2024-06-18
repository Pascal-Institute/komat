package komat.space

import komat.type.Padding
import kotlin.math.*

open class Vect() {

    var element = DoubleArray(0)

    companion object {
        operator fun Double.times(vect: Vect): Vect {

            for (i: Int in 0..<vect.element.size) {
                vect.element[i] = this * vect.element[i]
            }

            return vect
        }
    }

    constructor(element : DoubleArray) : this(){
        this.element = element.copyOf()
    }

    constructor(vararg elem: Number) : this() {
        element = DoubleArray(elem.size)
        elem.mapIndexed { index, number ->
            element[index] = number.toDouble()
        }
    }

    constructor(elem: MutableList<Double>) : this() {
        element = elem.toDoubleArray()
    }

    operator fun get(index: Int): Double {
        return element[index]
    }

    operator fun set(index: Int, value: Double) {
        element[index] = value
    }

    operator fun plus(vect: Vect): Vect {

        for (i: Int in element.indices) {
                element[i] += vect.element[i]
        }

        return this
    }

    operator fun minus(vect: Vect): Vect {

        for (i: Int in element.indices) {
            element[i] -= vect.element[i]
        }

        return this
    }

    fun Double.times() : Vect {

        for (i: Int in element.indices) {
            element[i] = this * element[i]
        }
        return this@Vect
    }

    open fun isOrthogonal(vect: Vect) : Boolean {
        return (dot(vect) == 0.0)
    }

    open fun print(){
        print("[")
        for (i : Int in element.indices-1){
            print("${element[i]}, ")
        }
        print(element.last())
        println("]")
    }

    fun pad(padding : Padding, size : Int ) : Vect{

        var bias = 0.0

        when(padding){
            Padding.ZERO ->{}
            Padding.MEAN -> bias = mean()
            Padding.MIN -> bias = min()
            Padding.MAX -> bias = max()
        }

        val vect = Vect(DoubleArray(size){bias})
        return concat(vect)
    }

    fun concat(vect : Vect) : Vect{
        return Vect(element + vect.element)
    }

    fun convolve(vect : Vect, stride : Int) : Vect{
        if(element.size < vect.element.size + stride){
            throw IllegalArgumentException("Size Invalid")
        }

        val size : Int = (element.size - vect.element.size) / stride + 1
        val convolutionVect = Vect(size)

        for(i in convolutionVect.element.indices){
            for(j in vect.element.indices){
                convolutionVect[i] += vect[j] * element[i * stride + j]
            }
        }

        return convolutionVect
    }

    fun convolve(vect : Vect, stride : Int, padding: Padding, padSize: Int) : Vect {
        val newVect = pad(padding, padSize)
        return convolve(vect, stride)
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

    fun roundUp(decimalPlaces : Int) : Vect{
        val factor = 10.0.pow(decimalPlaces)
        for(i : Int in element.indices){
            element[i] = round(element[i] * factor) / factor
        }
        return this
    }

    fun dot(vect: Vect): Double {
        var scalar = 0.0

        element.forEachIndexed { index, it ->
            scalar += it * vect.element[index]
        }
        return scalar
    }

    fun l1norm() : Double{

        var sum = 0.0

        element.forEach {
            sum += abs(it)
        }

        return sum
    }

    fun l2norm(): Double {

        var sum = 0.0

        element.forEach {
            sum += (it * it)
        }

        return sqrt(sum)
    }

    fun l3norm() : Double{
        var sum = 0.0

        element.forEach {
            sum += (it * it * it)
        }

        return cbrt(sum)
    }

    fun sigmoid() : Vect {
        element.forEachIndexed{ index, value->
            element[index] = 1 / (1 + exp(-value))
        }
        return this
    }

    fun softmax() : Vect {
        var denominator = 0.0

        element.forEach {
            denominator += exp(it)
        }

        for(i : Int in element.indices){
            val numerator = exp(element[i])
            element[i] = numerator/denominator
        }

        return this
    }

    //projection from this to u
    fun project(u : Vect) : Vect {
        return (u.dot(this) / u.dot(u))*u
    }

    //gramSchmidt for b
    fun gramSchmidt(b : Vect) : Vect {
        val vect = this - project(b)
        return vect
    }
}