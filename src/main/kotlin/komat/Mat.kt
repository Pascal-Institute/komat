package komat

class Mat {

    constructor()
    constructor(elements : MutableList<MutableList<Double>>){
        elements.forEach {
            rows.add(it)
            updateSize()
        }
    }

    var row = 0
    var col = 0

    val rows = mutableListOf<MutableList<Double>>()

    fun row(vararg elements: Double) {
        rows.add(elements.toMutableList())
        updateSize()
    }

    operator fun Mat.plus(mat : Mat) : Mat{
        return this.plus(mat)
    }

    operator fun plus(mat : Mat) : Mat {

        val newMat = Mat(this.rows)

        mat.rows.forEachIndexed { index, row ->
            this.rows[index].forEachIndexed { idx, double ->
                newMat.rows[index][idx] = double + row[idx]
            }
        }
        return newMat
    }

    operator fun Mat.minus(mat : Mat) : Mat{
        return this.minus(mat)
    }

    fun minus(mat : Mat) : Mat {

        val newMat = Mat(this.rows)

        mat.rows.forEachIndexed { index, row ->
            this.rows[index].forEachIndexed { idx, double ->
                newMat.rows[index][idx] = double - row[idx]
            }
        }
        return newMat
    }

    private fun updateSize(){
        if (row == 0) {
            row = 1
            col = rows.firstOrNull()?.size ?: 0
        } else {
            row++
        }
    }
}

fun mat(init: Mat.() -> Unit) : Mat {
    val mat = Mat()
    mat.apply(init).rows
    return mat
}