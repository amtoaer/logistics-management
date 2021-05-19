package work.allwens.logistics.ui.login

// 登陆表单所处的状态
data class FormState(
    val accountError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
