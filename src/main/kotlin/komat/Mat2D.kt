package komat

import komat.Generator.Companion.e
import komat.Mat.Companion.times
import kotlin.math.pow

class Mat2D : Vect {

    companion object {
        operator fun Double.times(mat: Mat2D): Mat2D {
            mat.element.replaceAll { this * it }
            return mat
        }
    }

    var row: Int = 0
    var column: Int = 0

    constructor()

    constructor(row: Int, column: Int) {
        this.row = row
        this.column = column
        element.addAll(MutableList(row * column) { 0.0 })
    }

    fun v(elements: MutableList<Double>) {
        if (column == 0) {
            column = elements.size
        }
        element.addAll(elements)
        row++
    }

    fun v(vararg elements: Number) {
        if (column == 0) {
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

    fun isInvertible(): Boolean {
        return (det() != 0.0)
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
                    newMat.element[i * newMat.column + j] += element[i * column + k] * mat.element[k * mat.column + j]
                }
            }
        }

        return newMat
    }

    fun print() {
        for (i: Int in 0..<row) {
            print("[")
            for (j: Int in 0..<column) {

                print(this[i, j])

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
        val element = mutableListOf<Double>()
        for (i in 0..<row) {
            for (j in 0..<column) {
                element.add(this[i, j])
            }
        }

        val copyMat2D = Mat2D(row, column)
        copyMat2D.element = element
        return copyMat2D
    }

    fun transpose(): Mat2D {

        val newMat = Mat2D(this.column, this.row)

        for (i in 0..<newMat.row) {
            for (j in 0..<newMat.column) {
                newMat[i, j] = this[j, i]
            }
        }

        this.row = newMat.row
        this.column = newMat.column
        this.element = newMat.element

        return this
    }

    fun removeRowAt(index: Int): Mat2D {

        for (i: Int in 0..<column) {
            element.removeAt(index * column)
        }

        row -= 1
        return this
    }

    fun removeColumnAt(index: Int): Mat2D {
        for (i: Int in 0..<row) {
            element.removeAt(index + (column - 1) * i)
        }

        column -= 1
        return this
    }

    fun removeAt(row: Int, column: Int): Mat2D {
        return removeRowAt(row).removeColumnAt(column)
    }

    fun exchangeRow(src: Int, dst: Int): Mat2D {

        val srcRow = mutableListOf<Double>()
        srcRow.addAll(element.subList(src * column, (src + 1) * column))

        for (i: Int in 0..<column) {
            this[src, +i] = this[dst, +i]
        }

        for (i: Int in 0..<column) {
            this[dst, +i] = srcRow[i]
        }

        return this
    }

    fun exchangeColumn(src: Int, dst: Int): Mat2D {

        val srcRow = mutableListOf<Double>()

        for (i: Int in 0..<row) {
            srcRow.add(this[i * row + src])
        }

        for (i: Int in 0..<row) {
            this[i * row + src] = this[i * row + dst]
        }

        for (i: Int in 0..<row) {
            this[i * row + dst] = srcRow[i]
        }
        return this
    }

    private fun cleanMinusZero(): Mat2D {

        for (i: Int in 0..<row) {
            for (j: Int in 0..<column) {
                if (this[i, j] == -0.0) {
                    this[i, j] = 0.0
                }
            }
        }
        return this
    }


    /*
   * ERO : Elementary Row Operation
   * */
    fun ero1(src: Int, dst: Int): Mat2D {
        return exchangeRow(src, dst)
    }

    fun ero2(scale: Double, dst: Int): Mat2D {

        for (i: Int in 0..<column) {
            this[dst, i] *= scale
        }

        return this
    }

    fun ero3(scale: Double, src: Int, dst: Int): Mat2D {

        val srcRow = copy().ero2(scale, src).element.subList(src * column, (src + 1) * column)

        for (i: Int in 0..<this.column) {
            this[dst, i] += srcRow[i]
        }

        return this
    }

    private fun getLeadingEntry(): Map<Int, Int> {
        val leadingEntry = mutableMapOf<Int, Int>()

        for (i: Int in 0..<row) {
            for (j: Int in 0..<column) {
                if (this[i, j] != 0.0) {
                    leadingEntry[i] = j
                    break
                }
            }
        }
        return leadingEntry.toList().sortedBy { (_, value) -> value }.toMap()
    }

    private fun getLeadingEntry(mat: Mat2D): Map<Int, Int> {
        val leadingEntry = mutableMapOf<Int, Int>()

        for (i: Int in 0..<mat.row) {
            for (j: Int in 0..<mat.column) {
                if (this[i,j] != 0.0) {
                    leadingEntry[i] = j
                    break
                }
            }
        }
        return leadingEntry.toList().sortedBy { (_, value) -> value }.toMap()
    }

    fun adjugate(): Mat2D {

        if (!this.isSquare()) {
            throw IllegalArgumentException("Invalid matrix: matrix must be square")
        }

        val mat2D = Mat2D(row, column)

        for (i: Int in 0..<row) {
            for (j: Int in 0..<column) {
                mat2D[i, j] = cofactor(i, j)
            }
        }

        return mat2D.transpose()
    }

    fun cofactor(row: Int, column: Int): Double {
        if (!this.isSquare()) {
            throw IllegalArgumentException("Invalid matrix: matrix must be square")
        }

        return (-1).toDouble().pow(row + column) * copy().removeAt(row, column).det()
    }

    fun det(): Double {

        var determinant = 0.0

        if (row != column) {
            throw IllegalArgumentException("Invalid matrix: Rows must have the same length")
        }

        if (row == 2 && column == 2) {
            return (this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0])
        }

        for (j: Int in 0..<column) {
            determinant += cofactor(0, j) * this[0, j]
        }

        return determinant
    }

    fun inverse(): Mat2D {
        if (!this.isInvertible()) {
            throw IllegalArgumentException("Invalid matrix: matrix is not invertible")
        }

        val mat = (1.0 / det()) * adjugate()

        return mat.cleanMinusZero()
    }


    /*
* Row Echelon Form
*
* Prop 1. If a column contains a leading entry then all entries below that leading entry are zero.
* Prop 2. In any two consecutive non-zero rows, the leading entry in the upper row occurs to the left of the leading entry in the lower row.
* Prop 3. All rows which consist entirely of zeroes appear at the bottom of the matrix.
*  */
    fun ref(): Mat2D {

        val leadingEntry = getLeadingEntry()

        for ((i, key) in leadingEntry.keys.withIndex()) {
            ero1(i, key)
        }

        var token = 0

        for (i: Int in 0..<row) {
            for (j: Int in i + 1..<row) {
                if (this[j, token] != 0.0) {
                    val coef = -(this[j, token] / this[i, token])
                    ero3(coef, i, j)
                }
            }
            token++
        }

        return this
    }

    /*
    * Reduced Row Echelon Form
    *
    * Prop 1. All the leading entries in each of the rows of the matrix are 1.
    * Prop 2. If a columnumn contains a leading entry then all entries upper and below that leading entry are zero.
    *  */
    fun rref(): Mat2D {

        ref()

        val leadingEntry = getLeadingEntry()

        for (key in leadingEntry.keys) {
            if (key != 0) {
                for (j: Int in 0..<row) {
                    if (j != key) {
                        val coef = -(this[j, key] / this[key, leadingEntry[key]!!])
                        ero3(coef, key, j)
                    }

                }
            }
        }

        for (key in leadingEntry.keys) {
            ero2(1 / this[key, leadingEntry[key]!!], key)
        }

        cleanMinusZero()

        return this
    }


    fun luDecompose(): Pair<Mat2D, Mat2D> {

        var lowerMat = e(row)
        val eromList = mutableListOf<Mat2D>()

        //Prop 3.
        var matReference = copy()

        val leadingEntry = getLeadingEntry(matReference)

        val matCopy = Mat2D(matReference.row, matReference.column)

        for ((i, key) in leadingEntry.keys.withIndex()) {

            for(j : Int in 0 ..<column){
                matCopy[i, j] = matReference[key , j]
            }
        }

        var token = 0

        for (i: Int in 0..<matCopy.row) {

            for (j: Int in i + 1..<matCopy.row) {
                if (matCopy[j,token] != 0.0) {
                    val scale = -(matCopy[j,token] / matCopy[i,token])
                    matCopy.ero3(scale, i, j)
                    val erom = e(row)
                    erom[j, i] = scale
                    eromList.add(erom)
                }
            }
            token++
        }

        val upperMat = matCopy.copy()

        eromList.reverse()
        eromList.forEach {
            lowerMat *= it.inverse()
        }

        return Pair(lowerMat, upperMat)
    }
}