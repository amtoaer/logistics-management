package work.allwens.logistics.ui.waybill

import android.os.Bundle
import androidx.activity.viewModels
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
        setContentView(R.layout.activity_show_local)
        val recyclerView: RecyclerView = findViewById(R.id.list)
        val adapter = WaybillAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        waybillViewModel.allWaybills.observe(this@WaybillShowActivity, { waybills ->
            waybills.let { adapter.submitList(it) }
        })
    }
}