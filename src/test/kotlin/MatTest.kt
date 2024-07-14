import komat.Converter.Companion.toVect
import komat.Converter.Companion.vectToMat
import komat.Generator.Companion.mat
import komat.space.Mat.Companion.times
import komat.Element
import komat.space.Vect
import komat.type.Axis
import komat.type.Padding
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class MatTest {

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

    val mat9 = mat {
        v(1, 2, 3)
        v(2, 4, 6)
        v(3, 6, 9)
    }

    val mat10 = mat {
        v(3.0 / 7, 2.0 / 7, 6.0 / 7)
        v(-6.0 / 7, 3.0 / 7, 2.0 / 7)
        v(2.0 / 7, 6.0 / 7, -3.0 / 7)
    }

    @Test
    fun `test toMat`() {
        val mutablelistVect = mutableListOf<Vect>()

        mutablelistVect.add(Vect(1, 1, 1, 1, 1))
        mutablelistVect.add(Vect(2, 2, 2, 2, 2))
        mutablelistVect.add(Vect(3, 3, 3, 3, 3))

        mutablelistVect.vectToMat().elements.contentEquals(
            mat {
                v(1, 1, 1, 1, 1)
                v(2, 2, 2, 2, 2)
                v(3, 3, 3, 3, 3)
            }.elements
        )
    }

    //TODO
    @Test
    fun `test toVect`() {
        val list = mat3.toVect()
        println(list)
    }

    @Test
    fun `test project`() {
        val a = Vect(2, 3, 4)
        val b = Vect(1, 0, 0)


        a.project(b).elements.contentEquals(
            Vect(2, 0, 0).elements
        )

    }

    @Test
    fun `test gramSchmidt`() {
        val u1 = Vect(3.0, 1.0)
        val u2 = Vect(2.0, 2.0)

        u2.gramSchmidt(u1).elements.contentEquals(
            Vect(-0.4, 1.2).elements
        )

    }

    @Test
    fun `test copy`() {

        val copy = mat1.copy()

        assertNotEquals(
            copy,
            mat1
        )

        mat1.elements.contentEquals(copy.elements)
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
        mat {
            v(5, 5)
            v(5, 5)
        }.elements.contentEquals(mat1.plus(mat2).elements)
    }

    @Test
    fun `test minus`() {
        mat {
            v(-3, -1)
            v(1, 3)
        }.elements.contentEquals(mat1.minus(mat2).elements)
    }

    @Test
    fun `test flip`() {
        mat1.flip(Axis.VERTICAL).elements.contentEquals(mat {
            v(3, 4)
            v(1, 2)
        }.elements)

        mat {
            v(1, 2)
            v(3, 4)
        }.flip(Axis.HORIZONTAL).elements.contentEquals(mat {
            v(2, 1)
            v(4, 3)
        }.elements)


    }

    @Test
    fun `test pad`() {
        mat9.pad(Padding.ZERO, 1).elements.contentEquals(
            mat {
                v(0.0, 0.0, 0.0, 0.0, 0.0)
                v(0.0, 1.0, 2.0, 3.0, 0.0)
                v(0.0, 4.0, 5.0, 6.0, 0.0)
                v(0.0, 7.0, 8.0, 9.0, 0.0)
                v(0.0, 0.0, 0.0, 0.0, 0.0)
            }.elements
        )
    }

    @Test
    fun `test scalar multiplication`() {

        (3.0 * mat1).elements.contentEquals(mat {
            v(3.0, 6.0)
            v(9.0, 12.0)
        }.elements)

    }

    @Test
    fun `test exchange column`() {

        mat1.exchangeColumn(0, 1).elements.contentEquals(mat {
            v(2, 1)
            v(4, 3)
        }.elements)

    }

    @Test
    fun `test exchange row`() {
        mat {
            v(3, 4)
            v(1, 2)
        }.elements.contentEquals(
            mat1.exchangeRow(0, 1).elements
        )
    }

    @Test
    fun `test adjugate`() {
        mat6.adjugate().elements.contentEquals(mat {
            v(-6, 8, 2)
            v(9, -10, -7)
            v(-3, -2, 1)
        }.elements)
    }

    @Test
    fun `test inverse`() {
        mat7.inverse().elements.contentEquals(mat {
            v(-1, -1, 0, 1)
            v(-1, 2, -1, 0)
            v(0, -1, 2, -1)
            v(1, 0, -1, 0.5)
        }.elements)

    }

    @Test
    fun `test transpose`() {
        mat {
            v(1, 2)
            v(3, 4)
            v(5, 6)
        }.transpose().elements.contentEquals(mat {
            v(1, 3, 5)
            v(2, 4, 6)
        }.elements)
    }

    @Test
    fun `test transpose twice`() {

        mat {
            v(1, 2)
            v(3, 4)
            v(5, 6)
        }.transpose().transpose().elements.contentEquals(
            mat {
                v(1, 2)
                v(3, 4)
                v(5, 6)
            }.elements
        )
    }

    @Test
    fun `test removeColumnAt`() {

        mat4.removeColumnAt(1).elements.contentEquals(mat {
            v(1, 0, 0)
            v(0, 3, 0)
            v(1, 3, 1)
            v(0, 0, 4)
        }.elements)
    }

    @Test
    fun `test removeRowAt`() {
        mat4.removeRowAt(2).elements.contentEquals(mat {
            v(1, 0, 0, 0)
            v(0, 2, 3, 0)
            v(0, 2, 0, 4)
        }.elements)
    }

    @Test
    fun `test removeAt`() {
        mat4.removeAt(2, 1).elements.contentEquals(mat {
            v(1, 0, 0)
            v(0, 3, 0)
            v(0, 0, 4)
        }.elements)
    }

    @Test
    fun `test concat`() {
        mat1.concat(
            mat { v(5, 6) }, Axis.HORIZONTAL
        ).elements.contentEquals(
            mat {
                v(1, 2)
                v(3, 4)
                v(5, 6)
            }.elements
        )

        mat2.concat(
            mat {
                v(5)
                v(6)
            }, Axis.VERTICAL
        ).elements.contentEquals(
            mat {
                v(4, 3, 5)
                v(2, 1, 6)
            }.elements
        )
    }

    @Test
    fun `test getColsInRange`() {
        mat {
            v(1, 2, 3)
            v(4, 5, 6)
        }.getColumnsInRange(0, 2).elements.contentEquals(
            mat {
                v(1, 2)
                v(4, 5)
            }.elements
        )
    }

    @Test
    fun `test det`() {
        assertEquals(
            mat5.det(), Element(115.0)
        )
    }

    @Test
    fun `test ref`() {

        mat {
            v(1, 1, 0, 1, 0)
            v(0, 2, 3, 0, 1)
            v(0, 0, -3, 0, -1)
            v(0, 0, 0, 0, 0)
        }.elements.contentEquals(mat3.ref().elements)


    }

    @Test
    fun `test rref`() {
        mat {
            v(1.0, 0.0, 0.0, 1.0, 0.0)
            v(0.0, 1.0, 0.0, 0.0, 0.0)
            v(0.0, 0.0, 1.0, 0.0, 1.0 / 3)
            v(0.0, 0.0, 0.0, 0.0, 0.0)
        }.elements.contentEquals(mat3.rref().elements)
    }

    @Test
    fun `test luDecompose`() {

        val copy = mat9.copy()

        val luDecomposeValue = copy.luDecompose()
        luDecomposeValue.first.elements.contentEquals(mat {
            v(1.0, 0.0, 0.0)
            v(2.0, 1.0, 0.0)
            v(3.0, 0.0, 1.0)
        }.elements)

        luDecomposeValue.second.elements.contentEquals(mat {
            v(1.0, 2.0, 3.0)
            v(0.0, 0.0, 0.0)
            v(0.0, 0.0, 0.0)
        }.elements)

        val restore = (luDecomposeValue.first * luDecomposeValue.second)

        restore.elements.contentEquals(mat {
            v(1.0, 2.0, 3.0)
            v(2.0, 4.0, 6.0)
            v(3.0, 6.0, 9.0)
        }.elements)
    }

    @Test
    fun `test solve`() {
        mat {
            v(1.0, 0.0, 0.0)
            v(0.0, 1.0, 0.0)
            v(0.0, 0.0, 1.0)
        }.solve(
            mat
            {
                v(1.0)
                v(1.0)
                v(1.0)
            })
    }

    @Test
    fun `test sum`() {
        assertEquals(mat1.sum(), Element(10.0))
    }

    @Test
    fun `test mean`() {
        assertEquals(mat1.mean(), Element(2.5))
    }

    @Test
    fun `test max`() {
        assertEquals(mat1.max(), Element(4.0))
    }

    @Test
    fun `test min`() {
        assertEquals(mat1.min(), Element(1.0))
    }

    @Test
    fun `test appendCol`() {
        var mat4 = mat1.copy()
        mat4.appendColumn(mutableListOf(3.0, 5.0))
        mat4.elements.contentEquals(mat {
            v(1, 2, 3)
            v(3, 4, 5)
        }.elements)
    }

    @Test
    fun `test print`() {
        mat1.print()
    }
}
