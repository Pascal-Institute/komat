import komat.Mat
import komat.mat

fun main(args: Array<String>) {
    val matrix = mat {
        row(1.0, 2.0, 3.0)
        row(1.0, 3.0, 3.0)
        row(1.0, 2.0, 3.0)
        row(1.0, 2.0, 3.0)
    }

    println(matrix.row)
    println(matrix.col)
}