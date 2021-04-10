package work.allwens.logistics.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// 为了方便维护用户activity集合而编写的公共基类，其他activity均继承于该父类
open class BaseActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Helper.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Helper.removeActivity(this)
    }
}