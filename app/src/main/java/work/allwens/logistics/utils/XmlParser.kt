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
                XmlPullParser.START_TAG -> if (tagName == "waybillRecord") {
                    waybill = Waybill()
                }
                XmlPullParser.TEXT
                -> text = parser.text
                XmlPullParser.END_TAG -> when (tagName) {
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
            eventType = parser.next()
        }
        return waybills
    }
}