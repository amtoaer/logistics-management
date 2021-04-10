package work.allwens.logistics.data

import work.allwens.logistics.data.model.User
import java.io.IOException

class DataSource {
    fun login(username:String,password:String): Result<User> {
        // 这里理论上应该做一些校验，以及如果登陆成功，将登陆信息持久化
        try {
            return Result.Success(User(username, password))
        }catch(e:Throwable){
            return Result.Error(IOException("Error logging in, $e"))
        }
    }
    fun logout(){
        // 这里需要从本地缓存中清除登陆信息，暂且略去
    }
}