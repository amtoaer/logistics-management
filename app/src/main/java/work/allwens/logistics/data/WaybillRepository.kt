package work.allwens.logistics.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import work.allwens.logistics.R
import work.allwens.logistics.data.dao.WaybillDao
import work.allwens.logistics.data.model.Waybill
import work.allwens.logistics.ui.waybill.AddResult

class WaybillRepository(private val waybillDao: WaybillDao) {
    val allWaybills: Flow<List<Waybill>> = waybillDao.getAllWaybill()

    @WorkerThread
    suspend fun insert(waybill: Waybill): AddResult {
        return try {
            waybillDao.insert(waybill)
            AddResult(true)
        } catch (e: Exception) {
            AddResult(false)
        }
    }
}