import java.io.Serializable
import java.util.concurrent.LinkedBlockingQueue

/**
 * Key Value Pair
 * */
data class KVP(private var key: Int = -1, private var value: Any? = null) : Serializable{

    public fun recycle() = Companion.recycle(this)

    public data class ConstStringDesc(val startPosition : Long, val fileSize : Long)

    companion object {
        private val pool = LinkedBlockingQueue<KVP>()

        public fun obtainKVP(key: Int = -1,value: Any? = null):KVP{
            synchronized(pool){
                return if (pool.isEmpty()){
                    KVP(key,value)
                }else{
                    pool.take()
                }
            }
        }

        private fun recycle(kvp: KVP){
            kvp.key = -1
            kvp.value = null
            pool.put(kvp)
        }
    }
}