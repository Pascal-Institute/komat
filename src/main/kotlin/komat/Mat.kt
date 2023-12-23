package komat

import komat.Generator.Companion.zero

//Support 2D Matrix
class Mat {
    companion object {
        operator fun Double.times(mat: Mat): Mat {
            mat.element.forEach { row ->
                row.replaceAll { this * it }
            }

            return mat
        }
    }

    private var row = 0
    private var col = 0

    var element = mutableListOf<MutableList<Double>>()

    constructor()

    constructor(row: Int, col: Int) : this(MutableList(row) { MutableList(col) { 0.0 } })

    constructor(elements: MutableList<MutableList<Double>>) {

        if (!isValid(elements)) {
            throw IllegalArgumentException("Invalid matrix: Rows must have the same length")
        }

        elements.forEach {
            element.add(it)
            updateSize()
        }
    }

    fun copy(): Mat {
        return Mat(this.element)
    }

    private fun isValid(srcCol: Int, dstRow: Int): Boolean {
        return (srcCol == dstRow)
    }

    private fun isValid(srcRow: Int, srcCol: Int, dstRow: Int, dstCol: Int): Boolean {
        return (srcRow == dstRow && srcCol == dstCol)
    }

    private fun isValid(elements: MutableList<MutableList<Double>>): Boolean {
        val firstRowSize = elements.firstOrNull()?.size ?: 0
        return elements.all { it.size == firstRowSize }
    }

    private fun isValid(elements: Array<out Number>): Boolean {
        if (col == 0) {
            col = elements.size
        } else {
            if (col != elements.size) {
                return false
            }
        }
        return true
    }

    fun setValue(row: Int, col: Int, value: Double) {
        element[row][col] = value
    }

    fun getValue(row: Int, col: Int): Double {
        return element[row][col]
    }

    /*
    * ERO : Elementary Row Operation
    * */
    fun ero1(src: Int, dst: Int): Mat {
        return exchangeRow(src, dst)
    }

    fun ero2(scale: Double, dst: Int): Mat {
        element[dst].replaceAll { it * scale }
        return this
    }

    fun ero3(scale: Double, src: Int, dst: Int): Mat {
        val srcRow = element[src]
        srcRow.replaceAll { it * scale }
        element[dst].addAll(srcRow)
        return exchangeRow(src, dst)
    }

    fun v(vararg elements: Number) {
        if (!isValid(elements)) {
            throw IllegalArgumentException("Invalid matrix: Rows must have the same length")
        }

        element.add(elements.map(Number::toDouble).toMutableList())
        updateSize()
    }

    fun appendRow(elements: MutableList<Double>): Mat {
        element.add(elements)
        updateSize()

        return this
    }

    fun appendRow(vararg elements: Number): Mat {
        element.add(elements.map(Number::toDouble).toMutableList())
        updateSize()

        return this
    }

    fun appendRowAt(idx: Int, elements: MutableList<Double>) {
        element.add(idx, elements)
    }

    fun getRowsInRange(start: Int, end: Int): Mat {

        val elementCopy = mutableListOf<MutableList<Double>>()

        for (i: Int in start..end) {
            elementCopy.add(element[i])
        }

        return Mat(elementCopy)
    }

    fun getColsInRange(start: Int, end: Int): Mat {

        val elementCopy = mutableListOf<MutableList<Double>>()
        var matCopy = this.copy()

        matCopy.transpose()

        for (i: Int in start..end) {
            elementCopy.add(matCopy.element[i])
        }

        matCopy = Mat(elementCopy).transpose()

        return matCopy
    }

    fun removeRowAt(index: Int): Mat {
        element.removeAt(index)
        updateSizeByElement()

        return this
    }

    private fun updateSizeByElement() {
        row = element.size
        col = element[0].size
    }

    operator fun plus(mat: Mat): Mat {

        if (!isValid(row, col, mat.row, mat.col)) {
            throw IllegalArgumentException("Invalid matrix: Matrix must be the same size")
        }

        for (i: Int in 0..<row) {
            for (j: Int in 0..<col) {
                element[i][j] += mat.element[i][j]
            }
        }

        return this
    }

