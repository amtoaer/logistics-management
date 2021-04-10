package work.allwens.logistics.ui.login

import work.allwens.logistics.data.model.User

// 返回登陆结果
data class LoginResult(val success: User?=null, val error:Int? = null)
