import java.io.*
import java.nio.charset.Charset
import java.util.concurrent.LinkedBlockingQueue

lateinit var filePath : String
lateinit var srcFile : File
lateinit var targetFilePath : String
lateinit var outFile : File
val dataQueue = LinkedBlockingQueue<KVP>(8192)

@Volatile
var running = true

fun main(args: Array<String>) {
    WordSet.initConfig(args)
    if ( args[0] == "-p"){
        printForm()
        return
    }
    if (args.isEmpty()){
        println(noSrcFile)
        return
    }
    filePath = args[0]
    srcFile = File(filePath)
    if (srcFile.isDirectory || !srcFile.exists()){
        println(invalidFile)
        return
    }
    targetFilePath = "${srcFile.parentFile.absolutePath}${File.separator}out.form"
    outFile = File(targetFilePath)
    println("outFilePath:${outFile.absolutePath}")
    if (outFile.exists())
        outFile.delete()
    if (!outFile.createNewFile()){
        println(createFileFailed)
        return
    }
    println("Start Lexical Analyze")
    initWorkThread()
}

fun printForm(){
    val file = File("out.form")
    if (!file.exists()){
        println(noSrcFile)
        return
    }
    var count = 0
    val input = ObjectInputStream(file.inputStream())
    try{
        while(true){
            println((input.readObject() as KVP).toString())
            count++
        }
    }catch (e:EOFException){

    }finally {
        println("Total Objects :$count")
        input.close()
    }
}

fun initWorkThread(){
    writeThread.start()
    analyzerThread.start()
}

val analyzerThread = Thread{
    var line = 1
    srcFile.forEachLine(Charset.forName("UTF-8")){
        val length = it.length
        var index = 0
        var superIndex = 1
        while (index<length){
            when(it[index]){
                in WordSet.numberSet->{
                    var pointFlag = false
                    val builder = StringBuilder()
                    while(index<length && (( it[index] in WordSet.numberSet ) || it[index] == '.' )){
                        if (it[index] == '.')
                            pointFlag = true
                        builder.append(it[index])
                        index++
                        superIndex++
                    }
                    superIndex = index
                    index--
                    try{
                        var type : Int
                        val number = if (pointFlag){
                            type = doubleNumber
                            builder.toString().toDouble()
                        }else if (superIndex<length && it[superIndex] == 'L'){
                            index = superIndex
                            superIndex++
                            type = longNumber
                            builder.toString().toLong()
                        }else{
                            type = integer
                            builder.toString().toInt()
                        }
                        val kvp = KVP.obtainKVP(type,number)
                        dataQueue.put(kvp)
                    }catch (e:NumberFormatException){
                        error("This is not a number! ",line,index)
                    }
                }
                in WordSet.letterSet->{
                    val builder = StringBuilder()
                    while ( index < length && WordSet.letterSet.contains(it[index])){
                        builder.append(it[index])
                        index++
                        superIndex++
                    }
                    index--
                    superIndex--
                    val str = builder.toString()
                    val type = if (WordSet.isKeyWord(str)){
                        keyWord
                    }else{
                        identifier
                    }
                    val kvp = KVP.obtainKVP(type,str)
                    dataQueue.put(kvp)
                }
                in WordSet.delimiterSet->{
                    val kvp = KVP.obtainKVP(delimiter,it[index])
                    dataQueue.put(kvp)
                }
                '\''->{
                    if (superIndex>=length){
                        error("missing character!",line,index)
                    }else{
                        val kvp = KVP.obtainKVP(singleWord,it[superIndex])
                        dataQueue.put(kvp)
                        index+=2
                        superIndex+=2
                    }
                }
                '\"'->{
                    if (superIndex>=length){
                        error("missing string in this line !",line,index)
                    }else{
                        val builder = StringBuilder()
                        while (superIndex<length && it[superIndex] != '\"'){
                            builder.append(it[superIndex])
                            index = superIndex
                            superIndex++
                        }
                        if (superIndex>=length){
                            error("missing the right \" in this line , please check it !",line,index)
                        }
                        val kvp = KVP.obtainKVP(string,builder.toString())
                        dataQueue.put(kvp)
                        index = superIndex
                        superIndex++
                    }
                }
                ' '->{}
                else->{
                    if (WordSet.isOperation(it[index])){
                        val op = if ( superIndex < length && WordSet.isOperation(it[superIndex])){
                            val str = "${it[index]}${it[superIndex]}"
                            index = superIndex
                            superIndex++
                            str
                        }else{
                            "${it[index]}"
                        }
                        val kvp = KVP.obtainKVP(operation,op)
                        dataQueue.put(kvp)
                    } else{
                        error("Unknown Character !",line,index)
                    }
                }
            }
            index++
            superIndex++
        }
        line++
    }
    running = false
}

fun error(msg:String,line:Int,index:Int){
    println("error[ line:$line , index:$index , $msg ]")
}

val writeThread = Thread {
    val writer = ObjectOutputStream(outFile.outputStream())
    var count = 0
    while (running || dataQueue.isNotEmpty()){
        val kvp = dataQueue.take()
        println(kvp.toString())
        writer.writeObject(kvp)
        count++
    }
    println("Total Objects :$count")
    try {
        writer.close()
    }catch (e:IOException){
        e.printStackTrace()
        println("IO Stream close failed")
    }
}