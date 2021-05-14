package work.allwens.logistics.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import work.allwens.logistics.data.dao.UserDao
import work.allwens.logistics.data.model.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    // 类似于java中的静态对象和方法
    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Instance?.let { database ->
                    scope.launch {
                        var userDao = database.userDao()
                        // 删除原有数据
                        userDao.deleteAll()
                        // 插入初始数据
                        userDao.insert(
                            User(
                                1,
                                "计算机1802",
                                "刘文兴",
                                "20184402",
                                "12345678",
                                "12365413540"
                            )
                        )
                    }
                }
            }
        }

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "application_db"
                ).addCallback(AppDatabaseCallback(scope)).build()
                Instance = instance
                instance
            }
        }
    }
}