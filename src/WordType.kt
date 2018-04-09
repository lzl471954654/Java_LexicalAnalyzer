import java.io.File
import java.io.IOException
import java.nio.charset.Charset

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

lateinit var delimiterConfig : File
lateinit var keywordConfig : File
lateinit var operationConfig : File


object WordSet{

    fun initConfig(args:Array<String>){
        var flag = false
        args.forEachIndexed { index, s ->
            if (s == "-language"){
                val baseFile = File(args[index+1])
                if (!baseFile.exists())
                    throw IOException("Config file not exists!")
                delimiterConfig = File(baseFile.absolutePath+File.separator+"delimiter.txt")
                keywordConfig = File(baseFile.absolutePath+File.separator+"keyword.txt")
                operationConfig = File(baseFile.absolutePath+File.separator+"operation.txt")
                if ( !delimiterConfig.exists() || !keywordConfig.exists() || !operationConfig.exists()){
                    throw IOException("Config file note exists!")
                }
                flag = true
                return@forEachIndexed
            }
        }
        if (!flag){
            println("Config file not found!")
            System.exit(0)
        }

        keyWordSet = keywordConfig.readLines(Charset.forName("UTF-8")).toHashSet()
        operationSet = operationConfig.readLines(Charset.forName("UTF-8")).toHashSet()
        delimiterSet = delimiterConfig.readText(Charset.forName("UTF-8")).toHashSet()
    }

    public fun isKeyWord(word : String) = keyWordSet.contains(word)

    public fun isOperation(word : String) = operationSet.contains(word)

    public fun isOperation(word : Char) = isOperation("$word")

    public lateinit var delimiterSet : HashSet<Char>
    /*public val delimiterSet = hashSetOf(
            '(',
            ')',
            '[',
            ']',
            '{',
            '}',
            ';',
            ','
    )*/

    public val numberSet = '0'..'9'

    public val letterSet = createLetterSet()


    private fun createLetterSet() : HashSet<Char>{
        val set = HashSet<Char>()
        set += 'a'..'z'
        set += 'A'..'Z'
        set += '_'
        return set
    }

    public lateinit var operationSet : HashSet<String>

    /*public val operationSet = hashSetOf(
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
    )*/

    private lateinit var keyWordSet : HashSet<String>
    /*private val keyWordSet = hashSetOf(
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
    )*/
}
