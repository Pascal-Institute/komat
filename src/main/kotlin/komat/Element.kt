package komat

sealed class Element {
    data class DoubleElement(var value: Double) : Element() {
        override fun getValue(): Any = value

        override fun plus(value: Element) {
            this.value + value.getValue() as Double
        }

        override fun plusAssign(value: Element) {
            this.value += value.getValue() as Double
        }

        override fun minus(value: Element) {
            this.value - value.getValue() as Double
        }

        override fun minusAssign(value: Element) {
            this.value -= value.getValue() as Double
        }

        override fun times(value: Element): Element {
            return Element((getValue() as Double) * value.getValue() as Double)
        }

        override fun div(value: Element): Element {
            return Element(getValue() as Double / value.getValue() as Double)
        }

        override fun unaryMinus(): Element {
            return Element(-(getValue() as Double))
        }
    }

    data class ByteElement(var value: Byte) : Element() {
        override fun getValue(): Any = value

        override fun plus(value: Element) {
            this.value + value.getValue() as Byte
        }

        override fun plusAssign(value: Element) {
            this.value = (getValue() as Byte + value.getValue() as Byte).toByte()
        }

        override fun minus(value: Element) {
            this.value - value.getValue() as Byte
        }

        override fun minusAssign(value: Element) {
            this.value = (getValue() as Byte - value.getValue() as Byte).toByte()
        }

        override fun times(value: Element): Element {
            return Element((getValue() as Byte) * value.getValue() as Byte)
        }

        override fun div(value: Element): Element {
            return Element(getValue() as Byte / value.getValue() as Byte)
        }

        override fun unaryMinus(): Element {
            return Element(-(getValue() as Byte))
        }

    }

    data class BooleanElement(var value: Boolean) : Element(){
        override fun getValue(): Any = value

        override fun plus(value: Element) {
            this.value || value.getValue() as Boolean
        }

        override fun plusAssign(value: Element) {
            this.value = (getValue() as Boolean ||  value.getValue() as Boolean)
        }

        override fun minus(value: Element) {
           this.value || !(value.getValue() as Boolean)
        }

        override fun minusAssign(value: Element) {
            this.value = (getValue() as Boolean) || !(value.getValue() as Boolean)
        }

        override fun times(value: Element): Element {
            return Element((getValue() as Boolean) && value.getValue() as Boolean)
        }

        override fun div(value: Element): Element {
            return Element((getValue() as Boolean) && !(value.getValue() as Boolean))
        }

        override fun unaryMinus(): Element {
            return Element(!(getValue() as Boolean))
        }

    }

    companion object {
        operator fun invoke(value: Double): Element {
            return DoubleElement(value)
        }

        operator fun invoke(value: Number): Element {
            return DoubleElement(value.toDouble())
        }

        operator fun invoke(value: Byte): Element {
            return ByteElement(value)
        }

        operator fun invoke(value: Boolean): Element {
            return BooleanElement(value)
        }
    }

    fun toDouble() : Double {
        return (this.getValue() as Double)
    }

    fun toByte() : Byte {
        return (this.getValue() as Byte)
    }

    fun toBoolean() : Boolean{
        return (this.getValue() as Boolean)
    }

    abstract fun getValue(): Any

    override fun toString(): String {
        return when (this) {
            is DoubleElement -> "DoubleElement(value=$value)"
            is ByteElement -> "ByteElement(value=$value)"
            is BooleanElement -> "BooleanElement(value=$value)"
        }
    }

    abstract operator fun plus(value: Element)
    abstract operator fun plusAssign(value: Element)
    abstract operator fun minus(value: Element)
    abstract operator fun minusAssign(value: Element)
    abstract operator fun times(value: Element): Element
    abstract operator fun div(value: Element): Element
    abstract operator fun unaryMinus(): Element
}