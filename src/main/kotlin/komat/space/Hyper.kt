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

    override fun size() : Int{
        return group * depth * row * column
    }

    operator fun get(g: Int, d: Int, r: Int, c: Int): Element {
        if (g >= group || d >= row || r >= column || c >= depth) {
            throw IndexOutOfBoundsException("Index out of bounds: [$g, $d, $r, $c]")
        }
        return elements[g * depth * row * column + d * row * column + r * column + c]
    }

    operator fun set(g: Int, d: Int, r: Int, c: Int, value: Double) {
        if (g >= group || d >= row || r >= column || c >= depth) {
            throw IndexOutOfBoundsException("Index out of bounds: [$g, $d, $r, $c]")
        }
        elements[g * depth * row * column + d * row * column + r * column + c] = Element(value)
    }

    operator fun set(g: Int, d: Int, r: Int, c: Int, value: Number) {
        if (g >= group || d >= row || r >= column || c >= depth) {
            throw IndexOutOfBoundsException("Index out of bounds: [$g, $d, $r, $c]")
        }
        elements[g * depth * row * column + d * row * column + r * column + c] = Element(value)
    }

    operator fun set(g: Int, d: Int, r: Int, c: Int, value: Byte) {
        if (g >= group || d >= row || r >= column || c >= depth) {
            throw IndexOutOfBoundsException("Index out of bounds: [$g, $d, $r, $c]")
        }
        elements[g * depth * row * column + d * row * column + r * column + c] = Element(value)
    }

    operator fun set(g: Int, d: Int, r: Int, c: Int, value: Element) {
        if (g >= group || d >= row || r >= column || c >= depth) {
            throw IndexOutOfBoundsException("Index out of bounds: [$g, $d, $r, $c]")
        }
        elements[g * depth * row * column + d * row * column + r * column + c] = value
    }

    override fun copy(): Hyper {
        val copiedHyper = Hyper(group, depth, row, column)
        copiedHyper.elements = elements.copyOf()
        return copiedHyper
    }
}