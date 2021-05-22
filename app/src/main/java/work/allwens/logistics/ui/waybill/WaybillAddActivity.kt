package work.allwens.logistics.ui.waybill

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
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