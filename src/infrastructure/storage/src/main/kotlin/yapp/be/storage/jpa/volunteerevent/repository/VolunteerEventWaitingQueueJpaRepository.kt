package yapp.be.storage.jpa.volunteerevent.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventUserWaitingQueueEntity

@Repository
interface VolunteerEventWaitingQueueJpaRepository : JpaRepository<VolunteerEventUserWaitingQueueEntity, Long>
