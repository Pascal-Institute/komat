import komat.space.Vect
import komat.type.Padding
import kotlin.test.Test

class VectTest {
    val vect1 = Vect(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
    val vect2 = Vect(3.0, 2.0)
    val vect3 = Vect(0.toByte() ,1.toByte() ,2.toByte() ,3.toByte())
    val vect4 = Vect(255.toByte() ,254.toByte() ,253.toByte() ,252.toByte())

    @Test
    fun `test plus`(){
        (vect3 + vect4).print()
        (vect3 + vect4).elements.contentEquals(
            Vect(255.toByte(), 255.toByte(), 255.toByte(), 255.toByte()).elements
        )
    }

    @Test
    fun `test flip`() {
        vect2.flip().elements.contentEquals(
            Vect(6, 5, 4, 3, 2, 1).elements
        )
    }

    @Test
    fun `test pad`() {
        vect2.pad(Padding.MAX, 3).elements.contentEquals(
            Vect(3.0, 2.0, 3.0, 3.0, 3.0).elements
        )
    }

    @Test
    fun `test concat`() {

        vect1.concat(vect2).elements.contentEquals(
            Vect(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 3.0, 2.0).elements
        )

        vect1.print()

    }

    @Test
    fun `test convolve`() {

        vect1.convolve(vect2, 2).elements.contentEquals(
            Vect(7.0, 17.0, 27.0).elements
        )

    }

}
