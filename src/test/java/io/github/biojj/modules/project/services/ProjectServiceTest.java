package io.github.biojj.modules.project.services;

import io.github.biojj.modules.project.model.Project;
import io.github.biojj.modules.project.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        projectService = new ProjectService(projectRepository);
    }

    @Test
    public void testSaveProject() {
        Project project = new Project();
        when(projectRepository.save(project)).thenReturn(project);

        Project savedProject = projectService.save(project);

        assertNotNull(savedProject);
        assertEquals(project, savedProject);

        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testFindAllProjects() {
        Page<Project> mockPage = mock(Page.class);
        when(projectRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<Project> projects = projectService.findAll(Pageable.unpaged());

        assertNotNull(projects);
        assertEquals(mockPage, projects);

        verify(projectRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testFindProjectById() {
        Project project = new Project();
        project.setId(1L);

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        Project foundProject = projectService.findById(project.getId());

        assertNotNull(foundProject);
        assertEquals(project.getId(), foundProject.getId());

        verify(projectRepository, times(1)).findById(project.getId());
    }

    @Test
    public void testFindProjectByIdNotFound() {
        Long nonExistingId = 123L;
        when(projectRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> projectService.findById(nonExistingId));

        verify(projectRepository, times(1)).findById(nonExistingId);
    }

    @Test
    public void testDeleteProject() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        projectService.delete(projectId);

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).delete(project);
    }

    @Test
    public void testDeleteProjectNotFound() {
        Long nonExistingId = 123L;
        when(projectRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> projectService.delete(nonExistingId));

        verify(projectRepository, times(1)).findById(nonExistingId);
        verify(projectRepository, never()).delete(any());
    }

    @Test
    public void testUpdateProject() {
        Long projectId = 1L;
        Project existingProject = new Project();
        existingProject.setId(projectId);

        Project updatedProject = new Project();
        updatedProject.setProjectName("Updated Project");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(existingProject)).thenReturn(updatedProject);

        projectService.update(projectId, updatedProject);

        assertEquals(updatedProject.getProjectName(), existingProject.getProjectName());

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).save(existingProject);
    }

    @Test
    public void testUpdateProjectNotFound() {
        Long nonExistingId = 123L;
        when(projectRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> projectService.update(nonExistingId, new Project()));

        verify(projectRepository, times(1)).findById(nonExistingId);
        verify(projectRepository, never()).save(any());
    }
}
