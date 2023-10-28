package ru.practicum.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.dto.compilation.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.util.exception.ClientErrorException;
import ru.practicum.util.exception.EntityNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {
        Compilation request = CompilationMapper.mapFromNewCompilationDto(newCompilationDto);
        List<Long> eventsIds = newCompilationDto.getEvents();

        if (eventsIds != null && !eventsIds.isEmpty()) {
            List<Event> eventList = eventRepository.findAllById(eventsIds);
            request.setEventList(eventList);
        }

        Compilation response = compilationRepository.save(request);
        return CompilationMapper.mapEntityToDto(response);
    }

    @Override
    @Transactional
    public void deleteCompilation(long compId) {
        getCompilationOrThrow(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(long compId, UpdateCompilationRequest updateRequest) {
        Compilation existingCompilation = getCompilationOrThrow(compId);

        if (updateRequest.getTitle() != null) {
            if (compilationRepository.findCompilationByTitle(updateRequest.getTitle()).isPresent()) {
                throw new ClientErrorException("Compilation with name " + updateRequest.getTitle() + " already exists");
            }
            existingCompilation.setTitle(updateRequest.getTitle());
        }
        if (updateRequest.getPinned() != null) {
            existingCompilation.setPinned(updateRequest.getPinned());
        }
        if (updateRequest.getEvents() != null) {
            List<Long> eventsIds = updateRequest.getEvents();
            List<Event> eventList = eventRepository.findAllById(eventsIds);

            existingCompilation.getEventList().clear();
            existingCompilation.getEventList().addAll(eventList);

            for (Event event : eventList) {
                event.getCompilationList().add(existingCompilation);
            }
        }

        compilationRepository.save(existingCompilation);

        return CompilationMapper.mapEntityToDto(existingCompilation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        Pageable page = PageRequest.of(from / size, size);
        List<Compilation> response;

        if (pinned == null) {
            response = compilationRepository.findAll(page).toList();
        } else {
            response = compilationRepository.findAllByPinned(pinned, page);
        }

        return CompilationMapper.mapEntityToDto(response);
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilation(long compId) {
        Compilation response = getCompilationOrThrow(compId);
        return CompilationMapper.mapEntityToDto(response);
    }

    private Compilation getCompilationOrThrow(long compId) {
        return compilationRepository.findById(compId).orElseThrow(
                () -> new EntityNotFoundException("Compilation with id " + compId + " not found")
        );
    }
}