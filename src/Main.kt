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
    if (args.isEmpty()){
        println(noSrcFile)
        return
    }
    filePath = args[0]
    srcFile = File(filePath)
    if (srcFile.isDirectory){
        println(invalidFile)
        return
    }
    targetFilePath = "${srcFile.absolutePath}${File.separator}out.form"
    outFile = File(targetFilePath)
    if (!outFile.createNewFile()){
        println(createFileFailed)
        return
    }
    println("Start Lexical Analyze")
    initWorkThread()
}

fun initWorkThread(){
    writeThread.start()
    analyzerThread.start()
}

val analyzerThread = Thread {
    srcFile.forEachLine(Charset.forName("UTF-8")){

    }
}

val writeThread = Thread {
    val writer = ObjectOutputStream(outFile.outputStream())
    var count = 0
    writer.writeInt(count)
    while (running){
        val kvp = dataQueue.take()
        writer.writeObject(kvp)
        count++
    }
    try {
        writer.close()
    }catch (e:IOException){
        e.printStackTrace()
        println("IO Stream close failed")
    }
}