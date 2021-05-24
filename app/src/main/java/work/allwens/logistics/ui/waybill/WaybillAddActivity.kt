package work.allwens.logistics.ui.waybill

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import work.allwens.logistics.LogisticApplication
import work.allwens.logistics.R
import work.allwens.logistics.data.model.User
import work.allwens.logistics.data.model.Waybill
import work.allwens.logistics.ui.BaseActivity
import work.allwens.logistics.ui.login.LoginViewModel
import work.allwens.logistics.ui.login.LoginViewModelFactory

class WaybillAddActivity : BaseActivity() {

    private val waybillViewModel: WaybillViewModel by viewModels {
        WaybillViewModelFactory((application as LogisticApplication).waybillRepository)
    }

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((application as LogisticApplication).loginRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_waybill)
        val from: EditText = findViewById(R.id.from)
        val to: EditText = findViewById(R.id.to)
        val fromPeople: EditText = findViewById(R.id.fromPeople)
        val fromTel: EditText = findViewById(R.id.fromTel)
        val toPeople: EditText = findViewById(R.id.toPeople)
        val toTel: EditText = findViewById(R.id.toTel)
        val goodsCount: EditText = findViewById(R.id.goodsCount)
        val goodsName: EditText = findViewById(R.id.goodsName)
        val paid: EditText = findViewById(R.id.paid)
        val toPay: EditText = findViewById(R.id.toPay)
        val back: Button = findViewById(R.id.back)
        val add: Button = findViewById(R.id.add)
        from.setText((loginViewModel.getUser() as User).location)

        waybillViewModel.formState.observe(this, Observer {
            val formState = it ?: return@Observer
            add.isEnabled = formState.isDataValid
            if (formState.toError != null) {
                to.error = getString(formState.toError!!)
            }
            if (formState.goodsNameError != null) {
                goodsName.error = getString(formState.goodsNameError!!)
            }
            if (formState.goodsCountError != null) {
                goodsCount.error = getString(formState.goodsCountError!!)
            }
        })

        waybillViewModel.addResult.observe(this, Observer {
            val result = it ?: return@Observer
            if (result.success) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.addSuccess),
                    Toast.LENGTH_SHORT
                ).show()
                this.exit()
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.addError),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        to.afterTextChanged {
            waybillViewModel.checkForm(
                to.text.toString(),
                goodsName.text.toString(),
                goodsCount.text.toString()
            )
        }

        goodsCount.afterTextChanged {
            waybillViewModel.checkForm(
                to.text.toString(),
                goodsName.text.toString(),
                goodsCount.text.toString()
            )
        }

        goodsName.afterTextChanged {
            waybillViewModel.checkForm(
                to.text.toString(),
                goodsName.text.toString(),
                goodsCount.text.toString()
            )
        }

        back.setOnClickListener {
            this.exit()
        }
        add.setOnClickListener {
            waybillViewModel.insert(
                Waybill(
                    0,
                    from.text.toString(),
                    to.text.toString(),
                    fromPeople.text.toString(),
                    fromTel.text.toString(),
                    toPeople.text.toString(),
                    toTel.text.toString(),
                    goodsName.text.toString(),
                    goodsCount.text.toString(),
                    paid.text.toString(),
                    toPay.text.toString()
                )
            )
        }
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