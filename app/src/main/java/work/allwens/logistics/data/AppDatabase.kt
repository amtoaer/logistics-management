package work.allwens.logistics.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import work.allwens.logistics.data.dao.UserDao
import work.allwens.logistics.data.dao.WaybillDao
import work.allwens.logistics.data.model.User
import work.allwens.logistics.data.model.Waybill


// room数据库实现
@Database(entities = [User::class, Waybill::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun waybillDao(): WaybillDao

    // 类似于java中的静态对象和方法
    companion object {
        // 单例模式且保证线程安全
        @Volatile
        private var Instance: AppDatabase? = null

        // 数据库的回调，用于插入初始数据
        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Instance?.let { database ->
                    scope.launch {
                        var userDao = database.userDao()
                        var waybillDao = database.waybillDao()
                        // 删除原有数据
                        userDao.deleteAll()
                        waybillDao.deleteAll()
                        // 插入初始帐号数据
                        userDao.insert(
                            User(
                                1,
                                "计算机1802",
                                "刘文兴",
                                "20184402",
                                "12345678",
                                "12365413540",
                                "沈阳"
                            )
                        )
                        waybillDao.insert(
                            Waybill(
                                1,
                                "X10001",
                                "保定",
                                "沈阳",
                                "刘某",
                                "12345678912",
                                "王某",
                                "12378965412",
                                "沙发",
                                "1",
                                "20",
                                "50"
                            )
                        )
                        waybillDao.insert(
                            Waybill(
                                2,
                                "S10002",
                                "南通",
                                "沈阳",
                                "刘某",
                                "12345678912",
                                "张某",
                                "12378975412",
                                "电视",
                                "1",
                                "1000",
                                "2000"
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
                // 随便进行一些操作以执行回调
                scope.launch {
                    instance.beginTransaction()
                    instance.endTransaction()
                }
                Instance = instance
                instance
            }
        }
    }
}