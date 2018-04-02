import java.io.Serializable

/**
 * Key Value Pair
 * */
data class KVP<Integer,V>(private val key: Integer, private val value: V?) : Serializable

class KPool{

}