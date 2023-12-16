import komat.mat
import org.junit.jupiter.api.Test
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
    fun testPlus(){
        assertEquals(
            mat{
                v(5,5)
                v(5,5)
            }.element,
            mat1.plus(mat2).element
        )
    }

    @Test
    fun testMinus(){
        assertEquals(
            mat{
                v(-3,-1)
                v(1,3)
            }.element,
            mat1.minus(mat2).element
        )
    }
}