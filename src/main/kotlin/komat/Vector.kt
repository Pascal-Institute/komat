package komat

class Vector(vararg _elements: Number) {
    var elements = _elements.map(Number::toDouble).toMutableList()

    fun dotProduct(_vector: Vector): Double {

        var result = 0.0

        elements.forEachIndexed { indexed, it ->
            result += it * _vector.elements[indexed]
        }

        return result
    }
}