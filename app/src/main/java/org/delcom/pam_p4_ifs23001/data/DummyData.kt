package org.delcom.pam_p4_ifs23001.data

import org.delcom.pam_p4_ifs23001.R
import org.delcom.pam_p4_ifs23001.network.plants.data.ResponsePlantData

object DummyData {
    fun getPlantsData(): List<ResponsePlantData> {
        return listOf(
            ResponsePlantData(
                id = "1",
                nama = "Lidah Buaya (Aloe Vera)",
                gambar = R.drawable.img_logo,
                deskripsi = "Tanaman sekulen dengan daun berdaging tebal yang mengandung gel.",
                manfaat = "Mengobati luka bakar ringan, melembapkan kulit, dan menenangkan iritasi.",
                efekSamping = "Dapat menyebabkan kram perut jika dikonsumsi berlebihan secara oral.",
                createdAt = "2024-01-01T00:00:00Z",
                updatedAt = "2024-01-01T00:00:00Z"
            ),
            ResponsePlantData(
                id = "2",
                nama = "Jahe (Zingiber officinale)",
                gambar = R.drawable.img_logo,
                deskripsi = "Tanaman rimpang yang populer sebagai rempah-rempah dan bahan obat.",
                manfaat = "Meredakan mual, menghangatkan tubuh, dan meningkatkan sistem imun.",
                efekSamping = "Dapat menyebabkan mulas atau kembung pada beberapa orang.",
                createdAt = "2024-01-01T00:00:00Z",
                updatedAt = "2024-01-01T00:00:00Z"
            ),
            ResponsePlantData(
                id = "3",
                nama = "Kunyit (Curcuma longa)",
                gambar = R.drawable.img_logo,
                deskripsi = "Tanaman rimpang berwarna kuning cerah yang mengandung kurkumin.",
                manfaat = "Sebagai anti-inflamasi alami dan antioksidan.",
                efekSamping = "Penggunaan berlebihan dapat mengencerkan darah.",
                createdAt = "2024-01-01T00:00:00Z",
                updatedAt = "2024-01-01T00:00:00Z"
            ),
            ResponsePlantData(
                id = "4",
                nama = "Daun Sirih",
                gambar = R.drawable.img_logo,
                deskripsi = "Tanaman merambat yang daunnya sering digunakan untuk pengobatan tradisional.",
                manfaat = "Sebagai antiseptik alami dan menjaga kesehatan mulut.",
                efekSamping = "Penggunaan jangka panjang pada mulut dapat merusak jaringan gusi.",
                createdAt = "2024-01-01T00:00:00Z",
                updatedAt = "2024-01-01T00:00:00Z"
            ),
            ResponsePlantData(
                id = "5",
                nama = "Lavender",
                gambar = R.drawable.img_logo,
                deskripsi = "Tanaman hias dengan bunga berwarna ungu dan aroma yang menenangkan.",
                manfaat = "Membantu relaksasi dan mengatasi insomnia.",
                efekSamping = "Dapat menyebabkan iritasi kulit jika minyak asirinya digunakan tanpa pengenceran.",
                createdAt = "2024-01-01T00:00:00Z",
                updatedAt = "2024-01-01T00:00:00Z"
            )
        )
    }
}
