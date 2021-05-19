package work.allwens.logistics.ui.login

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import work.allwens.logistics.R
import work.allwens.logistics.data.LoginRepository
import work.allwens.logistics.data.Result
import work.allwens.logistics.data.model.User
import java.lang.IllegalArgumentException

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    // 登陆表单状态
    private val _formState = MutableLiveData<FormState>()
    val loginFormState: LiveData<FormState> = _formState

    // 登陆结果
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    // 登陆功能
    fun login(account: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.login(account, password)
            if (result is Result.Success) {
                _loginResult.value = LoginResult(success = result.data)
            } else {
                _loginResult.value = LoginResult(error = R.string.loginFailed)
            }
        }
    }

    // 数据校验功能
    fun isDataValid(account: String, password: String) {
        if (!isUserNameValid(account)) {
            _formState.value = FormState(accountError = R.string.accountInvalid)
        } else if (!isPasswordValid(password)) {
            _formState.value = FormState(passwordError = R.string.passwordInvalid)
        } else {
            _formState.value = FormState(isDataValid = true)
        }
    }

    // 获取当前登陆用户
    fun getUser(): User? = loginRepository.currentUser

    // 用户名不为空
    private fun isUserNameValid(username: String): Boolean = username.isNotBlank()

    // 密码长度大于等于8
    private fun isPasswordValid(password: String): Boolean = password.length >= 8
}

class LoginViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}