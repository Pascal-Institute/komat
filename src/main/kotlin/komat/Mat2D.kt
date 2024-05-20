package komat

class Mat2D : Vect() {

    var row: Int = 0
    var column: Int = 0

    fun v(elements: MutableList<Double>) {
        if(column == 0){
            column = elements.size
        }
        element.addAll(elements)
        row++
    }

    fun v(vararg elements: Number) {
        element.addAll(elements.map(Number::toDouble).toMutableList())
    }
}