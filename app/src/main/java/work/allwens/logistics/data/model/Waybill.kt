package work.allwens.logistics.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "t_waybill")
data class Waybill(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "waybill_id") var id: Int = 0,
    @ColumnInfo(name = "waybill_no") var no: String = "",
    @ColumnInfo(name = "from_station") var fromStation: String = "",
    @ColumnInfo(name = "to_station") var toStation: String = "",
    @ColumnInfo(name = "from_name") var fromName: String = "",
    @ColumnInfo(name = "from_tel") var fromTel: String = "",
    @ColumnInfo(name = "to_name") var toName: String = "",
    @ColumnInfo(name = "to_tel") var toTel: String = "",
    @ColumnInfo(name = "goods_name") var goodsName: String = "",
    @ColumnInfo(name = "goods_count") var goodsCount: String = "",
    @ColumnInfo(name = "paid_money") var paidMoney: String = "",
    @ColumnInfo(name = "to_pay_money") var toPayMoney: String = ""
)
