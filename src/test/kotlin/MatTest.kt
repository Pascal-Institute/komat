import komat.mat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class MatTest {

    val mat1 = mat{
        v(1,2)
        v(3,4)
    }

    val mat2 = mat{
        v(4, 3)
        v(2, 1)
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
    fun `test plus`(){
        assertEquals(
            mat{
                v(5,5)
                v(5,5)
            }.element,
            mat1.plus(mat2).element
        )
    }

    @Test
    fun `test minus`(){
        assertEquals(
            mat{
                v(-3,-1)
                v(1,3)
            }.element,
            mat1.minus(mat2).element
        )
    }

    @Test
    fun `test exchange rows`(){
        assertEquals(mat{
            v(3, 4)
            v(1,2 )
        }.element,
            mat1.exchangeRow(0,1).element
            )
    }

    @Test
    fun `test transpose`(){
        assertEquals(mat{
            v(1,2)
            v(3,4)
            v(5,6)
        }.transpose().element,
            mat {
                v(1,3,5)
                v(2,4,6)
            }.element
        )
    }
}