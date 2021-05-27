package work.allwens.logistics.ui.waybill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import work.allwens.logistics.R
import work.allwens.logistics.data.model.Waybill

// 定义数据和视图行的绑定
class WaybillAdapter : ListAdapter<Waybill, WaybillAdapter.WaybillHolder>(WaybillComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaybillHolder {
        return WaybillHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WaybillHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class WaybillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context = itemView.context
        private val transport: TextView = itemView.findViewById(R.id.transport)
        private val goodsInfo: TextView = itemView.findViewById(R.id.goods_info)
        private val no: TextView = itemView.findViewById(R.id.no)
        private val peopleInfo: TextView = itemView.findViewById(R.id.people_info)
        private val paid: TextView = itemView.findViewById(R.id.paid)
        private val toPay: TextView = itemView.findViewById(R.id.to_pay)

        fun bind(waybill: Waybill) {
            transport.text = context.getString(
                R.string.transportInfo,
                waybill.fromStation,
                waybill.toStation
            )
            goodsInfo.text = context.getString(
                R.string.goodsInfo,
                waybill.goodsName,
                waybill.goodsCount.toString()
            )
            no.text = context.getString(
                R.string.no,
                waybill.no
            )
            peopleInfo.text = context.getString(
                R.string.peopleInfo,
                waybill.toName,
                waybill.toTel
            )
            toPay.text = context.getString(
                R.string.toPayInfo,
                waybill.toPayMoney.toString()
            )
            paid.text = context.getString(
                R.string.paidInfo,
                waybill.paidMoney.toString()
            )
        }

        companion object {
            fun create(parent: ViewGroup): WaybillHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WaybillHolder(view)
            }
        }
    }

    class WaybillComparator : DiffUtil.ItemCallback<Waybill>() {
        override fun areItemsTheSame(oldItem: Waybill, newItem: Waybill): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Waybill, newItem: Waybill): Boolean {
            return oldItem == newItem
        }
    }
}
