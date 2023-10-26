package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Uri;

public interface UriRepository extends JpaRepository<Uri, Long> {
    Uri findByName(String name);
}