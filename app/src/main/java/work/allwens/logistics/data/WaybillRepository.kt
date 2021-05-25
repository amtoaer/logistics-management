package work.allwens.logistics.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import work.allwens.logistics.data.dao.WaybillDao
import work.allwens.logistics.data.model.Waybill
import work.allwens.logistics.ui.waybill.Result

class WaybillRepository(private val waybillDao: WaybillDao) {
    val allWaybills: Flow<List<Waybill>> = waybillDao.getAllWaybill()

    @WorkerThread
    suspend fun insert(waybill: Waybill): Result {
        return try {
            waybillDao.insert(waybill)
            Result(true)
        } catch (e: Exception) {
            Result(false)
        }
    }
}