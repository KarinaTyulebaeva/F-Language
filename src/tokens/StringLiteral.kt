package tokens

class StringLiteral(private val literalValue: String) : LiteralToken(literalValue) {
    override fun getValue(): String {
        return literalValue
    }

    override val literalType: LiteralType
        get() = LiteralType.STRING
}