    operator fun minus(mat: Mat): Mat {

        if (!isValid(row, col, mat.row, mat.col)) {
            throw IllegalArgumentException("Invalid matrix: Matrix must be the same size")
        }

        for (i: Int in 0..<row) {
            for (j: Int in 0..<col) {
                element[i][j] -= mat.element[i][j]
            }
        }

        return this
    }

    operator fun times(scale: Double): Mat {

        element.forEach { row ->
            row.replaceAll { it * scale }
        }

        return this
    }

    operator fun times(mat: Mat): Mat {

        if (!isValid(col, mat.row)) {
            throw IllegalArgumentException("Invalid matrix: A column & B row must be the same")
        }

        val newMat = Mat(row, mat.col)

        for (i: Int in 0..<newMat.row) {
            for (j: Int in 0..<newMat.col) {
                for (k: Int in 0..<col) {
                    newMat.element[i][j] += element[i][k] * mat.element[k][j]
                }
            }
        }

        return newMat
    }

    fun transpose(): Mat {

        val newMat = Mat(this.col, this.row)

        for (i in 0..<newMat.row) {
            for (j in 0..<newMat.col) {
                newMat.element[i][j] = element[j][i]
            }
        }

        this.row = newMat.row
        this.col = newMat.col
        this.element = newMat.element

        return this
    }

    fun exchangeRow(src: Int, dst: Int): Mat {

        val srcRow = element[src]
        element[src] = element[dst]
        element[dst] = srcRow

        return this
    }

    fun exchangeColumn(src: Int, dst: Int): Mat {

        val matCopy = this.copy()

        matCopy.transpose()

        val srcRow = matCopy.element[src]
        matCopy.element[src] = matCopy.element[dst]
        matCopy.element[dst] = srcRow

        this.element = matCopy.transpose().element

        return this
    }

    /*
    * Row Echelon Form
    *
    * Prop 1. All the leading entries in each of the rows of the matrix are 1.
    * Prop 2. If a column contains a leading entry then all entries below that leading entry are zero.
    * Prop 3. In any two consecutive non-zero rows, the leading entry in the upper row occurs to the left of the leading entry in the lower row.
    * Prop 4. All rows which consist entirely of zeroes appear at the bottom of the matrix.
    *  */
    fun ref(): Mat {

        //Prop 4.
        var zeroCount = 0
        var matCopy = copy()
        val zeroRow = zero(matCopy.col).element[0]

        for (i: Int in 0..<row) {
            if (isZero(element[i])) {
                matCopy.removeRowAt(i)
                matCopy.appendRow(zeroRow)
                zeroCount++
            }
        }

        if (zeroCount > 0) {
            matCopy = matCopy.getRowsInRange(0, row - zeroCount - 1)
        }

        var token = 0
        var leading1 = mutableListOf<Int>()

        for (j: Int in 0..<matCopy.col) {
            for (i: Int in token..<matCopy.row) {
                if (matCopy.element[i][j] != 0.0) {
                    matCopy.exchangeRow(i, token)
                    matCopy.ero2(1.0 / matCopy.element[token][j] , token)
                    leading1.add(j)
                    token++
                    break
                }
            }
            if (token == matCopy.row) {
                break
            }
        }

        for (i: Int in 0..<zeroCount) {
            matCopy.appendRow(zeroRow)
        }

        return matCopy
    }


    private fun updateSize() {
        if (row == 0) {
            row = 1
            col = element.firstOrNull()?.size ?: 0
        } else {
            row++
        }
    }

    fun isZero(rowElement: MutableList<Double>): Boolean {
        return (rowElement.sum() == 0.0)
    }

    fun sum(): Double {
        var sum = 0.0
        for (row in element) {
            sum += row.sum()
        }
        return sum
    }

    fun mean(): Double {
        return sum() / (row * col)
    }

    fun max(): Double {
        val rowMaxList = element.map { it.maxOrNull() ?: Double.NaN }
        return rowMaxList.maxOrNull() ?: Double.NaN
    }

    fun min(): Double {
        val rowMaxList = element.map { it.minOrNull() ?: Double.NaN }
        return rowMaxList.minOrNull() ?: Double.NaN
    }

    fun print() {
        for (row in this.element) {
            println(row.joinToString(", ", "[", "]"))
        }
    }
}