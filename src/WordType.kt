const val keyWord = 1
const val identifier = 2
const val delimiter = 3
const val number = 4
const val string = 5
const val operation = 6


object WordSet{

    public fun isKeyWord(word : String) = keyWordSet.contains(word)

    public fun isOperationSet(word : String) = operationSet.contains(word)

    private val operationSet = hashSetOf(
            "+",
            "-",
            "*",
            "/",
            "%",
            "+=",
            "-=",
            "*=",
            "/=",
            "%=",
            "++",
            "--"
    )

    private val keyWordSet = hashSetOf(
            // access control
            "public",
            "private",
            "protect",

            // class method variable
            "abstract",
            "class",
            "extends",
            "final",
            "implements",
            "interface",
            "native",
            "new",
            "static",
            "synchronized",
            "transient",
            "volatile",

            // control
            "break",
            "case",
            "continue",
            "default",
            "do",
            "else",
            "for",
            "if",
            "instanceof",
            "return",
            "switch",
            "while",

            // error handling
            "assert",
            "catch",
            "finally",
            "throw",
            "throws",
            "try",

            // package about
            "import",
            "package",

            // base type
            "boolean",
            "byte",
            "char",
            "double",
            "float",
            "int",
            "long",
            "short",
            "null",

            // variable references
            "super",
            "this",
            "void"
    )
}
