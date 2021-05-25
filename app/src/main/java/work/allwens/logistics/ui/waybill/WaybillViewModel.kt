package work.allwens.logistics.ui.waybill

import work.allwens.logistics.R
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import work.allwens.logistics.data.WaybillRepository
import work.allwens.logistics.data.model.Waybill
import work.allwens.logistics.utils.JsonParser
import work.allwens.logistics.utils.XmlParser
import java.lang.IllegalArgumentException

class WaybillViewModel(private val waybillRepository: WaybillRepository) : ViewModel() {
    // 本地的全部订单信息
    val allWaybills: LiveData<List<Waybill>> = waybillRepository.allWaybills.asLiveData()

    var allNetWorkWaybills: List<Waybill>? = null

    // 添加订单的表单状态
    private val _formState = MutableLiveData<FormState>()
    val formState: LiveData<FormState> = _formState

    // 本地订单的添加结果
    private val _addResult = MutableLiveData<Result>()
    val addResult: LiveData<Result> = _addResult

    // 网络订单的请求结果
    private val _requestResult = MutableLiveData<Result>()
    val requestResult: LiveData<Result> = _requestResult

    private val client = OkHttpClient()


    // 请求XML数据
    fun requestXml() {
        viewModelScope.launch(IO) {
            val request =
                Request.Builder().url("http://60.12.122.142:6080/simulated-Waybills-db.xml").build()
            try {
                val resp = client.newCall(request).execute()
                allNetWorkWaybills = XmlParser.parse(resp.body!!.byteStream())
                launch(Main) {
                    _requestResult.value = Result(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Main) {
                    _requestResult.value = Result(false)
                }
            }
        }
    }

    // 请求JSON数据
    fun requestJson() {
        viewModelScope.launch(IO) {
            val request =
                Request.Builder().url("http://60.12.122.142:6080/simulated-Waybills-db.json")
                    .build()
            try {
                val resp = client.newCall(request).execute()
                allNetWorkWaybills = JsonParser.parse(resp.body!!.string())
                launch(Main) {
                    _requestResult.value = Result(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Main) {
                    _requestResult.value = Result(false)
                }
            }
        }
    }

    // 插入本地订单
    fun insert(waybill: Waybill) {
        viewModelScope.launch {
            _addResult.value = waybillRepository.insert(waybill)
        }
    }

    // 检查添加订单表格中的必填项
    fun checkForm(to: String, name: String, count: String) {
        val currentFormState = FormState(null, null, null, false)
        if (!isToValid(to)) {
            currentFormState.toError = R.string.toError
        }
        if (!isNameValid(name)) {
            currentFormState.goodsNameError = R.string.nameError
        }
        if (!isCountValid(count)) {
            currentFormState.goodsCountError = R.string.countError
        }
        if (currentFormState.goodsCountError == null && currentFormState.goodsNameError == null && currentFormState.toError == null) {
            currentFormState.isDataValid = true
        }
        _formState.value = currentFormState
    }

    // 到站是否为空
    private fun isToValid(to: String): Boolean = to.isNotBlank()

    // 商品名称是否为空
    private fun isNameValid(name: String): Boolean = name.isNotBlank()

    // 商品数量是否为空
    private fun isCountValid(count: String): Boolean = count.isNotBlank()
}

class WaybillViewModelFactory(private val repository: WaybillRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaybillViewModel::class.java)) {
            return WaybillViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}