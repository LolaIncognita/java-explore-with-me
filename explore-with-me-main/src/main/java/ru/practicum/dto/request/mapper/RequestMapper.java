package ru.practicum.dto.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.model.ParticipationRequest;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RequestMapper {
    public ParticipationRequestDto mapToDto(ParticipationRequest entity) {
        return ParticipationRequestDto.builder()
                .id(entity.getId())
                .requester(entity.getRequester().getId())
                .event(entity.getEvent().getId())
                .created(entity.getCreated())
                .status(String.valueOf(entity.getStatus()))
                .build();
    }

    public List<ParticipationRequestDto> mapToDto(List<ParticipationRequest> entities) {
        return entities.stream()
                .map(RequestMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
