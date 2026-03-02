package org.delcom.pam_p4_ifs23001.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.delcom.pam_p4_ifs23001.network.plants.service.IPlantpcAppContainer
import org.delcom.pam_p4_ifs23001.network.plants.service.IPlantpcRepository
import org.delcom.pam_p4_ifs23001.network.plants.service.PlantpcAppContainer

@Module
@InstallIn(SingletonComponent::class)
object PlantModulepc {
    @Provides
    fun providePlantContainerpc(): IPlantpcAppContainer {
        return PlantpcAppContainer()
    }

    @Provides
    fun providePlantRepositorypc(container: IPlantpcAppContainer): IPlantpcRepository {
        return container.plantpcRepository
    }
}