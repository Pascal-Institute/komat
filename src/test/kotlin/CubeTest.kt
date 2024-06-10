import komat.Generator.Companion.cube
import komat.Generator.Companion.mat
import org.junit.jupiter.api.Test

class CubeTest {

    val mat1 = mat{
        v(1,2)
        v(3,4)
    }

    @Test
    fun `test generator`() {

        /*val cube1 = cube {
            m(mat1)
            m(mat1)
        }*/

        val cube2 = cube{
            +mat1
            +mat1
        }

        val cube3= cube{
            +mat{
                v(1,2,3)
                v(4,5,6)
            }

            +mat{
                v(7,8,9)
                v(10,11,12)
            }

        }

        cube2.print()
        cube3.print()

    }



}