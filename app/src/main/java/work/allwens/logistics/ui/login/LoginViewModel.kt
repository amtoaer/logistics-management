package work.allwens.logistics.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import work.allwens.logistics.R
import work.allwens.logistics.data.LoginRepository
import work.allwens.logistics.data.Result
import work.allwens.logistics.data.model.User

class LoginViewModel(private val loginRepository: LoginRepository):ViewModel() {
    // 登陆表单状态
    private val _formState = MutableLiveData<FormState>()
    val loginFormState:LiveData<FormState> = _formState
    // 登陆结果
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult:LiveData<LoginResult> = _loginResult
    // 登陆功能
    fun login(username:String,password:String){
        val result = loginRepository.login(username,password)
        if (result is Result.Success){
            _loginResult.value = LoginResult(success = result.data)
        }else {
            _loginResult.value = LoginResult(error = R.string.loginFailed)
        }
    }
    // 数据校验功能
    fun isDataValid(username:String,password:String){
        if (!isUserNameValid(username)){
            _formState.value = FormState(usernameError = R.string.usernameInvalid)
        }else if (!isPasswordValid(password)){
            _formState.value = FormState(passwordError = R.string.passwordInvalid)
        }else{
            _formState.value = FormState(isDataValid = true)
        }
    }
    // 获取当前登陆用户
    fun getUser():User? = loginRepository.currentUser
    // 用户名不为空
    private fun isUserNameValid(username:String):Boolean = username.isNotBlank()
    // 密码长度大于等于8
    private fun isPasswordValid(password:String):Boolean = password.length>=8
}