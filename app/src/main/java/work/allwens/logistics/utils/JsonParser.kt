package work.allwens.logistics.utils

import org.json.JSONArray
import org.json.JSONObject
import work.allwens.logistics.data.model.Waybill

object JsonParser {
    fun parse(input: String): List<Waybill> {
        val result = ArrayList<Waybill>()
        // 获取JSON数据数组
        val jsonArray = JSONObject(input).getJSONArray("waybillRecord")
        // 遍历数组
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            // 取相应数据内容构造订单
            val waybill = Waybill()
            waybill.no = jsonObject.get("waybillNo") as String
            waybill.fromName = jsonObject.get("consignor") as String
            waybill.fromTel = jsonObject.get("consignorPhoneNumber") as String
            waybill.toName = jsonObject.get("consignee") as String
            waybill.toTel = jsonObject.get("consigneePhoneNumber") as String
            waybill.fromStation = jsonObject.get("transportationDepartureStation") as String
            waybill.toStation = jsonObject.get("transportationArrivalStation") as String
            waybill.goodsName = jsonObject.get("goodsName") as String
            waybill.goodsCount = (jsonObject.get("numberOfPackages") as Integer).toString()
            waybill.toPayMoney =
                (jsonObject.get("freightPaidByTheReceivingParty") as Integer).toString()
            waybill.paidMoney = (jsonObject.get("freightPaidByConsignor") as Integer).toString()
            // 将订单加入结果集中
            result.add(waybill)
        }
        return result
    }
}