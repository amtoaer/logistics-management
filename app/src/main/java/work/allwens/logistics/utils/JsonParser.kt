package work.allwens.logistics.utils

import org.json.JSONArray
import org.json.JSONObject
import work.allwens.logistics.data.model.Waybill

object JsonParser {
    fun parse(input: String): List<Waybill> {
        val result = ArrayList<Waybill>()
        val jsonArray = JSONObject(input).getJSONArray("waybillRecord")
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val waybill = Waybill()
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
            result.add(waybill)
        }
        return result
    }
}