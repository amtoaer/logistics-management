package work.allwens.logistics.ui.waybill

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import work.allwens.logistics.LogisticApplication
import work.allwens.logistics.R
import work.allwens.logistics.ui.BaseActivity

class WaybillShowActivity : BaseActivity() {
    private val waybillViewModel: WaybillViewModel by viewModels {
        WaybillViewModelFactory((application as LogisticApplication).waybillRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val recyclerView: RecyclerView = findViewById(R.id.list)
        val adapter = WaybillAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        // 根据页面跳转的传参调整该页面的显示策略
        when (intent.getIntExtra("type", 0)) {
            // 0表示查看本地订单
            0 -> waybillViewModel.allWaybills.observe(this@WaybillShowActivity, { waybills ->
                waybills.let { adapter.submitList(it) }
            })
            // 1表示查看XML网络订单
            1 -> {
                Toast.makeText(applicationContext, "开始请求", Toast.LENGTH_SHORT).show()
                waybillViewModel.requestResult.observe(this, Observer {
                    val requestResult = it ?: return@Observer
                    if (requestResult.success) {
                        Toast.makeText(applicationContext, "请求成功", Toast.LENGTH_SHORT).show()
                        adapter.submitList(waybillViewModel.allNetWorkWaybills)
                    } else {
                        Toast.makeText(applicationContext, "请求失败", Toast.LENGTH_SHORT).show()
                    }
                }
                )
                waybillViewModel.requestXml()
            }
            // 2表示查看JSON网络订单
            2 -> {
                Toast.makeText(applicationContext, "开始请求", Toast.LENGTH_SHORT).show()
                waybillViewModel.requestResult.observe(this, Observer {
                    val requestResult = it ?: return@Observer
                    if (requestResult.success) {
                        Toast.makeText(applicationContext, "请求成功", Toast.LENGTH_SHORT).show()
                        adapter.submitList(waybillViewModel.allNetWorkWaybills)
                    } else {
                        Toast.makeText(applicationContext, "请求失败", Toast.LENGTH_SHORT).show()
                    }
                }
                )
                waybillViewModel.requestJson()
            }
        }
    }
}