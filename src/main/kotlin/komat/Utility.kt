package komat

import komat.space.Vect
import kotlin.math.exp

class Utility {
    companion object {
        //IEEE 754
        val EPSLION: Double = 1e-10

        //please use eq instead of ==
        infix fun Element.eq(other: Double): Boolean = this.toDouble() == other

        //Activate
        fun relu(vect: Vect): Vect {
            for (i: Int in vect.elements.indices) {
                vect.elements[i] = if (vect[i].toDouble() > 0) vect[i] else Element(0.0)
            }

            return vect
        }

        fun tanh(vect: Vect): Vect {
            for (i: Int in vect.elements.indices) {
                vect[i] =
                    (exp(vect[i].toDouble()) - exp(-(vect[i].toDouble()))) / (exp(vect[i].toDouble()) + exp(
                        -(vect[i].toDouble())
                    ))
            }
            return vect
        }

        fun sigmoid(vect: Vect): Vect {
            for (i: Int in vect.elements.indices) {
                vect[i] = 1 / (1 + exp(vect[i].toDouble()))
            }
            return vect
        }

        fun softmax(vect: Vect): Vect {
            var denominator = Element(0.0)

            vect.elements.forEach {
                denominator += Element(exp(it.toDouble()))
            }

            for (i: Int in vect.elements.indices) {
                val numerator = Element(exp(vect[i].toDouble()))
                vect[i] = numerator / denominator
            }

            return vect
        }

        fun swish(vect: Vect): Vect {

            val softmaxVect = softmax(vect)

            for (i: Int in vect.elements.indices) {
                vect[i] = (vect[i].toDouble()) * (softmaxVect[i].toDouble())
            }

            return vect
        }
    }
}