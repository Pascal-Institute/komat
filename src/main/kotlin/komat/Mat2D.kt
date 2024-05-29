package komat

import kotlin.math.pow

class Mat2D : Vect {

    var row: Int = 0
    var column: Int = 0

    constructor()

    constructor(row: Int, column: Int){
        this.row = row
        this.column = column
        element.addAll(MutableList(row * column){0.0})
    }

    fun v(elements: MutableList<Double>) {
        if(column == 0){
            column = elements.size
        }
        element.addAll(elements)
        row++
    }

    fun v(vararg elements: Number) {
        if(column == 0){
            column = elements.size
        }
        element.addAll(elements.map(Number::toDouble).toMutableList())
        row++
    }

    private fun isValid(srcColumn: Int, dstRow: Int): Boolean {
        return (srcColumn == dstRow)
    }

    private fun isValid(srcRow: Int, srcColumn: Int, dstRow: Int, dstColumn: Int): Boolean {
        return (srcRow == dstRow && srcColumn == dstColumn)
    }

    fun isSquare(): Boolean {
        return (row == column)
    }

    operator fun get(i: Int, j: Int): Double {
        if (i >= row || j >= column) {
            throw IndexOutOfBoundsException("Index out of bounds: [$i, $j]")
        }
        return element[i * column + j]
    }

    operator fun set(i: Int, j: Int, value: Number) {
        if (i >= row || j >= column) {
            throw IndexOutOfBoundsException("Index out of bounds: [$i, $j]")
        }
        element[i * column + j] = value.toDouble()
    }

    operator fun times(mat: Mat2D): Mat2D {

        if (!isValid(column, mat.row)) {
            throw IllegalArgumentException("Invalid matrix: A's column & B's row must be the same")
        }

        val newMat = Mat2D(row, mat.column)

        for (i: Int in 0..<newMat.row) {
            for (j: Int in 0..<newMat.column) {
                for (k: Int in 0..<column) {
                    newMat.element[i * newMat.column  + j] += element[i* column + k] * mat.element[k* mat.column + j]
                }
            }
        }

        return newMat
    }

    fun print(){
        for (i : Int in 0..<row) {
            print("[")
            for(j : Int in 0..<column){
                try {
                    print(element[i * column + j])
                }catch (e : Exception){
                    println()
                }

                when {
                    (j + 1) % column == 0 -> {

                    }
                    else -> {
                        print(", ")
                    }
                }

            }
            println("]")
        }
    }

    fun copy(): Mat2D {
        val mat = this
        return mat
    }

    fun transpose(): Mat2D {

        val newMat = Mat2D(this.column, this.row)

        for (i in 0..<newMat.row) {
            for (j in 0..<newMat.column) {
                newMat[i,j] = this[j,i]
            }
        }

        this.row = newMat.row
        this.column = newMat.column
        this.element = newMat.element

        return this
    }

    fun removeRowAt(index: Int): Mat2D {
        element.subList(index * column, index * (column + 1)).clear()
        row -= 1
        return this
    }

    fun removeColumnAt(index: Int): Mat2D {
        return transpose().removeRowAt(index).transpose()
    }

    fun removeAt(row: Int, column: Int): Mat2D {
        return removeRowAt(row).removeColumnAt(column)
    }
}