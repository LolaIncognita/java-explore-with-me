package ru.practicum.service;

import ru.practicum.StatDto;
import ru.practicum.StatDtoWithHits;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    StatDto saveStats(StatDto statDto);

    List<StatDtoWithHits> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}