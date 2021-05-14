package work.allwens.logistics.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import work.allwens.logistics.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM t_user WHERE user_login = :account AND user_passwd = :passwd")
    fun findByUserInfo(account: String, passwd: String): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("DELETE FROM t_user")
    suspend fun deleteAll()
}