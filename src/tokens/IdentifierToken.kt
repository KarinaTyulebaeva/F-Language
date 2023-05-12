package tokens

class IdentifierToken(var identifier: String) : Token(identifier) {
    override fun isIdentifier(): Boolean {
        return true
    }

    override fun toString(): String {
        return String.format("Identifier [%s]", this.value)
    }

    override fun getValue(): String {
        return identifier
    }
}