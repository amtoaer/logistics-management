package work.allwens.logistics

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import work.allwens.logistics.data.AppDatabase
import work.allwens.logistics.data.LoginRepository

class LogisticApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val loginRepository by lazy { LoginRepository(database.userDao()) }
}