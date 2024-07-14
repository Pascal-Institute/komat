import komat.Generator.Companion.cube
import komat.Generator.Companion.mat
import komat.space.Cube
import org.junit.jupiter.api.Test

class CubeTest {

    val mat1 = mat {
        v(1, 2)
        v(3, 4)
    }
    val mat2 = mat {
        v(5, 6)
        v(7, 8)
    }

    @Test
    fun `test transpose`() {
        val cube = cube {
            +mat1
            +mat2
        }

        val cube1 = cube.copy()

        cube.transpose().transpose().elements.contentEquals(
            cube1.elements
        )
    }

    @Test
    fun `test generator`() {

        val cube2 = cube {
            +mat1
            +mat2
        }

        val cube3 = cube {
            +mat {
                v(1, 2, 3)
                v(4, 5, 6)
            }

            +mat {
                v(7, 8, 9)
                v(10, 11, 12)
            }

        }
    }

    @Test
    fun `test times`() {

        //1-1
        val cube1 = cube {
            +mat {
                v(1, 2)
                v(3, 4)
            }
            +mat {
                v(5, 6)
                v(7, 8)
            }
        }

        val cube2 = cube {
            +mat {
                v(9, 8)
                v(7, 6)
            }
            +mat {
                v(5, 4)
                v(3, 2)
            }
        }

        val cube3 = cube {
            +mat {
                v(23, 20)
                v(55, 48)
            }
            +mat {
                v(43, 32)
                v(99, 74)
            }

        }

        (cube1 * cube2).elements.contentEquals(cube3.elements)

        //1-2
        val cubeA = Cube(2, 2, 2)
        cubeA[0, 0, 0] = 1.0
        cubeA[0, 0, 1] = 2.0
        cubeA[0, 1, 0] = 3.0
        cubeA[0, 1, 1] = 4.0
        cubeA[1, 0, 0] = 5.0
        cubeA[1, 0, 1] = 6.0
        cubeA[1, 1, 0] = 7.0
        cubeA[1, 1, 1] = 8.0

        // Define the second 3D matrix (Cube B)
        val cubeB = Cube(2, 2, 2)
        cubeB[0, 0, 0] = 1.0
        cubeB[0, 0, 1] = 0.0
        cubeB[0, 1, 0] = 0.0
        cubeB[0, 1, 1] = 1.0
        cubeB[1, 0, 0] = 1.0
        cubeB[1, 0, 1] = 1.0
        cubeB[1, 1, 0] = 1.0
        cubeB[1, 1, 1] = 1.0

        // Perform matrix multiplication
        val resultCube = cubeA * cubeB
        (resultCube).elements.contentEquals(
            cube {
                +mat {
                    v(3, 2)
                    v(4, 7)
                }
                +mat {
                    v(11, 6)
                    v(8, 15)
                }
            }.elements
        )
    }

}
