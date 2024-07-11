package komat.space

import komat.type.Axis

//3-Dimensional
open class Cube : Mat {

    var depth: Int = 0

    constructor()

    constructor(depth: Int, row: Int, column: Int) {
        this.depth = depth
        this.row = row
        this.column = column

        element = DoubleArray(depth * row * column) { 0.0 }
    }

    operator fun get(d: Int, r: Int, c: Int): Double {
        if (d >= depth || r >= row || c >= column) {
            throw IndexOutOfBoundsException("Index out of bounds: [$d, $r, $c]")
        }
        return element[d * row * column + r * column + c]
    }

    operator fun set(d: Int, r: Int, c: Int, value: Number) {
        if (d >= depth || r >= row || c >= column) {
            throw IndexOutOfBoundsException("Index out of bounds: [$d, $r, $c]")
        }
        element[d * row * column + r * column + c] = value.toDouble()
    }

    operator fun times(cube: Cube): Cube {

        if (this.column != cube.depth) {
            throw IllegalArgumentException("Invalid matrix: A's column & B's depth must be the same")
        }

        val newCube = Cube(this.depth, this.row, cube.column)

        for (d: Int in 0..<depth) {
            for (r: Int in 0..<row) {
                for (i: Int in 0..<cube.column) {
                    for (c in 0..<column) {
                        newCube[d, r, c] += this[d, r, i] * cube[i, r, c]
                    }
                }
            }
        }

        element = newCube.element.clone()

        depth = newCube.depth
        row = newCube.row
        column = newCube.column

        return this
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

    override fun flip(axis: Axis): Cube {

        val cube = Cube(depth, row, column)

        when (axis) {
            Axis.HORIZONTAL -> {
                for (d: Int in 0..<depth) {
                    for (r: Int in 0..<row) {
                        for (i: Int in 0..<cube.column) {
                            for (c in 0..<column) {
                                cube[d, r, c] = this[d, r, column - c - 1]
                            }
                        }
                    }
                }
            }

            Axis.VERTICAL -> {
                for (d: Int in 0..<depth) {
                    for (r: Int in 0..<row) {
                        for (i: Int in 0..<cube.column) {
                            for (c in 0..<column) {
                                cube[d, r, c] = this[depth - d - 1, row, column]
                            }
                        }
                    }
                }
            }

            Axis.FRONTAL -> {
                for (d: Int in 0..<depth) {
                    for (r: Int in 0..<row) {
                        for (i: Int in 0..<cube.column) {
                            for (c in 0..<column) {
                                cube[d, r, c] = this[d, row - r - 1, column]
                            }
                        }
                    }
                }
            }

            else->{/*Do Nothing*/}

        }

        this.element = cube.element

        return this
    }

    override fun transpose(): Cube {
        val newCube = Cube(column, row, depth)

        for (d in 0..<newCube.depth) {
            for (r in 0..<newCube.row) {
                for (c in 0..<newCube.column) {
                    newCube[d, r, c] = this[c, r, d]
                }
            }
        }

        element = newCube.element.clone()

        depth = newCube.depth
        row = newCube.row
        column = newCube.column

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