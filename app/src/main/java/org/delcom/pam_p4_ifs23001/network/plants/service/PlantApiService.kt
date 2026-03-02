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
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantApiService {
    // Ambil profile developer
    @GET("profile")
    suspend fun getProfile(): ResponseMessage<ResponseProfile?>

    // Ambil semua data Komponen
    @GET("plants")
    suspend fun getAllPlants(
        @Query("search") search: String? = null
    ): ResponseMessage<ResponsePlants?>

    // Tambah data Komponen
    @Multipart
    @POST("/plants")
    suspend fun postPlant(
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("manfaat") manfaat: RequestBody,
        @Part("efekSamping") efekSamping: RequestBody,
        @Part file: MultipartBody.Part
    ): ResponseMessage<ResponsePlantAdd?>

    // Ambil data Komponen berdasarkan ID
    @GET("plants/{plantId}")
    suspend fun getPlantById(
        @Path("plantId") plantId: String
    ): ResponseMessage<ResponsePlant?>


    // Ubah data Komponen
    @Multipart
    @PUT("/plants/{plantId}")
    suspend fun putPlant(
        @Path("plantId") plantId: String,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("manfaat") manfaat: RequestBody,
        @Part("efekSamping") efekSamping: RequestBody,
        @Part file: MultipartBody.Part? = null
    ): ResponseMessage<String?>

    // Hapus data Komponen
    @DELETE("plants/{plantId}")
    suspend fun deletePlant(
        @Path("plantId") plantId: String
    ): ResponseMessage<String?>

    @Multipart
    @PUT("/plantspc/{plantId}")
    suspend fun putPlantpc(
        @Path("plantId") plantIdpc: String,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("pengaruh") pengaruh: RequestBody,
        @Part file: MultipartBody.Part? = null
    ): ResponseMessage<String?>

    @DELETE("plantspc/{plantId}")
    suspend fun deletePlantpc(
        @Path("plantId") plantIdpc: String
    ): ResponseMessage<String?>

    @GET("plantspc/{plantId}")
    suspend fun getPlantByIdpc(
        @Path("plantId") plantIdpc: String
    ): ResponseMessage<ResponsePlantpc?>

    @Multipart
    @POST("/plantspc")
    suspend fun postPlantpc(
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part("pengaruh") pengaruh: RequestBody,
        @Part file: MultipartBody.Part
    ): ResponseMessage<ResponsePlantAddpc?>

    @GET("plantspc")
    suspend fun getAllPlantspc(
        @Query("search") search: String? = null
    ): ResponseMessage<ResponsePlantspc?>

}