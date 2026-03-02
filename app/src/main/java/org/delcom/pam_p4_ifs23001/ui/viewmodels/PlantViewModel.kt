package org.delcom.pam_p4_ifs23001.ui.viewmodels

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantData
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantDatapc
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponseProfile
import org.delcom.pam_p4_ifs23001.network.plants.service.IPlantRepository
import org.delcom.pam_p4_ifs23001.network.plants.service.IPlantpcRepository // Import repository PC
import javax.inject.Inject

sealed interface ProfileUIState {
    data class Success(val data: ResponseProfile) : ProfileUIState
    data class Error(val message: String) : ProfileUIState
    object Loading : ProfileUIState
}

sealed interface PlantsUIState {
    data class Success(val data: List<Any>) : PlantsUIState
    data class Error(val message: String) : PlantsUIState
    object Loading : PlantsUIState
}

sealed interface PlantUIState {
    data class Success(val data: Any) : PlantUIState
    data class Error(val message: String) : PlantUIState
    object Loading : PlantUIState
}

sealed interface PlantActionUIState {
    data class Success(val message: String) : PlantActionUIState
    data class Error(val message: String) : PlantActionUIState
    object Loading : PlantActionUIState
}

data class UIStatePlant(
    val profile: ProfileUIState = ProfileUIState.Loading,
    val plants: PlantsUIState = PlantsUIState.Loading,
    var plant: PlantUIState = PlantUIState.Loading,
    var plantAction: PlantActionUIState = PlantActionUIState.Loading
)

@HiltViewModel
@Keep
class PlantViewModel @Inject constructor(
    private val repository: IPlantRepository,
    private val repositorypc: IPlantpcRepository // Inject repository PC di sini
) : ViewModel() {
    private val _uiState = MutableStateFlow(UIStatePlant())
    val uiState = _uiState.asStateFlow()

    // ==========================================================
    // SEKSI TANAMAN STANDAR (Normal Plants)
    // ==========================================================

    fun getProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(profile = ProfileUIState.Loading) }
            val tmpState = runCatching {
                repository.getProfile()
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") ProfileUIState.Success(response.data!!)
                    else ProfileUIState.Error(response.message)
                },
                onFailure = { ProfileUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(profile = tmpState) }
        }
    }

    fun getAllPlants(search: String? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(plants = PlantsUIState.Loading) }
            val tmpState = runCatching {
                repository.getAllPlants(search)
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") PlantsUIState.Success(response.data!!.plants)
                    else PlantsUIState.Error(response.message)
                },
                onFailure = { PlantsUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(plants = tmpState) }
        }
    }

    fun postPlant(nama: RequestBody, deskripsi: RequestBody, manfaat: RequestBody, efekSamping: RequestBody, file: MultipartBody.Part) {
        viewModelScope.launch {
            _uiState.update { it.copy(plantAction = PlantActionUIState.Loading) }
            val tmpState = runCatching {
                repository.postPlant(nama, deskripsi, manfaat, efekSamping, file)
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") PlantActionUIState.Success(response.data!!.plantId)
                    else PlantActionUIState.Error(response.message)
                },
                onFailure = { PlantActionUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(plantAction = tmpState) }
        }
    }

    fun getPlantById(plantId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(plant = PlantUIState.Loading) }
            val tmpState = runCatching {
                repository.getPlantById(plantId)
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") PlantUIState.Success(response.data!!.plant)
                    else PlantUIState.Error(response.message)
                },
                onFailure = { PlantUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(plant = tmpState) }
        }
    }

    fun putPlant(plantId: String, nama: RequestBody, deskripsi: RequestBody, manfaat: RequestBody, efekSamping: RequestBody, file: MultipartBody.Part?) {
        viewModelScope.launch {
            _uiState.update { it.copy(plantAction = PlantActionUIState.Loading) }
            val tmpState = runCatching {
                repository.putPlant(plantId, nama, deskripsi, manfaat, efekSamping, file)
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") PlantActionUIState.Success(response.message)
                    else PlantActionUIState.Error(response.message)
                },
                onFailure = { PlantActionUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(plantAction = tmpState) }
        }
    }

    fun deletePlant(plantId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(plantAction = PlantActionUIState.Loading) }
            val tmpState = runCatching {
                repository.deletePlant(plantId)
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") PlantActionUIState.Success(response.message)
                    else PlantActionUIState.Error(response.message)
                },
                onFailure = { PlantActionUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(plantAction = tmpState) }
        }
    }

    // ==========================================================
    // SEKSI KOMPONEN PC (Plantspc) - Menggunakan repositorypc
    // ==========================================================

    fun getAllPlantspc(search: String? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(plants = PlantsUIState.Loading) }
            val tmpState = runCatching {
                repositorypc.getAllPlantspc(search) // Gunakan repositorypc
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") PlantsUIState.Success(response.data!!.plants)
                    else PlantsUIState.Error(response.message)
                },
                onFailure = { PlantsUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(plants = tmpState) }
        }
    }

    fun postPlantpc(nama: RequestBody, deskripsi: RequestBody, manfaat: RequestBody, efekSamping: RequestBody, file: MultipartBody.Part) {
        viewModelScope.launch {
            _uiState.update { it.copy(plantAction = PlantActionUIState.Loading) }
            val tmpState = runCatching {
                repositorypc.postPlantpc(nama, deskripsi, manfaat, efekSamping, file) // Gunakan repositorypc
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") PlantActionUIState.Success(response.data!!.plantIdpc)
                    else PlantActionUIState.Error(response.message)
                },
                onFailure = { PlantActionUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(plantAction = tmpState) }
        }
    }

    fun getPlantByIdpc(plantId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(plant = PlantUIState.Loading) }
            val tmpState = runCatching {
                repositorypc.getPlantByIdpc(plantId) // Gunakan repositorypc
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") PlantUIState.Success(response.data!!.plant)
                    else PlantUIState.Error(response.message)
                },
                onFailure = { PlantUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(plant = tmpState) }
        }
    }

    fun putPlantpc(plantId: String, nama: RequestBody, deskripsi: RequestBody, manfaat: RequestBody, efekSamping: RequestBody, file: MultipartBody.Part?) {
        viewModelScope.launch {
            _uiState.update { it.copy(plantAction = PlantActionUIState.Loading) }
            val tmpState = runCatching {
                repositorypc.putPlantpc(plantId, nama, deskripsi, manfaat, efekSamping, file) // Gunakan repositorypc
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") PlantActionUIState.Success(response.message)
                    else PlantActionUIState.Error(response.message)
                },
                onFailure = { PlantActionUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(plantAction = tmpState) }
        }
    }

    fun deletePlantpc(plantId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(plantAction = PlantActionUIState.Loading) }
            val tmpState = runCatching {
                repositorypc.deletePlantpc(plantId) // Gunakan repositorypc
            }.fold(
                onSuccess = { response ->
                    if (response.status == "success") PlantActionUIState.Success(response.message)
                    else PlantActionUIState.Error(response.message)
                },
                onFailure = { PlantActionUIState.Error(it.message ?: "Unknown error") }
            )
            _uiState.update { it.copy(plantAction = tmpState) }
        }
    }
}