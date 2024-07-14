package komat

import komat.Converter.Companion.toDoubleArray
import komat.Generator.Companion.mat
import komat.space.Mat
import komat.space.Vect

class Converter {
    companion object {
        fun Array<Array<Double>>.toMat(): Mat {

            val mat = Mat(this.size, this[0].size)
            mat.elements = this.flatten().toDoubleArray().map { Element(it) }.toTypedArray()
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
            mat.elements = this.flatten().map { Element(it) }.toTypedArray()
            return mat
        }

        fun MutableList<Vect>.vectToMat(): Mat {

            val mutableListVect = this

            return mat {
                for (i: Int in 0..<mutableListVect.size) {
                    v(mutableListVect[i].elements)
                }
            }
        }

        fun Array<Element>.toDoubleArray(): DoubleArray {
            return DoubleArray(this.size) { index ->
                (this[index] as Element.DoubleElement).value
            }
        }

        fun Array<Element>.toNumberArray(): Array<Number> {
            return Array<Number>(this.size) { index ->
                (this[index] as Element.DoubleElement).value
            }
        }

        fun Array<Number>.toDoubleArray() : DoubleArray {
            return this.map { it.toDouble() }.toDoubleArray()
        }

        fun Mat.toVect(): MutableList<Vect> {

            val vectList = mutableListOf<Vect>()

            for(index : Int in elements.indices step row ){
                vectList.add(Vect(elements.copyOfRange(index, index + row)));
            }

            return vectList;
        }

        fun Mat.toArray(): Array<Array<Number>> {
            val array2D: Array<Array<Number>> = Array(row) { i ->
                val start = i * column
                val end = Math.min(start + column, elements.size)
                elements.copyOfRange(start, end).map { it as Number }.toTypedArray()
            }
            return array2D
        }

        fun Mat.toMutableList(): MutableList<MutableList<Number>> {
            val list2D: MutableList<MutableList<Number>> = mutableListOf()
            var rowIndex = 0
            var columnIndex = 0
            while (rowIndex < elements.size) {
                val row: MutableList<Number> = mutableListOf()
                columnIndex = 0
                while (columnIndex < column && rowIndex < elements.size) {
                    row.add(elements[rowIndex].getValue() as Number)
                    rowIndex++
                    columnIndex++
                }
                list2D.add(row)
            }
            return list2D
        }
    }
}