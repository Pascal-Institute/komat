package komat

import komat.Converter.Companion.toMutableList

//Support 2D Matrix
class Mat {

    var row = 0
    var col = 0

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

    fun appendRow(vararg elements: Number): Mat {
        element.add(elements.map(Number::toDouble).toMutableList())
        updateSize()

        return Mat(element)
    }

    operator fun plus(mat: Mat): Mat {

        val newMat = Mat(this.element)

        mat.element.forEachIndexed { index, row ->
            this.element[index].forEachIndexed { idx, double ->
                newMat.element[index][idx] = double + row[idx]
            }
        }
        return newMat
    }

    operator fun minus(mat: Mat): Mat {

        val newMat = Mat(this.element)

        mat.element.forEachIndexed { index, row ->
            this.element[index].forEachIndexed { idx, double ->
                newMat.element[index][idx] = double - row[idx]
            }
        }
        return newMat
    }

    operator fun times(mat: Mat): Mat {

        val newMat = Mat(this.row, mat.col)

        for (i: Int in 0..<newMat.row) {
            for (j: Int in 0..<newMat.col) {
                for (k: Int in 0..<this.col) {
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


    private fun updateSize() {
        if (row == 0) {
            row = 1
            col = element.firstOrNull()?.size ?: 0
        } else {
            row++
        }
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