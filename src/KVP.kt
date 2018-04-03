import java.io.Serializable
import java.util.concurrent.LinkedBlockingQueue

/**
 * Key Value Pair
 * */
data class KVP(private var key: Int = -1, private var value: Any) : Serializable{

    public fun recycle() = Pool.recycle(this)

    override fun toString(): String {
        return "(${Type[key]}, $value)"
    }

    public data class ConstStringDesc(val startPosition : Long, val fileSize : Long) : Serializable

    companion object Pool : Serializable{
        private val pool = LinkedBlockingQueue<KVP>()

        public fun obtainKVP(key: Int = -1,value: Any):KVP{
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
            kvp.value = Unit
            pool.put(kvp)
        }
    }


}