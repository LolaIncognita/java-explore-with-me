package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatDto;
import ru.practicum.StatDtoWithHits;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StatsController {

    private final StatsService service;

    @PostMapping(path = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public StatDto saveStats(@RequestBody StatDto statDto) {
        log.info("POST request for stats received: {}", statDto);
        StatDto response = service.saveStats(statDto);
        log.info("Stats saved: {}", response);
        return response;
    }

    @GetMapping(path = "/stats")
    public List<StatDtoWithHits> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) String[] uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique
    ) {
        log.info("GET request for stats received from {} to {} with uris: {}", start, end, uris);
        List<StatDtoWithHits> response = service.getStats(start, end, uris, unique);
        log.info("{}", response);
        return response;
    }

    @GetMapping(path = "/stats/{id}")
    public StatDto getStatsById(@PathVariable long id) {
        log.info("GET request received for stats with id {}", id);
        StatDto response = service.getStatsById(id);
        log.info("{}", response);
        return response;
    }

}