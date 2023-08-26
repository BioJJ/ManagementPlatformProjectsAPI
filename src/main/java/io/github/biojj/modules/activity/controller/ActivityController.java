package io.github.biojj.modules.activity.controller;

import io.github.biojj.modules.project.model.Project;
import io.github.biojj.modules.project.services.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ActivityController {

    private final ProjectService service;

    public ActivityController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project save(@RequestBody @Valid Project client) {
        return service.save(client);
    }

    @GetMapping
    public Page<Project> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return service.findAll(pageable);
    }

    @GetMapping("{id}")
    public Project findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.delete(id);

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Long id, @RequestBody @Valid Project clientAtualizado) {
        service.update(id, clientAtualizado);
    }


}
