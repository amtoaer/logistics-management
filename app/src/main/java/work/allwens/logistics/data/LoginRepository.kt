package work.allwens.logistics.data

import work.allwens.logistics.data.dao.UserDao
import work.allwens.logistics.data.model.User
import java.io.IOException

class LoginRepository(private val userDao: UserDao) {
    var currentUser: User? = null

    fun login(username: String, password: String): Result<User> {
        when (val result = userDao.findByUserInfo(username, password)) {
            null -> {
                return Result.Error(IOException())
            }
            else -> {
                this.currentUser = result
                return Result.Success(result)
            }
        }
    }
}