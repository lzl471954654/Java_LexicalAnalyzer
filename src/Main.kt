import java.io.File
import java.util.concurrent.LinkedBlockingQueue

lateinit var filePath : String
lateinit var srcFile : File
lateinit var targetFilePath : String
lateinit var outFile : File
val dataQueue = LinkedBlockingQueue<KVP<Int,Any>>(8192)
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

}