package work.allwens.logistics.data

import work.allwens.logistics.data.model.User

class LoginRepository(private val dataSource: DataSource) {
    var currentUser: User? = null
    val isLoggedIn: Boolean get() = currentUser != null

    init {
        // 此处适用于从缓存中读取历史登陆信息
        currentUser = null
    }

    fun login(username: String, password: String):Result<User> {
        val result = dataSource.login(username, password)
        if (result is Result.Success){
            this.currentUser = result.data
        }
        return result
    }

    fun logout(){
        this.currentUser=null
        dataSource.logout()
    }
}