package work.allwens.logistics.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import work.allwens.logistics.LogisticApplication
import work.allwens.logistics.R
import work.allwens.logistics.data.model.User
import work.allwens.logistics.ui.BaseActivity
import work.allwens.logistics.ui.Helper
import work.allwens.logistics.ui.login.LoginViewModel
import work.allwens.logistics.ui.login.LoginViewModelFactory
import work.allwens.logistics.ui.waybill.WaybillAddActivity
import work.allwens.logistics.ui.waybill.WaybillShowActivity

class MainActivity : BaseActivity() {
    private lateinit var currentUser: User
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((application as LogisticApplication).loginRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 取到登陆成功的用户
        currentUser = loginViewModel.getUser() as User
        // 获取视图元素
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val exit = findViewById<Button>(R.id.exit)
        val switchUser = findViewById<Button>(R.id.switchUser)
        val showLocalWaybill = findViewById<Button>(R.id.showLocalWaybill)
        val addLocalWaybill = findViewById<Button>(R.id.addLocalWaybill)
        val showXMLWaybill = findViewById<Button>(R.id.showXmlWaybill)
        val showJSONWaybill = findViewById<Button>(R.id.showJsonWaybill)
        // 设置用户名密码
        username.setText(getString(R.string.username, currentUser.username))
        password.setText(getString(R.string.password, currentUser.password))
        // 退出程序
        exit.setOnClickListener {
            Helper.exitTheApp()
        }
        // 返回上一页面
        switchUser.setOnClickListener {
            Helper.removeActivity(this)
        }
        // 展示本地订单
        showLocalWaybill.setOnClickListener {
            val intent = Intent(this, WaybillShowActivity::class.java)
            startActivity(intent)
        }
        // 添加本地订单
        addLocalWaybill.setOnClickListener {
            val intent = Intent(this, WaybillAddActivity::class.java)
            startActivity(intent)
        }
        // 展示XML订单
        showXMLWaybill.setOnClickListener {
            val intent = Intent(this, WaybillShowActivity::class.java)
            intent.putExtra("type", 1)
            startActivity(intent)
        }
        // 展示JSON订单
        showJSONWaybill.setOnClickListener {
            val intent = Intent(this, WaybillShowActivity::class.java)
            intent.putExtra("type", 2)
            startActivity(intent)
        }
    }
}