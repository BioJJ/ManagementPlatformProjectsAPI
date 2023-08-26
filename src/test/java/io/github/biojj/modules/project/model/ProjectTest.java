package io.github.biojj.modules.project.model;

import io.github.biojj.modules.project.repository.ProjectRepository;
import io.github.biojj.modules.project.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProjectTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        projectService = new ProjectService(projectRepository);
    }

    @Test
    public void testValidProject() {
        Project project = Project.builder()
                .projectName("Sample Project")
                .startDate(LocalDate.now())
                .expectedEndDate(LocalDate.now().plusDays(10))
                .projectStatus("In Progress")
                .build();

        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidProject() {
        Project project = Project.builder()
                .projectName("")
                .startDate(null)
                .expectedEndDate(null)
                .projectStatus("")
                .build();

        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertEquals(4, violations.size());
    }

    @Test
    public void testSaveProject() {
        Project project = Project.builder()
                .projectName("Sample Project")
                .startDate(LocalDate.now())
                .expectedEndDate(LocalDate.now().plusDays(10))
                .projectStatus("In Progress")
                .build();

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project savedProject = projectService.save(project);

        assertEquals(project, savedProject);
    }
}
