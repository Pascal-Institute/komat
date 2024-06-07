package komat

class Mat3D : Vect{

    constructor()

    var depth : Int = 0
    var row: Int = 0
    var column: Int = 0

    constructor(depth : Int, row: Int, column: Int) {
        this.row = row
        this.column = column

        element = DoubleArray(depth * row * column) { 0.0 }
    }

    fun m(mat : Mat) {
        if (column == 0) {
            column = mat.column
        }

        if (row == 0){
            row = mat.row
        }

        if (mat.column != column || mat.row != row) {
            throw IllegalArgumentException("Invalid matrix: Column & Row must have the same length")
        }

        append(mat)
    }

    fun append(mat : Mat) : Mat3D{
        depth++
        val oldArray = element.clone()
        element = DoubleArray(depth * row * column)
        System.arraycopy(oldArray, 0, element, 0, oldArray.size)
        System.arraycopy(mat.element, 0, element,  (depth -1) * row * column, mat.element.size)
        return this
    }

}