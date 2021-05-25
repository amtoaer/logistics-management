package work.allwens.logistics.ui.waybill

// 添加订单表单所处的状态
data class FormState(
    var toError: Int? = null,
    var goodsNameError: Int? = null,
    var goodsCountError: Int? = null,
    var isDataValid: Boolean = false
)
