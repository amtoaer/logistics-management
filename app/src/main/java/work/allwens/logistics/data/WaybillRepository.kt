package work.allwens.logistics.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import work.allwens.logistics.data.dao.WaybillDao
import work.allwens.logistics.data.model.Waybill

class WaybillRepository(private val waybillDao: WaybillDao) {
    val allWaybills: Flow<List<Waybill>> = waybillDao.getAllWaybill()

    @WorkerThread
    suspend fun insert(waybill: Waybill) {
        waybillDao.insert(waybill)
    }
}