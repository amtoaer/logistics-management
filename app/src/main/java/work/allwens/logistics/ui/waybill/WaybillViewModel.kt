package work.allwens.logistics.ui.waybill

import work.allwens.logistics.R
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import work.allwens.logistics.data.WaybillRepository
import work.allwens.logistics.data.model.Waybill
import work.allwens.logistics.ui.waybill.FormState
import java.lang.IllegalArgumentException

class WaybillViewModel(private val waybillRepository: WaybillRepository) : ViewModel() {
    val allWaybills: LiveData<List<Waybill>> = waybillRepository.allWaybills.asLiveData()

    private val _formState = MutableLiveData<FormState>()
    val formState: LiveData<FormState> = _formState

    private val _addResult = MutableLiveData<AddResult>()
    val addResult: LiveData<AddResult> = _addResult


    fun insert(waybill: Waybill) {
        viewModelScope.launch {
            _addResult.value = waybillRepository.insert(waybill)
        }
    }

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

    private fun isToValid(to: String): Boolean = to.isNotBlank()

    private fun isNameValid(name: String): Boolean = name.isNotBlank()

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