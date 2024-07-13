package komat

import komat.space.Vect
import kotlin.math.exp

class Utility {
    companion object {
        //IEEE 754
        val EPSLION: Double = 1e-10

        //Activate
        fun relu(vect : Vect) : Vect {
            for(i : Int in vect.elements.indices){
                vect.elements[i] = if (vect.elements[i] > 0) vect.elements[i] else 0.0
            }

            return vect
        }

        fun tanh(vect : Vect) : Vect {
            for(i : Int in vect.elements.indices){
                vect[i] = (exp(vect[i]) - exp(-vect[i])) / (exp(vect[i]) + exp(-vect[i]))
            }
            return vect
        }

        fun sigmoid(vect: Vect) : Vect {
            for(i : Int in vect.elements.indices){
                vect[i] = 1 / (1 + exp(vect[i]))
            }
            return vect
        }

        fun softmax(vect : Vect): Vect {
            var denominator = 0.0

            vect.elements.forEach {
                denominator += exp(it)
            }

            for (i: Int in vect.elements.indices) {
                val numerator = exp(vect[i])
                vect[i] = numerator / denominator
            }

            return vect
        }

        fun swish(vect : Vect): Vect {

            val softmaxVect = softmax(vect)

            for (i: Int in vect.elements.indices) {
                vect[i] = vect[i] * softmaxVect[i]
            }

            return vect
        }
    }
}