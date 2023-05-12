package tokens

class NumberLiteral : LiteralToken {
    private var literalValue: Double? = null

    constructor(rawString: String) : super(rawString) {
        literalValue = rawString.toDouble()
    }

    constructor(value: Double) : super(value.toString()) {
        literalValue = value
    }

    constructor(value: LiteralToken) : super(value.toString()) {
        if (value.literalType === LiteralType.BOOLEAN) {
            literalValue = if ((value as BooleanLiteralToken).value) 0.0 else 1.0
        } else {
            literalValue = (value as NumberLiteral).value
        }
    }

    override fun getValue(): Double {
        return literalValue!!
    }

    override val literalType: LiteralType
        get() = LiteralType.NUMBER
}