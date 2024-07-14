package komat.space

import komat.Element

//4-Dimensional
class Hyper : Cube {

    var group: Int = 0

    constructor()

    constructor(group: Int, depth: Int, row: Int, column: Int) {
        this.group = group
        this.depth = depth
        this.row = row
        this.column = column

        elements = Array(group * depth * row * column) { Element(0.0) }
    }

    operator fun get(g: Int, h: Int, i: Int, j: Int): Element {
        if (i >= row || j >= column || h >= depth) {
            throw IndexOutOfBoundsException("Index out of bounds: [$i, $j]")
        }
        return elements[g * depth * row * column + h * row * column + i * column + j]
    }

    operator fun set(g: Int, h: Int, i: Int, j: Int, value: Double) {
        if (i >= row || j >= column) {
            throw IndexOutOfBoundsException("Index out of bounds: [$i, $j]")
        }
        elements[g * depth * row * column + h * row * column + i * column + j] = Element(value)
    }

    operator fun set(g: Int, h: Int, i: Int, j: Int, value: Number) {
        if (i >= row || j >= column) {
            throw IndexOutOfBoundsException("Index out of bounds: [$i, $j]")
        }
        elements[g * depth * row * column + h * row * column + i * column + j] = Element(value)
    }

    operator fun set(g: Int, h: Int, i: Int, j: Int, value: Element) {
        if (i >= row || j >= column) {
            throw IndexOutOfBoundsException("Index out of bounds: [$i, $j]")
        }
        elements[g * depth * row * column + h * row * column + i * column + j] = value
    }
}