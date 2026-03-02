package org.delcom.pam_p4_ifs23001.network.plants.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponsePlants (
    val plants: List<ResponsePlantData>
)

@Serializable
data class ResponsePlant (
    val plant: ResponsePlantData
)

@Serializable
data class ResponsePlantAdd (
    val plantId: String
)

@Serializable
data class ResponsePlantData(
    val id: String,
    val nama: String,
    val deskripsi: String,
    val manfaat: String,
    val efekSamping: String,
    val createdAt: String,
    val updatedAt: String,
    val gambar: Int? = null
)


@Serializable
data class ResponsePlantspc (
    val plants: List<ResponsePlantDatapc>
)

@Serializable
data class ResponsePlantpc (
    val plant: ResponsePlantDatapc
)

@Serializable
data class ResponsePlantAddpc (
    val plantIdpc: String
)

@Serializable
data class ResponsePlantDatapc (
    val id: String,
    val nama: String,
    val deskripsi: String,
    val harga: String,
    val pengaruh: String,
    val createdAt: String,
    val updatedAt: String
)