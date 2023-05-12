package tokens


class ReturnLiteral(var value: LiteralToken) : LiteralToken() {

    override val literalType: LiteralType
        get() = LiteralType.RETURN

    override fun getValue(): LiteralToken {
        return value
    }
}