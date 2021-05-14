package work.allwens.logistics.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "t_user")
@Parcelize
data class User(@PrimaryKey(autoGenerate = true) @ColumnInfo(name="user_id") val id:Int,
                @ColumnInfo(name="user_department") val department:String,
                @ColumnInfo(name="user_name") val username:String,
                @ColumnInfo(name="user_login") val login:String,
                @ColumnInfo(name="user_passwd") val password:String,
                @ColumnInfo(name="user_tel") val telephone:Int):Parcelable
