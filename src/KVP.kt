import java.io.Serializable

/**
 * Key Value Pair
 * */
data class KVP<K,V>(private val key: K, private val value: V) : Serializable


object KPool{

}