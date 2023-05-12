package tokens

class UnitLiteral : LiteralToken() {
    override val literalType: LiteralType
        get() = LiteralType.UNIT
}