package tokens

class LeftBracket : Token() {
    override fun toString(): String {
        return "LBRK"
    }

    override fun getValue(): Any {
        return "LBRK"
    }
}