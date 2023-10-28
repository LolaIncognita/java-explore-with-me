package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.ParticipationRequest;
import ru.practicum.model.enums.RequestStatus;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByRequesterId(long userId);

    List<ParticipationRequest> findAllByEventIdAndStatus(long eventId, RequestStatus status);

    List<ParticipationRequest> findAllByEventInitiatorIdAndEventId(long userId, long eventId);

    boolean existsByRequesterIdAndEventId(long userId, long eventId);
}