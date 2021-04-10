package work.allwens.logistics.data

// 可携带任何类型结果的密封类
sealed class Result<out T:Any>{
    data class Success<out T:Any>(val data:T):Result<T>()
    data class Error(val exception:Exception):Result<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success<*> ->"Success[data=$data]"
            is Error->"Error[exception=$exception]"
        }
    }
}
