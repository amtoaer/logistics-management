package work.allwens.logistics.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "t_waybill")
data class Waybill(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "waybill_id") val id: Int,
    @ColumnInfo(name = "from_station") val fromStation: String,
    @ColumnInfo(name = "to_station") val toStation: String,
    @ColumnInfo(name = "from_name") val fromName: String,
    @ColumnInfo(name = "from_tel") val fromTel: String,
    @ColumnInfo(name = "to_name") val toName: String,
    @ColumnInfo(name = "to_tel") val toTel: String,
    @ColumnInfo(name = "goods_name") val goodsName: String,
    @ColumnInfo(name = "goods_count") val goodCount: Int,
    @ColumnInfo(name = "paid_money") val paidMoney: Int,
    @ColumnInfo(name = "to_pay_money") val toPayMoney: Int
)
