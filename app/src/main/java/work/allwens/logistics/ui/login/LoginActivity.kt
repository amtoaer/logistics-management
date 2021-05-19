package work.allwens.logistics.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import work.allwens.logistics.LogisticApplication
import work.allwens.logistics.R
import work.allwens.logistics.data.model.User
import work.allwens.logistics.ui.BaseActivity
import work.allwens.logistics.ui.Helper
import work.allwens.logistics.ui.main.MainActivity

class LoginActivity : BaseActivity() {
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((application as LogisticApplication).loginRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // 获取布局元素
        val account = findViewById<EditText>(R.id.account)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val exit = findViewById<Button>(R.id.exit)
        // 设置观察者，进行表单状态监听
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            login.isEnabled = loginState.isDataValid
            if (loginState.accountError != null) {
                account.error = getString(loginState.accountError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })
        // 设置登陆结果监听
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer
            if (loginResult.error != null) {
                showError(loginResult.error)
            }
            // 登陆成功
            if (loginResult.success != null) {
                showWelcome(loginResult.success)
                setResult(Activity.RESULT_OK)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
        // 实时响应用户名密码的变化
        account.afterTextChanged {
            loginViewModel.isDataValid(login.text.toString(), password.text.toString())
        }
        password.afterTextChanged {
            loginViewModel.isDataValid(login.text.toString(), password.text.toString())
        }
        // 登陆按钮触发登陆行为
        login.setOnClickListener {
            // 查看登陆结果
            loginViewModel.login(login.text.toString(), password.text.toString())
        }
        // 退出按钮销毁所有activity
        exit.setOnClickListener {
            Helper.exitTheApp()
        }
    }

    private fun showError(@StringRes message: Int) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun showWelcome(model: User) {
        val welcome = getString(R.string.welcomeMessage)
        val username = model.username
        Toast.makeText(
            applicationContext,
            "$welcome $username",
            Toast.LENGTH_LONG
        ).show()
    }
}

// 为文本框扩展文本监听方法，便于使用
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}