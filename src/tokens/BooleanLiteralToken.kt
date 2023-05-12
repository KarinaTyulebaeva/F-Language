package tokens

class BooleanLiteralToken : LiteralToken {
    private var literalValue: Boolean

    constructor(rawString: String) : super(rawString) {
        literalValue = rawString == "true"
    }

    constructor(literalValue: Boolean) : super() {
        this.literalValue = literalValue
    }

    override fun getValue(): Boolean {
        return literalValue
    }

    override fun getLiteralType(): LiteralType {
        return LiteralType.BOOLEAN
    }
}