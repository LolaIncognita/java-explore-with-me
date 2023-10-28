package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.model.Location;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class EventFullDto {
    @NotNull
    private String annotation;
    @NotNull
    private CategoryDto category;
    private Integer confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @NotNull
    private String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Long id;
    private UserShortDto initiator;
    @NotNull
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private String state;
    @NotNull
    private String title;
    private Integer views;
}