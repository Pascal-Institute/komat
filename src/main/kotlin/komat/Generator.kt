package komat

import komat.space.Cube
import komat.space.Mat

class Generator {
    companion object {

        fun mat(init: Mat.() -> Unit): Mat {
            val mat = Mat()
            mat.apply(init)
            return mat
        }

        fun cube(init: Cube.() -> Unit): Cube {
            val cube = Cube()
            cube.apply(init)
            return cube
        }

        fun zero(size: Int): Mat {
            return Mat(size, size)
        }

        fun e(size: Int): Mat {
            val mat = Mat(size, size)
            (0..<size).forEach { index ->
                mat[index, index] = 1.0
            }
            return mat
        }

        fun diagonal(mutableList: MutableList<Double>): Mat {
            val mat = zero(mutableList.size)
            mutableList.forEachIndexed { idx, value ->
                mat[idx, idx] = value
            }
            return mat
        }
    }
}