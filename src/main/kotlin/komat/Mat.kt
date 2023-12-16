package komat

class Mat {

    constructor()

    constructor(row : Int, col : Int) : this(MutableList(row) { MutableList(col) { 0.0 } })

    constructor(elements : MutableList<MutableList<Double>>){
        elements.forEach {
            element.add(it)
            updateSize()
        }
    }

    var row = 0
    var col = 0

    val element = mutableListOf<MutableList<Double>>()

    fun row(vararg elements: Number) {
        element.add(elements.map(Number::toDouble).toMutableList())
        updateSize()
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

        for(j : Int in 0..<newMat.col){
            for (i : Int in 0..<newMat.row){
                newMat.element[i][j] += element[j][i] * mat.element[i][j]
            }
        }

        return newMat
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

fun mat(init: Mat.() -> Unit) : Mat {
    val mat = Mat()
    mat.apply(init).element
    return mat
}