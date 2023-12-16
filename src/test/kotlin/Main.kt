import komat.mat

fun main(args: Array<String>) {

    val mat = mat{
        row(1, 2, 3)
        row(4,5,6)
        row(7,8,9)
    }

    mat.exchangeRow(0, 2)
    mat.appendRow(1,1,1)
    mat.transpose()

    val matrix1 = mat {
        row(1.0, 1.0, 1.0)
        row(1.0, 1.0, 1.0)
    }

    val matrix2 = mat {
        row(2.0, 2.0, 2.0)
        row(2.0, 2.0, 2.0)
    }

    val matrix5 = mat{
        row(1, 1)
    }

    val matrix6 = mat{
        row(3)
        row(4)
    }

    println(matrix1.row)
    println(matrix1.col)

    val matrix3 = matrix1.plus(matrix2)
    val matrix4 = matrix2 + matrix3

    val matrix7 = matrix5 * matrix6

    println()

}