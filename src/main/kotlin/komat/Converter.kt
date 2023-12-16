package komat

class Converter {
    companion object{

        fun Array<Array<Double>>.toMat(): Mat {
            return Mat(this.map { it.toMutableList() }.toMutableList())
        }

        fun Array<Array<Number>>.toMat(): Mat {
            return (this.map { row ->
                row.map { it.toDouble() }.toTypedArray()
            }.toTypedArray()).toMat()
        }

        fun Array<Array<Int>>.toMat(): Mat {
            return (this.map { row ->
                row.map { it.toDouble() }.toTypedArray()
            }.toTypedArray()).toMat()
        }

        fun MutableList<MutableList<Double>>.toMat(): Mat {
            return Mat(this)
        }

        fun MutableList<MutableList<Number>>.toMat(): Mat {
            return (this.map { row ->
                row.map { it.toDouble() }.toMutableList()
            }.toMutableList()).toMat()
        }

        fun MutableList<MutableList<Int>>.toMat(): Mat {
            return (this.map { row ->
                row.map { it.toDouble() }.toMutableList()
            }.toMutableList()).toMat()
        }

    }
}