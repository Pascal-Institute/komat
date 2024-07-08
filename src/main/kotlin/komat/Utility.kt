package komat

import komat.space.Vect
import kotlin.math.exp
import kotlin.reflect.KType

class Utility {
    companion object {
        //IEEE 754
        val EPSLION: Double = 1e-10

        //Activate
        fun relu(vect : Vect) : Vect {
            for(i : Int in vect.element.indices){
                vect.element[i] = if (vect.element[i] > 0) vect.element[i] else 0.0
            }

            return vect
        }

        fun tanh(vect : Vect) : Vect {
            for(i : Int in vect.element.indices){
                vect.element[i] = (exp(vect.element[i]) - exp(-vect.element[i])) / (exp(vect.element[i]) + exp(-vect.element[i]))
            }
            return vect
        }

        fun sigmoid(vect: Vect) : Vect {
            for(i : Int in vect.element.indices){
                vect.element[i] = 1 / (1 + exp(vect.element[i]))
            }
            return vect
        }

        fun softmax(vect : Vect): Vect {
            var denominator = 0.0

            vect.element.forEach {
                denominator += exp(it)
            }

            for (i: Int in vect.element.indices) {
                val numerator = exp(vect.element[i])
                vect.element[i] = numerator / denominator
            }

            return vect
        }
    }
}