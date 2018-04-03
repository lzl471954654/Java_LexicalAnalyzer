const val keyWord = 1
const val identifier = 2
const val delimiter = 3
const val integer = 4
const val string = 5
const val operation = 6
const val singleWord = 7
const val doubleNumber = 8
const val longNumber = 9

val Type = arrayOf("","keyWord","identifier","delimiter","integer",
        "string","operation","singleWord","doubleNumber","longNumber")

object WordSet{

    public fun isKeyWord(word : String) = keyWordSet.contains(word)

    public fun isOperation(word : String) = operationSet.contains(word)

    public fun isOperation(word : Char) = isOperation("$word")

    public val delimiterSet = hashSetOf(
            '(',
            ')',
            '[',
            ']',
            '{',
            '}',
            ';',
            ','
    )

    public val numberSet = '0'..'9'

    public val letterSet = createLetterSet()


    private fun createLetterSet() : HashSet<Char>{
        val set = HashSet<Char>()
        set += 'a'..'z'
        set += 'A'..'Z'
        set += '_'
        return set
    }

    public val operationSet = hashSetOf(
            "+",
            "-",
            "*",
            "/",
            "%",
            "=",
            "+=",
            "-=",
            "*=",
            "/=",
            "%=",
            "++",
            "--",
            "."
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
