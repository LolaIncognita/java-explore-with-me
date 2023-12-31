package ru.practicum.repository;

import org.springframework.data.jpa.repository.Query;
import ru.practicum.StatDtoWithHits;
import ru.practicum.model.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Stat, Long> {

    @Query("SELECT new ru.practicum.StatDtoWithHits(st.app, st.uri.name, count(st.ip)) " +
            "FROM Stat AS st " +
            "WHERE st.timestamp BETWEEN :start AND :end " +
            "AND st.uri.name IN :uris " +
            "GROUP BY st.uri.name, st.app " +
            "ORDER BY count(st.ip) DESC ")
    List<StatDtoWithHits> getStatsForTimeIntervalAndUris(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("SELECT new ru.practicum.StatDtoWithHits(st.app, st.uri.name, count(distinct st.ip)) " +
            "FROM Stat AS st " +
            "WHERE st.timestamp BETWEEN :start AND :end " +
            "AND st.uri.name IN :uris " +
            "GROUP BY st.uri.name, st.app " +
            "ORDER BY count(distinct st.ip) DESC ")
    List<StatDtoWithHits> getStatsForTimeIntervalAndUrisUnique(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("SELECT new ru.practicum.StatDtoWithHits(st.app, st.uri.name, count(st.ip)) " +
            "FROM Stat AS st " +
            "WHERE st.timestamp BETWEEN :start AND :end " +
            "GROUP BY st.uri.name, st.app " +
            "ORDER BY count(st.ip) DESC ")
    List<StatDtoWithHits> getStatsForTimeInterval(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.StatDtoWithHits(st.app, st.uri.name, count(distinct st.ip)) " +
            "FROM Stat AS st " +
            "WHERE st.timestamp BETWEEN :start AND :end " +
            "GROUP BY st.uri.name, st.app " +
            "ORDER BY count(distinct st.ip) DESC ")
    List<StatDtoWithHits> getStatsForTimeIntervalUnique(LocalDateTime start, LocalDateTime end);
}