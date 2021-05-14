package work.allwens.logistics.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import work.allwens.logistics.data.dao.UserDao
import work.allwens.logistics.data.model.User


@Database(entities = [User::class],version = 1,exportSchema = false)
abstract class AppDatabase:RoomDatabase(){
    abstract fun userDao():UserDao
    // 类似于java中的静态对象和方法
    companion object{
        @Volatile
        private var Instance: AppDatabase?=null

        fun getDatabase(context:Context):AppDatabase{
            return Instance?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"application_db").build()
                Instance = instance
                instance
            }
        }
    }
}