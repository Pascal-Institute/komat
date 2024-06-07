package komat

import komat.Generator.Companion.mat

class Converter {
    companion object {

        fun Array<Array<Double>>.toMat(): Mat {

            val mat = Mat(this.size, this[0].size)
            mat.element = this.flatten().toDoubleArray()
            return mat
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

        fun MutableList<MutableList<Number>>.toMat(): Mat {
            val mat = Mat(this.size, this[0].size)
            mat.element = this.flatten().map { it.toDouble() }.toDoubleArray()
            return mat
        }

        fun MutableList<Vect>.vectToMat(): Mat {

            val mutableListVect = this

            return mat {
                for (i: Int in 0..<mutableListVect.size) {
                    v(mutableListVect[i].element)
                }
            }
        }

        fun Mat.toVect(): MutableList<Vect> {

            val vectList = mutableListOf<Vect>()

            element.forEach {
                vectList.add(Vect(it));
            }

            return vectList;
        }

        fun Mat.toArray(): Array<Array<Number>> {
            val array2D: Array<Array<Number>> = Array(row) { i ->
                val start = i * column
                val end = Math.min(start + column, element.size)
                element.copyOfRange(start, end).map { it as Number }.toTypedArray()
            }
            return array2D
        }

        fun Mat.toMutableList(): MutableList<MutableList<Number>> {
            val list2D: MutableList<MutableList<Number>> = mutableListOf()
            var rowIndex = 0
            var columnIndex = 0
            while (rowIndex < element.size) {
                val row: MutableList<Number> = mutableListOf()
                columnIndex = 0
                while (columnIndex < column && rowIndex < element.size) {
                    row.add(element[rowIndex])
                    rowIndex++
                    columnIndex++
                }
                list2D.add(row)
            }
            return list2D
        }
    }
}