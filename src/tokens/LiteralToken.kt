package tokens

abstract class LiteralToken : Token {
    enum class LiteralType {
        STRING, NUMBER, BOOLEAN, COMPOSITE, UNIT, RETURN
    }

    protected var literal: String? = null

    internal constructor(literalValue: String?) {
        literal = literalValue
    }

    internal constructor() {}

    override fun isLiteral(): Boolean {
        return true
    }

    override fun toString(): String {
        return String.format("Literal [%s]", this.value.toString())
    }

    abstract val literalType: LiteralType?
}