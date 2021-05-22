package work.allwens.logistics.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_user")
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") val id: Int,
    @ColumnInfo(name = "user_department") val department: String,
    @ColumnInfo(name = "user_name") val username: String,
    @ColumnInfo(name = "user_account") val account: String,
    @ColumnInfo(name = "user_passwd") val password: String,
    @ColumnInfo(name = "user_tel") val telephone: String,
    @ColumnInfo(name = "user_loc") val location: String
)
