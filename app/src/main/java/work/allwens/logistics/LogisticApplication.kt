package work.allwens.logistics

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import work.allwens.logistics.data.AppDatabase
import work.allwens.logistics.data.LoginRepository
import work.allwens.logistics.data.WaybillRepository

class LogisticApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val loginRepository by lazy { LoginRepository(database.userDao()) }
    val waybillRepository by lazy { WaybillRepository(database.waybillDao()) }
}