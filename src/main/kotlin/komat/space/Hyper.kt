package komat.space

class Hyper : Vect {

    var group : Int = 0
    var depth : Int = 0
    var row : Int = 0
    var column : Int = 0

    constructor()

    constructor(group : Int, depth: Int, row: Int, column: Int) {
        this.group = group
        this.depth = depth
        this.row = row
        this.column = column

        element = DoubleArray(group * depth * row * column) { 0.0 }
    }

    operator fun get(g : Int, h: Int, i: Int, j: Int): Double {
        if (i >= row || j >= column || h >= depth) {
            throw IndexOutOfBoundsException("Index out of bounds: [$i, $j]")
        }
        return element[g * depth * row * column  + h * row * column + i * column + j]
    }

    operator fun set(g: Int, h: Int, i: Int, j: Int, value: Number) {
        if (i >= row || j >= column) {
            throw IndexOutOfBoundsException("Index out of bounds: [$i, $j]")
        }
        element[g * depth * row * column + h * row * column + i * column + j] = value.toDouble()
    }
}