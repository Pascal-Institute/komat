package komat

class Vector(vararg _elements: Number) {
    var elements = _elements.map(Number::toDouble).toMutableList();
}