package io.github.biojj.modules.project.controller;

import io.github.biojj.modules.project.model.Project;
import io.github.biojj.modules.project.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    private ProjectController projectController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        projectController = new ProjectController(projectService);
    }

    @Test
    public void testSaveProject() {
        Project project = new Project();
        project.setId(1L);

        when(projectService.save(any(Project.class))).thenReturn(project);

        Project response = projectController.save(project);

        assertNotNull(response);
        assertEquals(project, response);

        verify(projectService, times(1)).save(project);
    }

    @Test
    public void testFindAllProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());

        Page<Project> projectPage = new PageImpl<>(projects);

        when(projectService.findAll(any(Pageable.class))).thenReturn(projectPage);

        Page<Project> response = projectController.findAll(0, 10, "id");

        assertNotNull(response);
        assertEquals(projects.size(), response.getContent().size());

        verify(projectService, times(1)).findAll(any(Pageable.class));
    }


    @Test
    public void testDeleteProject() {
        doNothing().when(projectService).delete(anyLong());

        assertDoesNotThrow(() -> projectController.deletar(1L));

        verify(projectService, times(1)).delete(1L);
    }

    @Test
    public void testUpdateProject() {
        Project updatedProject = new Project();
        updatedProject.setId(1L);

        doNothing().when(projectService).update(anyLong(), any(Project.class));

        assertDoesNotThrow(() -> projectController.atualizar(1L, updatedProject));

        verify(projectService, times(1)).update(1L, updatedProject);
    }

    @Test
    public void testFindProjectById() {
        Project project = new Project();
        project.setId(1L);

        when(projectService.findById(anyLong())).thenReturn(project);

        Project response = projectController.findById(1L);

        assertNotNull(response);
        assertEquals(project, response);

        verify(projectService, times(1)).findById(anyLong());
    }
}
