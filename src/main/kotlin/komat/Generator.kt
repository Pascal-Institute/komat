package komat

class Generator {
    companion object {
        fun mat(init: Mat.() -> Unit) : Mat {
            val mat = Mat()
            mat.apply(init)
            return mat
        }

        fun mat2D(init: Mat2D.() -> Unit) : Mat2D {
            val mat2d = Mat2D()
            mat2d.apply(init)
            return mat2d
        }

        fun zero(size: Int): Mat {
            return Mat(size, size)
        }

        fun _e(size : Int) : Mat{
            val mat = Mat(size,size)
            (0..<size).forEach { index->
                mat.element[index][index] = 1.0
            }
            return mat
        }

        fun e(size : Int) : Mat2D{
            val mat = Mat2D(size,size)
            (0..<size).forEach { index->
                mat[index,index] = 1.0
            }
            return mat
        }

        fun elementary(size : Int) : Mat{
            return _e(size)
        }

        fun diagonal(mutableList: MutableList<Double>) : Mat {
            val mat = zero(mutableList.size)
            mutableList.forEachIndexed { idx, value ->
                mat.element[idx][idx] = value
            }
            return mat
        }
    }
}