package komat

import komat.space.Vect
import kotlin.math.exp
import kotlin.reflect.KType

class Utility {
    companion object {
        //IEEE 754
        val EPSLION: Double = 1e-10

        //Activate
        fun Vect.relu() : Vect {
            for(i : Int in element.indices){
                element[i] = if (element[i] > 0) element[i] else 0.0
            }

            return this
        }

        fun Vect.sigmoid() : Vect {
            for(i : Int in element.indices){

                element[i] = 1 / (1 + exp(-element[i]))
            }
            return this
        }

        fun Vect.softmax(): Vect {
            var denominator = 0.0

            element.forEach {
                denominator += exp(it)
            }

            for (i: Int in element.indices) {
                val numerator = exp(element[i])
                element[i] = numerator / denominator
            }

            return this
        }

    }
}