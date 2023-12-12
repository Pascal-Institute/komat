package komat

class Mat {

    var row = 0
    var col = 0

    val rows = mutableListOf<List<Double>>()

    fun row(vararg elements: Double) {
        rows.add(elements.toList())
        updateSize()
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