package tokens

class CompositeLiteral(value: ArrayList<LiteralToken>) : LiteralToken() {
    var literals = ArrayList<LiteralToken>()

    init {
        literals = value
    }

    override fun getLiteralType(): LiteralType {
        return LiteralType.COMPOSITE
    }

    override fun getValue(): ArrayList<LiteralToken> {
        return literals
    }
}