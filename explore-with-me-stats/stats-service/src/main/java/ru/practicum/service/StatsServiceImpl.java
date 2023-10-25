package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import ru.practicum.StatDto;
import ru.practicum.StatDtoWithHits;
import ru.practicum.model.Stat;
import ru.practicum.model.StatMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.Uri;
import ru.practicum.repository.StatsRepository;
import ru.practicum.repository.UriRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final UriRepository uriRepository;

    @Override
    @Transactional
    public StatDto saveStats(StatDto statDto) {
        Stat stat = StatMapper.fromStatDto(statDto);
        Uri uri;
        if (uriRepository.findByName(stat.getUri().getName()) == null) {
            uri = uriRepository.save(stat.getUri());
        } else {
            uri = uriRepository.findByName(stat.getUri().getName());
        }
        stat.setUri(uri);
        return StatMapper.toStatDto(statsRepository.save(stat));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatDtoWithHits> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {

        if (!unique) {
            if (uris == null || uris.length == 0) {
                return statsRepository.getStatsForTimeInterval(start, end);
            } else {
                return statsRepository.getStatsForTimeIntervalAndUris(start, end, uris);
            }
        } else {
            if (uris == null || uris.length == 0) {
                return statsRepository.getStatsForTimeIntervalUnique(start, end);
            } else {
                return statsRepository.getStatsForTimeIntervalAndUrisUnique(start, end, uris);
            }
        }

    }

    @Override
    @Transactional(readOnly = true)
    public StatDto getStatsById(long id) {
        return StatMapper.toStatDto(statsRepository.findById(id).orElseThrow());
    }

}