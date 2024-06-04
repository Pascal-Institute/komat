package komat

class Generator {
    companion object {
        fun mat(init: Mat.() -> Unit) : Mat {
            val mat = Mat()
            mat.apply(init)
            return mat
        }

        fun mat2D(init: Mat.() -> Unit) : Mat {
            val mat2d = Mat()
            mat2d.apply(init)
            return mat2d
        }

        fun zero(size: Int): Mat {
            return Mat(size, size)
        }

        fun e(size : Int) : Mat{
            val mat = Mat(size,size)
            (0..<size).forEach { index->
                mat[index,index] = 1.0
            }
            return mat
        }

        fun diagonal(mutableList: MutableList<Double>) : Mat {
            val mat = zero(mutableList.size)
            mutableList.forEachIndexed { idx, value ->
                mat[idx,idx] = value
            }
            return mat
        }
    }
}