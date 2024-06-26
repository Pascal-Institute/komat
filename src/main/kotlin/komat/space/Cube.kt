package komat.space

import komat.type.Padding

open class Cube : Mat {

    var depth: Int = 0

    constructor()

    constructor(depth: Int, row: Int, column: Int) {
        this.depth = depth
        this.row = row
        this.column = column

        element = DoubleArray(depth * row * column) { 0.0 }
    }

    operator fun get(h: Int, i: Int, j: Int): Double {
        if (i >= row || j >= column || h >= depth) {
            throw IndexOutOfBoundsException("Index out of bounds: [$i, $j]")
        }
        return element[h * row * column + i * column + j]
    }

    operator fun set(h: Int, i: Int, j: Int, value: Number) {
        if (i >= row || j >= column) {
            throw IndexOutOfBoundsException("Index out of bounds: [$i, $j]")
        }
        element[h * row * column + i * column + j] = value.toDouble()
    }

    operator fun Mat.unaryPlus() {
        val oldArray = this@Cube.element.clone()
        depth++
        this@Cube.row = this.row
        this@Cube.column = this.column
        this@Cube.element = DoubleArray(depth * row * column)
        System.arraycopy(oldArray, 0, this@Cube.element, 0, oldArray.size)
        System.arraycopy(this.element, 0, this@Cube.element, (depth - 1) * row * column, this.element.size)
    }

    fun appendMat(mat: Mat): Cube {

        depth++

        if (row == 0) {
            row = mat.row
        }

        if (column == 0) {
            column = mat.column
        }

        val oldArray = element.clone()
        element = DoubleArray(depth * row * column)
        System.arraycopy(oldArray, 0, element, 0, oldArray.size)
        System.arraycopy(mat.element, 0, element, (depth - 1) * row * column, mat.element.size)


        return this
    }

    override fun print() {
        println("[")
        for (h: Int in 0..<depth) {


            for (i: Int in 0..<row) {
                print("[")
                for (j: Int in 0..<column) {

                    print(this[h, i, j])

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
            if (h < depth - 1) {
                println()
            }
        }
        println("]")
    }


}