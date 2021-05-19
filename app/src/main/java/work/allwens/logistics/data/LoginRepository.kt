package work.allwens.logistics.data

import androidx.annotation.WorkerThread
import work.allwens.logistics.data.dao.UserDao
import work.allwens.logistics.data.model.User
import java.io.IOException

class LoginRepository(private val userDao: UserDao) {
    var currentUser: User? = null

    @WorkerThread
    suspend fun login(account: String, password: String): Result<User> {
        return when (val result = userDao.findByUserInfo(account, password)) {
            null -> {
                Result.Error(IOException())
            }
            else -> {
                this.currentUser = result
                Result.Success(result)
            }
        }
    }
}