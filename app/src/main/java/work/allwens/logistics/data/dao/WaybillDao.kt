package work.allwens.logistics.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import work.allwens.logistics.data.model.Waybill

@Dao
interface WaybillDao {
    @Query("SELECT * FROM t_waybill")
    fun getAllWaybill(): Flow<List<Waybill>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(waybill: Waybill)

    @Query("DELETE FROM t_waybill")
    suspend fun deleteAll()
}