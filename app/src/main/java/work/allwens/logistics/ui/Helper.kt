package work.allwens.logistics.ui

import android.util.ArraySet
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

// 维护activity集合的辅助单例类
object Helper {
    // 为了方便管理，选用了集合
    private val activityList = ArraySet<AppCompatActivity>()

    // 添加activity
    fun addActivity(item: AppCompatActivity) {
        activityList.add(item)
    }

    // 移除activity并销毁
    fun removeActivity(item: AppCompatActivity) {
        if (activityList.contains(item)) {
            activityList.remove(item)
            item.finish()
        }
    }

    // 退出程序
    fun exitTheApp() {
        for (item in activityList) {
            item.finish()
        }
    }
}