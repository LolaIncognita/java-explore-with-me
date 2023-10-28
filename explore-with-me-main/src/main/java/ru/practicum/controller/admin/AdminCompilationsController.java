package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.service.compilation.CompilationService;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationsController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addNewCompilation(
            @RequestBody @Valid NewCompilationDto newCompilationDto
    ) {
        log.info("POST | Add new Compilation");
        CompilationDto response = compilationService.addNewCompilation(newCompilationDto);
        log.info("Add new Compilation: ");
        return response;
    }

    @DeleteMapping(path = "/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(
            @PathVariable(name = "compId") long compId
    ) {
        log.info("DELETE | Delete Compilation");
        compilationService.deleteCompilation(compId);
        log.info("Delete Compilation successfully");
    }

    @PatchMapping(path = "/{compId}")
    public CompilationDto updateCompilation(
            @PathVariable(name = "compId") long compId,
            @RequestBody @Valid UpdateCompilationRequest updateRequest
    ) {
        log.info("PATCH | Update Compilation");
        CompilationDto response = compilationService.updateCompilation(compId, updateRequest);
        log.info("Update Compilation: {}", response);
        return response;
    }

}