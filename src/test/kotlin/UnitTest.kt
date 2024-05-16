import komat.Generator.Companion.mat
import komat.Generator.Companion.v
import komat.Mat.Companion.times
import komat.prop.Axis
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class UnitTest {

    val vect1 = v(1.0, 2.0, 3.0)

    val mat1 = mat {
        v(1, 2)
        v(3, 4)
    }

    val mat2 = mat {
        v(4, 3)
        v(2, 1)
    }

    val mat3 = mat {
        v(0, 0, 0, 0, 0)
        v(0, 2, 3, 0, 1)
        v(1, 1, 0, 1, 0)
        v(0, 2, 0, 0, 0)
    }

    val mat4 = mat {
        v(1, 0, 0, 0)
        v(0, 2, 3, 0)
        v(1, 1, 3, 1)
        v(0, 2, 0, 4)
    }

    val mat5 = mat {
        v(1, 3, 4, 1)
        v(1, 2, -3, 2)
        v(2, 6, 5, 6)
        v(3, 4, 5, 9)
    }

    val mat6 = mat {
        v(2, 1, 3)
        v(-1, 0, 2)
        v(4, 3, 1)
    }

    val mat7 = mat {
        v(1, 2, 3, 4)
        v(2, 4, 5, 6)
        v(3, 5, 7, 8)
        v(4, 6, 8, 10)
    }

    val mat8 = mat {
        v(2, 1, -1)
        v(-3, -1, 2)
        v(-2, 1, 2)
    }

    val mat9 = mat{
        v(1,2,3)
        v(2,4,6)
        v(3,6,9)
    }

    val mat10 = mat{
        v(3.0/7, 2.0/7, 6.0/7)
        v(-6.0/7, 3.0/7, 2.0/7)
        v(2.0/7, 6.0/7, -3.0/7)
    }

    @Test
    fun `test copy`() {

        val copy = mat1.copy()

        assertNotEquals(
            copy,
            mat1
        )

        assertEquals(mat1.element, copy.element)
    }

    @Test
    fun `test isOrthogonal`() {
        assertEquals(mat10.isOrthogonal(), true)
    }

    @Test
    fun `test invalid matrix size`() {
        assertThrows<IllegalArgumentException> {
            mat {
                v(1, 2)
                v(1)
            }
        }
    }

    @Test
    fun `test plus`() {
        assertEquals(
            mat {
                v(5, 5)
                v(5, 5)
            }.element,
            mat1.plus(mat2).element
        )
    }

    @Test
    fun `test minus`() {
        assertEquals(
            mat {
                v(-3, -1)
                v(1, 3)
            }.element,
            mat1.minus(mat2).element
        )
    }

    @Test
    fun `test flip`() {
        assertEquals(
            mat1.flip(Axis.HORIZONTAL).element,
            mat {
                v(3, 4)
                v(1, 2)
            }.element
        )



        assertEquals(
            mat {
                v(1, 2)
                v(3, 4)
            }.flip(Axis.VERTICAL).element,
            mat {
                v(2, 1)
                v(4, 3)
            }.element
        )
    }

    @Test
    fun `test scalar multiplication`() {
        assertEquals(
            (3.0 * mat1).element,
            mat {
                v(3.0, 6.0)
                v(9.0, 12.0)
            }.element
        )
    }

    @Test
    fun `test exchange rows`() {
        assertEquals(
            mat1.exchangeColumn(0, 1).element,
            mat {
                v(2, 1)
                v(4, 3)
            }.element
        )
    }

    @Test
    fun `test exchange columns`() {
        assertEquals(
            mat {
                v(3, 4)
                v(1, 2)
            }.element,
            mat1.exchangeRow(0, 1).element
        )
    }

    @Test
    fun `test adjugate`() {
        assertEquals(
            mat6.adjugate().element,
            mat {
                v(-6, 8, 2)
                v(9, -10, -7)
                v(-3, -2, 1)
            }.element
        )
    }

    @Test
    fun `test inverse`(){
        assertEquals(
            mat7.inverse().element,
            mat {
                v(-1, -1, 0, 1)
                v(-1, 2, -1, 0)
                v(0, -1, 2, -1)
                v(1, 0, -1, 0.5)
            }.element
        )
    }

    @Test
    fun `test transpose`() {
        assertEquals(
            mat {
                v(1, 2)
                v(3, 4)
                v(5, 6)
            }.transpose().element,
            mat {
                v(1, 3, 5)
                v(2, 4, 6)
            }.element
        )
    }

    @Test
    fun `test transpose twice`() {
        assertEquals(
            mat {
                v(1, 2)
                v(3, 4)
                v(5, 6)
            }.transpose().transpose().element,
            mat {
                v(1, 2)
                v(3, 4)
                v(5, 6)
            }.element
        )
    }

    @Test
    fun `test removeColumnAt`() {
        assertEquals(
            mat4.removeColumnAt(1).element,
            mat {
                v(1, 0, 0)
                v(0, 3, 0)
                v(1, 3, 1)
                v(0, 0, 4)
            }.element
        )
    }

    @Test
    fun `test removeRowAt`() {
        assertEquals(
            mat4.removeRowAt(2).element,
            mat {
                v(1, 0, 0, 0)
                v(0, 2, 3, 0)
                v(0, 2, 0, 4)
            }.element
        )
    }

    @Test
    fun `test removeAt`() {
        assertEquals(
            mat4.removeAt(2, 1).element,
            mat {
                v(1, 0, 0)
                v(0, 3, 0)
                v(0, 0, 4)
            }.element
        )
    }

    @Test
    fun `test concat`() {
        assertEquals(
            mat1.concat(
                mat { v(5, 6) }, Axis.HORIZONTAL
            ).element,
            mat {
                v(1, 2)
                v(3, 4)
                v(5, 6)
            }.element
        )

        assertEquals(
            mat2.concat(
                mat {
                    v(5)
                    v(6)
                }, Axis.VERTICAL
            ).element,
            mat {
                v(4, 3, 5)
                v(2, 1, 6)
            }.element
        )
    }

    @Test
    fun `test getColsInRange`() {
        assertEquals(
            mat {
                v(1, 2, 3)
                v(4, 5, 6)
            }.getColumnsInRange(0, 2).element,
            mat {
                v(1, 2)
                v(4, 5)
            }.element
        )
    }

    @Test
    fun `test det`() {
        assertEquals(
            mat5.det(), 115.0
        )
    }

    @Test
    fun `test ref`() {
        assertEquals(
            mat {
                v(1, 1, 0, 1, 0)
                v(0, 2, 3, 0, 1)
                v(0, 0, -3, 0, -1)
                v(0, 0, 0, 0, 0)
            }.element,
            mat3.ref().element
        )
    }

    @Test
    fun `test rref`() {
        assertEquals(
            mat {
                v(1, 0, 0, 1, 0)
                v(0, 1, 0, 0, 0)
                v(0, 0, 1, 0, 1.0/3)
                v(0, 0, 0, 0, 0)
            }.element,
            mat3.rref().element
        )
    }

    @Test
    fun `test luDecompose`(){

        val copy = mat9.copy()

        val luDecomposeValue = copy.luDecompose()
        assertEquals(luDecomposeValue.first.element, mat{
            v(1,0,0)
            v(2,1,0)
            v(3,0,1)
        }.element)
        
        assertEquals(luDecomposeValue.second.element, mat{
            v(1,2,3)
            v(0,0,0)
            v(0,0,0)
        }.element)

        val restore = (luDecomposeValue.first * luDecomposeValue.second)

        assertEquals(
            restore.element,
            mat{
               v(1.0,2.0,3.0)
                v(2.0,4.0,6.0)
                v(3.0,6.0,9.0)
            }.element,
        )
    }

    @Test
    fun `test solve`() {
        mat {
            v(1, 0, 0)
            v(0, 1, 0)
            v(0, 0, 1)
        }.solve(
            mat
            {
                v(1)
                v(1)
                v(1)
            })

    }

    @Test
    fun `test sum`() {
        assertEquals(mat1.sum(), 10.0)
    }

    @Test
    fun `test mean`() {
        assertEquals(mat1.mean(), 2.5)
    }

    @Test
    fun `test max`() {
        assertEquals(mat1.max(), 4.0)
    }

    @Test
    fun `test min`() {
        assertEquals(mat1.min(), 1.0)
    }

    @Test
    fun `test appendCol`() {
        var mat4 = mat1.copy()
        mat4.appendColumn(mutableListOf(3.0, 5.0))
        assertEquals(mat4.element, mat {
            v(1, 2, 3)
            v(3, 4, 5)
        }.element)
    }

    @Test
    fun `test appendCol2`() {
        var mat4 = mat1.copy()
        mat4.appendColumn(3.0, 5.0)
        assertEquals(mat4.element, mat {
            v(1, 2, 3)
            v(3, 4, 5)
        }.element)
    }

    @Test
    fun `test print`() {
        mat1.print()
    }
}