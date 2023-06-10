package yapp.be.storage.jpa.volunteerevent.model

import jakarta.persistence.*

@Entity
@Table(
    name = "volunteer_event_join_queue",
    indexes = [
        Index(name = "IDX_VOLUNTEER_EVENT_ID", columnList = "volunteer_event_id")
    ]
)
class VolunteerEventJoinQueueEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "volunteer_event_id")
    val volunteerEventId: Long,
)