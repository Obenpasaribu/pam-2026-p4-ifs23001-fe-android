package org.delcom.pam_p4_ifs23001.network.plants.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.delcom.pam_p4_ifs23001.helper.SuspendHelper
import org.delcom.pam_p4_ifs23001.network.data.ResponseMessage
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantpc
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantAddpc
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantspc

class PlantpcRepository (private val plantApiService: PlantApiService): IPlantpcRepository {
    override suspend fun getAllPlantspc(search: String?): ResponseMessage<ResponsePlantspc?> {
        return SuspendHelper.safeApiCall {
            plantApiService.getAllPlantspc(search)
        }
    }

    override suspend fun postPlantpc(
        nama: RequestBody,
        deskripsi: RequestBody,
        manfaat: RequestBody,
        efekSamping: RequestBody,
        file: MultipartBody.Part
    ): ResponseMessage<ResponsePlantAddpc?> {
        return SuspendHelper.safeApiCall {
            plantApiService.postPlantpc(
                nama = nama,
                deskripsi = deskripsi,
                harga = manfaat,
                pengaruh = efekSamping,
                file = file
            )
        }
    }

    override suspend fun getPlantByIdpc(plantIdpc: String): ResponseMessage<ResponsePlantpc?> {
        return SuspendHelper.safeApiCall {
            plantApiService.getPlantByIdpc(plantIdpc)
        }
    }

    override suspend fun putPlantpc(
        plantIdpc: String,
        nama: RequestBody,
        deskripsi: RequestBody,
        manfaat: RequestBody,
        efekSamping: RequestBody,
        file: MultipartBody.Part?
    ): ResponseMessage<String?> {
        return SuspendHelper.safeApiCall {
            plantApiService.putPlantpc(
                plantIdpc = plantIdpc,
                nama = nama,
                deskripsi = deskripsi,
                harga = manfaat,
                pengaruh = efekSamping,
                file = file
            )
        }
    }

    override suspend fun deletePlantpc(plantIdpc: String): ResponseMessage<String?> {
        return SuspendHelper.safeApiCall {
            plantApiService.deletePlantpc(plantIdpc)
        }
    }
}