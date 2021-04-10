package work.allwens.logistics.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import work.allwens.logistics.R
import work.allwens.logistics.data.model.User
import work.allwens.logistics.ui.BaseActivity
import work.allwens.logistics.ui.Helper

class MainActivity : BaseActivity() {
    private lateinit var currentUser: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 取到登陆成功的用户
        currentUser = intent.getParcelableExtra<User>("user") as User
        // 获取视图元素
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val exit = findViewById<Button>(R.id.exit)
        val switchUser = findViewById<Button>(R.id.switchUser)
        // 设置用户名密码
        username.setText("我的用户名为：${currentUser.username}")
        password.setText("我的密码为：${currentUser.password}")
        // 退出程序
        exit.setOnClickListener {
            Helper.exitTheApp()
        }
        // 返回上一页面
        switchUser.setOnClickListener {
            Helper.removeActivity(this)
        }
    }
}