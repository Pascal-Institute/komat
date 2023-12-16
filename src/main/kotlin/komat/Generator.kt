package komat

class Generator {
    companion object {
        fun mat(init: Mat.() -> Unit) : Mat {
            val mat = Mat()
            mat.apply(init)
            return mat
        }

        fun zero(size: Int): Mat {
            return Mat(size, size)
        }

        fun e(size : Int) : Mat{
            val mat = Mat(size,size)
            (0..<size).forEach { index->
                mat.element[index][index] = 1.0
            }
            return mat
        }
    }
}