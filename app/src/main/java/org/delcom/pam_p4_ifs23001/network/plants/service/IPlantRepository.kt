package org.delcom.pam_p4_ifs23001.network.plants.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.delcom.pam_p4_ifs23001.network.data.ResponseMessage
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlant
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantAdd
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlants
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantpc
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantAddpc
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantspc
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponseProfile

interface IPlantRepository {
    // Ambil profile developer
    suspend fun getProfile(): ResponseMessage<ResponseProfile?>

    // Ambil semua data Komponen
    suspend fun getAllPlants(
        search: String? = null
    ): ResponseMessage<ResponsePlants?>

    // Tambah data Komponen
    suspend fun postPlant(
        nama: RequestBody,
        deskripsi: RequestBody,
        manfaat: RequestBody,
        efekSamping: RequestBody,
        file: MultipartBody.Part
    ): ResponseMessage<ResponsePlantAdd?>

    // Ambil data Komponen berdasarkan ID
    suspend fun getPlantById(
        plantId: String
    ): ResponseMessage<ResponsePlant?>


    // Ubah data Komponen
    suspend fun putPlant(
        plantId: String,
        nama: RequestBody,
        deskripsi: RequestBody,
        manfaat: RequestBody,
        efekSamping: RequestBody,
        file: MultipartBody.Part? = null
    ): ResponseMessage<String?>

    // Hapus data Komponen
    suspend fun deletePlant(
        plantId: String
    ): ResponseMessage<String?>

    // PC Methods
    suspend fun getAllPlantspc(
        search: String? = null
    ): ResponseMessage<ResponsePlantspc?>

    suspend fun postPlantpc(
        nama: RequestBody,
        deskripsi: RequestBody,
        harga: RequestBody,
        pengaruh: RequestBody,
        file: MultipartBody.Part
    ): ResponseMessage<ResponsePlantAddpc?>

    suspend fun getPlantByIdpc(
        plantIdpc: String
    ): ResponseMessage<ResponsePlantpc?>

    suspend fun putPlantpc(
        plantIdpc: String,
        nama: RequestBody,
        deskripsi: RequestBody,
        harga: RequestBody,
        pengaruh: RequestBody,
        file: MultipartBody.Part? = null
    ): ResponseMessage<String?>

    suspend fun deletePlantpc(
        plantIdpc: String
    ): ResponseMessage<String?>
}