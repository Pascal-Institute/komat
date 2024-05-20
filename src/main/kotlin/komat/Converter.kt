package komat

import komat.Generator.Companion.mat

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

        fun MutableList<MutableList<Number>>.toMat(): Mat {
            return Mat(this.map { row ->
                row.map { it.toDouble() }.toMutableList()
            }.toMutableList())
        }

        fun MutableList<Vect>.vectToMat() : Mat {

            val mutableListVect = this

            return mat{
                for(i : Int in 0..<mutableListVect.size){
                    mutableListVect[i]
                }
            }
        }

        fun Mat.toVect() : MutableList<Vect> {

            val vectList = mutableListOf<Vect>()

            element.forEach {
                vectList.add(Vect(it));
            }

            return vectList;
        }

        fun Mat.toArray() : Array<Array<Number>> {
            return this.element.map { it.map { it as Number }.toTypedArray() }.toTypedArray()
        }

        fun Mat.toMutableList() : MutableList<MutableList<Number>> {
            return this.element.map { it.map { it as Number }.toMutableList() }.toMutableList()
        }
    }
}