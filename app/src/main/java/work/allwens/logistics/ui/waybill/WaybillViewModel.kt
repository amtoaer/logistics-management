package work.allwens.logistics.ui.waybill

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import work.allwens.logistics.data.LoginRepository
import work.allwens.logistics.data.WaybillRepository
import work.allwens.logistics.data.model.Waybill
import work.allwens.logistics.ui.login.LoginViewModel
import java.lang.IllegalArgumentException

class WaybillViewModel(private val waybillRepository: WaybillRepository) : ViewModel() {
    val allWaybills: LiveData<List<Waybill>> = waybillRepository.allWaybills.asLiveData()

    fun insert(waybill: Waybill) {
        viewModelScope.launch {
            waybillRepository.insert(waybill)
        }
    }
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