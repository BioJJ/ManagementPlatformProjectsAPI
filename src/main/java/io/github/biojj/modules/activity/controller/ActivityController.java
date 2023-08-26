package io.github.biojj.modules.activity.controller;

import io.github.biojj.modules.activity.model.Activity;
import io.github.biojj.modules.activity.services.ActivityService;
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
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Activity save(@RequestBody @Valid Activity actiActivity) {
        return service.save(actiActivity);
    }

    @GetMapping
    public Page<Activity> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return service.findAll(pageable);
    }

    @GetMapping("{id}")
    public Activity findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.delete(id);

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Activity atualizar(@PathVariable Long id, @RequestBody @Valid Activity actiActivityAtualizado) {
        return service.update(id, actiActivityAtualizado);
    }


}
