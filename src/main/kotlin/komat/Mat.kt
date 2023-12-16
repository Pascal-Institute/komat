package komat

import komat.Converter.Companion.toMutableList

//Support 2D Matrix
class Mat {

    constructor()

    constructor(row : Int, col : Int) : this(MutableList(row) { MutableList(col) { 0.0 } })

    constructor(elements : MutableList<MutableList<Double>>){

        if (!isValid(elements)) {
            throw IllegalArgumentException("Invalid matrix: Rows must have the same length")
        }

        elements.forEach {
            element.add(it)
            updateSize()
        }
    }

    private fun isValid(elements: MutableList<MutableList<Double>>): Boolean {
        val firstRowSize = elements.firstOrNull()?.size ?: 0
        return elements.all { it.size == firstRowSize }
    }

    private fun isValid(elements: Array<out Number>): Boolean {
        if(col == 0){
            col = elements.size
        } else{
            if(col != elements.size){
                return false
            }
        }
        return true
    }

    var row = 0
    var col = 0

    var element = mutableListOf<MutableList<Double>>()

    fun v(vararg elements: Number) {
        if(!isValid(elements)){
            throw IllegalArgumentException("Invalid matrix: Rows must have the same length")
        }


        element.add(elements.map(Number::toDouble).toMutableList())
        updateSize()
    }

    fun appendRow(vararg elements: Number) : Mat {
        element.add(elements.map(Number::toDouble).toMutableList())
        updateSize()

        return Mat(element)
    }

    operator fun plus(mat : Mat) : Mat {

        val newMat = Mat(this.element)

        mat.element.forEachIndexed { index, row ->
            this.element[index].forEachIndexed { idx, double ->
                newMat.element[index][idx] = double + row[idx]
            }
        }
        return newMat
    }

    operator fun minus(mat : Mat) : Mat {

        val newMat = Mat(this.element)

        mat.element.forEachIndexed { index, row ->
            this.element[index].forEachIndexed { idx, double ->
                newMat.element[index][idx] = double - row[idx]
            }
        }
        return newMat
    }

    operator fun times(mat : Mat) : Mat {

        val newMat = Mat(this.row, mat.col)

        for(i : Int in 0..<newMat.row){
            for (j : Int in 0..<newMat.col){
                for(k : Int in 0..<this.col){
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

    private fun updateSize(){

        if (row == 0) {
            row = 1
            col = element.firstOrNull()?.size ?: 0
        } else {
            row++
        }
    }
}