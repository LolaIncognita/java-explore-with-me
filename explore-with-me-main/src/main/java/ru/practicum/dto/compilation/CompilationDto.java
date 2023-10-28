package ru.practicum.dto.compilation;

import lombok.Builder;
import lombok.Data;
import ru.practicum.dto.event.EventShortDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class CompilationDto {
    private List<EventShortDto> events;
    @NotNull
    private Long id;
    @NotNull
    private Boolean pinned;
    @NotNull
    private String title;
}