import komat.Generator.Companion.cube
import komat.Generator.Companion.mat
import komat.Mat.Companion.times
import org.junit.jupiter.api.Test

class CubeTest {

    val mat1 = mat{
        v(1,1)
        v(1,1)
    }

    @Test
    fun `test generator`() {

        val cube1 = cube {
            m(mat1)
            m(mat1)
        }

        cube1.print()

    }


}