package work.allwens.logistics.ui.waybill

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
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
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        // 根据页面跳转的传参调整该页面的显示策略
        when (intent.getIntExtra("type", 0)) {
            // 0表示查看本地订单
            0 -> waybillViewModel.allWaybills.observe(this@WaybillShowActivity, { waybills ->
                waybills.let { adapter.submitList(it) }
            })
            // 1表示查看XML网络订单
            1 -> loadNetWorkWaybills { waybillViewModel.requestXml() }
            // 2表示查看JSON网络订单
            2 -> loadNetWorkWaybills { waybillViewModel.requestJson() }
        }
    }

    private fun loadNetWorkWaybills(load: () -> Unit) {
        val loading = findViewById<ProgressBar>(R.id.loading)
        val adapter = findViewById<RecyclerView>(R.id.list).adapter as WaybillAdapter
        loading.visibility = View.VISIBLE
        waybillViewModel.requestResult.observe(this, Observer {
            val requestResult = it ?: return@Observer
            if (requestResult.success) {
                loading.visibility = View.INVISIBLE
                adapter.submitList(waybillViewModel.allNetWorkWaybills)
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.requestFailed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        )
        load()
    }
}