package yapp.be.storage.repository

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.outbound.observationanimal.ObservationAnimalCommandHandler
import yapp.be.domain.port.outbound.observationanimal.ObservationAnimalQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.model.support.PagedResult
import yapp.be.storage.config.PAGE_SIZE
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.observationanimal.model.mappers.toDomainModel
import yapp.be.storage.jpa.observationanimal.model.mappers.toEntityModel
import yapp.be.storage.jpa.observationanimal.repository.ObservationAnimalJpaRepository

@Component
class ObservationAnimalRepository(
    private val observationAnimalJpaRepository: ObservationAnimalJpaRepository,
) : ObservationAnimalQueryHandler, ObservationAnimalCommandHandler {

    @Transactional(readOnly = true)
    override fun findAllByShelterId(
        page: Int,
        shelterId: Long
    ): PagedResult<ObservationAnimal> {
        val pageable = PageRequest.of(
            page, PAGE_SIZE
        )
        val observationAnimalEntities = observationAnimalJpaRepository.findAllByShelterId(
            shelterId = shelterId,
            pageable = pageable
        )
        return PagedResult(
            pageSize = observationAnimalEntities.size,
            pageNumber = observationAnimalEntities.number,
            content = observationAnimalEntities.content.map { it.toDomainModel() }
        )
    }

    @Transactional(readOnly = true)
    override fun findByIdAndShelterId(observationAnimalId: Long, shelterId: Long): ObservationAnimal {
        return observationAnimalJpaRepository.findByIdAndShelterId(
            id = observationAnimalId,
            shelterId = shelterId
        )?.toDomainModel() ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Observation Animal Not Found")
    }

    @Transactional(readOnly = true)
    override fun findById(observationAnimalId: Long): ObservationAnimal {
        val observationAnimalEntity = observationAnimalJpaRepository.findByIdOrNull(observationAnimalId)
            ?: throw CustomException(
                StorageExceptionType.ENTITY_NOT_FOUND, "Observation Animal Not Found"
            )
        return observationAnimalEntity.toDomainModel()
    }

    @Transactional
    override fun create(observationAnimal: ObservationAnimal): ObservationAnimal {
        val observationAnimalEntity = observationAnimal.toEntityModel()
        return observationAnimalJpaRepository.save(observationAnimalEntity).toDomainModel()
    }

    @Transactional
    override fun update(observationAnimal: ObservationAnimal): ObservationAnimal {
        val observationAnimalEntity = observationAnimalJpaRepository.findByIdOrNull(observationAnimal.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Shelter Not Found")
        observationAnimalEntity.update(observationAnimal)
        return observationAnimalJpaRepository.save(observationAnimalEntity).toDomainModel()
    }

    @Transactional
    override fun delete(
        observationAnimalId: Long,
        shelterId: Long
    ): ObservationAnimal {
        val observationAnimalEntity = observationAnimalJpaRepository.findByIdAndShelterId(
            id = observationAnimalId,
            shelterId = shelterId
        )
            ?: throw CustomException(
                StorageExceptionType.ENTITY_NOT_FOUND, "Observation Animal Not Found"
            )
        observationAnimalJpaRepository.delete(observationAnimalEntity)

        return observationAnimalEntity.toDomainModel()
    }
}
