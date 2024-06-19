import komat.space.Vect
import komat.type.Padding
import kotlin.test.Test

class VectTest {
    val vect1 = Vect(1.0,2.0,3.0,4.0,5.0,6.0)
    val vect2 = Vect(3.0,2.0)

    @Test
    fun `test pad`(){
        vect2.pad(Padding.MAX,3).element.contentEquals(
            Vect(3.0,2.0,3.0,3.0,3.0).element
        )
    }

    @Test
    fun `test concat`() {

        vect1.concat(vect2).element.contentEquals(
            Vect(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 3.0, 2.0).element
        )

    }

    @Test
    fun `test convolve`() {

        vect1.convolve(vect2, 2).element.contentEquals(
            Vect(7.0, 17.0, 27.0).element
        )

    }

}
