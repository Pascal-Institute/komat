package komat

sealed class Element {
    data class DoubleElement(var value: Double) : Element() {
        override fun getValue(): Any = value
        override fun div(value: Element): Element {
            return Element(getValue() as Double / value.getValue() as Double)
        }

        override fun times(value: Element): Element {
            return Element((getValue() as Double) * value.getValue() as Double)
        }

        override fun plusAssign(value: Element) {
            this.value += value.getValue() as Double
        }

        override fun minusAssign(value: Element) {
            this.value -= value.getValue() as Double
        }

        override fun divAssign(value: Element) {
            this.value /= value.getValue() as Double
        }

        override fun unaryMinus(): Element {
            return Element(-(getValue() as Double))
        }
    }

    data class ByteElement(var value: Byte) : Element() {
        override fun getValue(): Any = value
        override fun div(value: Element): Element {
            return Element(getValue() as Byte / value.getValue() as Byte)
        }

        override fun times(value: Element): Element {
          return Element((getValue() as Byte) * value.getValue() as Byte)
        }

        override fun plusAssign(value: Element) {
            this.value = (getValue() as Byte + value.getValue() as Byte).toByte()
        }

        override fun minusAssign(value: Element) {
            this.value = (getValue() as Byte - value.getValue() as Byte).toByte()
        }

        override fun divAssign(value: Element) {
            this.value = (getValue() as Byte / value.getValue() as Byte).toByte()
        }

        override fun unaryMinus(): Element {
            return Element(-(getValue() as Byte))
        }

    }

    companion object {
        operator fun invoke(value: Double): Element {
            return DoubleElement(value)
        }

        operator fun invoke(value: Byte): Element {
            return ByteElement(value)
        }

        operator fun invoke(value: Number): Element {
            return DoubleElement(value.toDouble())
        }
    }

    fun toByte() : Byte {
        return (this.getValue() as Byte)
    }

    fun toDouble() : Double {
        return (this.getValue() as Double)
    }

    abstract fun getValue(): Any

    override fun toString(): String {
        return when (this) {
            is DoubleElement -> "DoubleElement(value=$value)"
            is ByteElement -> "ByteElement(value=$value)"
        }
    }

    abstract operator fun div(value: Element): Element
    abstract operator fun times(value: Element): Element
    abstract operator fun plusAssign(value: Element)
    abstract operator fun minusAssign(value: Element)
    abstract operator fun divAssign(value: Element)
    abstract operator fun unaryMinus(): Element
}