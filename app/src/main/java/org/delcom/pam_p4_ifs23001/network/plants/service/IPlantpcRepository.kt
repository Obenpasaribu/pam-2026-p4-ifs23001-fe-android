package org.delcom.pam_p4_ifs23001.network.plants.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.delcom.pam_p4_ifs23001.network.data.ResponseMessage
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantpc
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantAddpc
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantspc


interface IPlantpcRepository {

    suspend fun getAllPlantspc(
        search: String? = null
    ): ResponseMessage<ResponsePlantspc?>

    // Tambah data Komponen
    suspend fun postPlantpc(
        nama: RequestBody,
        deskripsi: RequestBody,
        harga: RequestBody,
        pengaruh: RequestBody,
        file: MultipartBody.Part
    ): ResponseMessage<ResponsePlantAddpc?>

    // Ambil data Komponen berdasarkan ID
    suspend fun getPlantByIdpc(
        plantIdpc: String
    ): ResponseMessage<ResponsePlantpc?>


    // Ubah data Komponen
    suspend fun putPlantpc(
        plantIdpc: String,
        nama: RequestBody,
        deskripsi: RequestBody,
        harga: RequestBody,
        pengaruh: RequestBody,
        file: MultipartBody.Part? = null
    ): ResponseMessage<String?>

    // Hapus data Komponen
    suspend fun deletePlantpc(
        plantIdpc: String
    ): ResponseMessage<String?>
}