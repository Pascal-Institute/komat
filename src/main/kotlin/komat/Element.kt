package komat

sealed class Element {
    data class DoubleElement(val value: Double) : Element() {
        override fun getValue(): Any = value
    }
    data class NumberElement(val value: Number) : Element(){
        override fun getValue(): Any = value
    }

    companion object {
        operator fun invoke(value: Double): Element {
            return DoubleElement(value)
        }

        operator fun invoke(value: Number): Element {
            return NumberElement(value)
        }
    }

    // getValue()를 여기서 정의하지 않음
    abstract fun getValue(): Any

    override fun toString(): String {
        return when (this) {
            is DoubleElement -> "DoubleElement(value=$value)"
            is NumberElement -> "NumberElement(value=$value)"
        }
    }
}