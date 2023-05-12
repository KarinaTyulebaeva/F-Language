package tokens

abstract class Token {
    internal constructor() {}
    internal constructor(rawString: String?) {}

    open val isIdentifier: Boolean
        get() = false
    open val isLiteral: Boolean
        get() = false
    open val value: Any?
        get() = null
}