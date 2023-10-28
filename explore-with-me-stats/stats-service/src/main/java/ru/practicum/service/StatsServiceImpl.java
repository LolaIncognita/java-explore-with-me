package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import ru.practicum.StatDto;
import ru.practicum.StatDtoWithHits;
import ru.practicum.controller.exception.LocalDateTimeException;
import ru.practicum.model.Stat;
import ru.practicum.model.StatMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public StatDto saveStats(StatDto statDto) {
        Stat stat = StatMapper.fromStatDto(statDto);
        return StatMapper.toStatDto(statsRepository.save(stat));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatDtoWithHits> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        checkLocalDateTimeOrThrow(start, end);
        List<StatDtoWithHits> ans = new ArrayList<>();

        for (String u : uris) {
            if (!unique) {
                List<StatDtoWithHits> results = statsRepository.getStatsForTimeInterval(start, end, List.of(u))
                        .stream()
                        .map(s -> new StatDtoWithHits(s.getApp(), s.getUri(), s.getHits()))
                        .collect(Collectors.toList());
                ans.addAll(results);
            } else {
                List<StatDtoWithHits> results = statsRepository.getStatsForTimeIntervalUnique(start, end, List.of(u))
                        .stream()
                        .map(s -> new StatDtoWithHits(s.getApp(), s.getUri(), s.getHits()))
                        .collect(Collectors.toList());
                ans.addAll(results);
            }
        }

        ans.sort(Comparator.comparing(StatDtoWithHits::getHits).reversed());
        return ans;
    }

    private void checkLocalDateTimeOrThrow(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end) || end == null) {
            throw new LocalDateTimeException("Start must be after end");
        }
    }
}