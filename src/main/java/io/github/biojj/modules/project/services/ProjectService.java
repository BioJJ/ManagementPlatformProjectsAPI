package io.github.biojj.modules.project.services;


import io.github.biojj.modules.project.model.Project;
import io.github.biojj.modules.project.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project save(Project project) {

        return projectRepository.save(project);
    }

    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    public Project findById(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "project não encontrado"));
    }

    public void delete(Long id) {
        projectRepository
                .findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "project não encontrado"));
    }

    public void update(Long id,
                       Project projectDTO) {

        projectRepository
                .findById(id)
                .map(project -> {
                    project.setProjectName(projectDTO.getProjectName());
                    project.setStartDate(projectDTO.getStartDate());
                    project.setExpectedEndDate(projectDTO.getExpectedEndDate());
                    project.setProjectStatus(projectDTO.getProjectStatus());

                    return projectRepository.save(project);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "project não encontrado"));
    }

}
