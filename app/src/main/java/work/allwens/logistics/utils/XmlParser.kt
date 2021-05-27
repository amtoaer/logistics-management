package work.allwens.logistics.utils

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import work.allwens.logistics.data.model.Waybill
import java.io.IOException
import java.io.InputStream
import kotlin.jvm.Throws

object XmlParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Waybill> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setInput(inputStream, null)
            return parseData(parser)
        }
    }

    private fun parseData(parser: XmlPullParser): List<Waybill> {
        val waybills = ArrayList<Waybill>()
        var waybill: Waybill? = null
        var eventType = parser.eventType
        var text = ""
        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tagName = parser.name
            when (eventType) {
                //遇到标签名是订单记录则新建一个订单
                XmlPullParser.START_TAG -> if (tagName == "waybillRecord") {
                    waybill = Waybill()
                }
                //解析标签内容
                XmlPullParser.TEXT
                -> text = parser.text
                //根据不同的结束标签为订单填充数据（结束标签为订单记录时将该订单加入结果集）
                XmlPullParser.END_TAG -> when (tagName) {
                    "waybillNo" -> waybill!!.no = text
                    "consignor" -> waybill!!.fromName = text
                    "consignorPhoneNumber" -> waybill!!.fromTel = text
                    "consignee" -> waybill!!.toName = text
                    "consigneePhoneNumber" -> waybill!!.toTel = text
                    "transportationDepartureStation" -> waybill!!.fromStation = text
                    "transportationArrivalStation" -> waybill!!.toStation = text
                    "goodsName" -> waybill!!.goodsName = text
                    "numberOfPackages" -> waybill!!.goodsCount = text
                    "freightPaidByTheReceivingParty" -> waybill!!.toPayMoney = text
                    "freightPaidByConsignor" -> waybill!!.paidMoney = text
                    "waybillRecord" -> waybills.add(waybill!!)
                }
            }
            // 继续扫描
            eventType = parser.next()
        }
        return waybills
    }
}