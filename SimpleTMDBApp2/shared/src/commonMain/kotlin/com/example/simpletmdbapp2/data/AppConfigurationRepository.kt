package com.example.simpletmdbapp2.data

import com.example.simpletmdbapp2.data.service.ConfigurationService
import com.example.simpletmdbapp2.domain.CommonConfigurationRepository
import com.example.simpletmdbapp2.entities.ConfigurationEntity
import com.example.simpletmdbapp2.exception.ErrorDuringMappingEntityException
import com.example.simpletmdbapp2.kotinext.safe
import com.example.simpletmdbapp2.model.Configuration
import com.example.simpletmdbapp2.model.ImageSize
import com.example.simpletmdbapp2.model.PickingSize

class CommonConfigurationRepositoryImpl(
    val service: ConfigurationService
): CommonConfigurationRepository {

    private var cachedConfiguration: Configuration? = null

    override suspend fun loadConfiguration(): ConfigurationEntity {
        return service.getConfiguration()
    }

    override suspend fun loadAndSaveConfiguration() {
        try {
            val config = loadConfiguration()
            cachedConfiguration = createConfiguration(config)
        } catch (e: Exception) {
            throw ErrorDuringMappingEntityException("Error while create configuration")
        }
    }

    override fun getCachedConfiguration(): Configuration {
        return cachedConfiguration ?: throw NullPointerException()
    }

    private fun createConfiguration(configurationEntity: ConfigurationEntity): Configuration {
        return Configuration(
            imageBase = configurationEntity.imageConfiguration.secureBaseUrl ?: configurationEntity.imageConfiguration.baseUrl.safe(),
            posterSize = "",
            profileSizes = createImageSizes(configurationEntity.imageConfiguration.profileSizes),
            movieSizes = createImageSizes(configurationEntity.imageConfiguration.posterSizes)
        )
    }

    private fun createImageSizes(listSizes: List<String>): List<ImageSize> {
        val smallest = listSizes.sortedBy {
            val valueString = it.drop(0)
            valueString.toLongOrNull() ?: -1
        }.firstOrNull { it != "original" }
        return listSizes.mapNotNull { v ->
            val pickingSize = if (v == "original") {
                PickingSize.ORIGINAL
            } else if (v == smallest) {
                PickingSize.S_SMALLEST
            } else if (v.contains(PickingSize.S_500.value)) {
                PickingSize.S_500
            } else if (v.contains(PickingSize.S_700.value)) {
                PickingSize.S_700
            } else {
                PickingSize.S_SMALLEST.takeIf { v == smallest }
            }
            if (pickingSize != null) ImageSize(value = v, pickingSize) else null
        }.distinctBy { it.pickingSize }
    }
}