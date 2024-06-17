import komat.space.Vect
import kotlin.test.Test

class VectTest {
    val vect1 = Vect(1.0,2.0,3.0,4.0,5.0,6.0)
    val vect2 = Vect(3.0,2.0)

    @Test
    fun `test convolve`() {

        vect1.convolve(vect2, 2).element.contentEquals(
            Vect(7.0, 17.0, 27.0).element
        )

    }

}
