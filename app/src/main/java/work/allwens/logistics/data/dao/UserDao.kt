package work.allwens.logistics.data.dao

import androidx.room.Dao
import androidx.room.Query
import work.allwens.logistics.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM t_user WHERE user_login = :account AND user_passwd = :passwd")
    fun findByUserInfo(account:String,passwd:String):User
}