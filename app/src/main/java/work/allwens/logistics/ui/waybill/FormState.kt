package work.allwens.logistics.ui.waybill

data class FormState(
    var toError: Int? = null,
    var goodsNameError: Int? = null,
    var goodsCountError: Int? = null,
    var isDataValid: Boolean = false
)
