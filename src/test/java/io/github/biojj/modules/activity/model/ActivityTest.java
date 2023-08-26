package io.github.biojj.modules.activity.model;

import io.github.biojj.modules.client.model.Client;
import io.github.biojj.modules.project.model.Project;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ActivityTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testActivityCreation() {
        Project project = mock(Project.class);
        Client client = mock(Client.class);

        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate expectedEndDate = LocalDate.of(2023, 8, 15);

        Activity activity = Activity.builder()
                .id(1L)
                .startDate(startDate)
                .expectedEndDate(expectedEndDate)
                .status("In Progress")
                .description("Sample activity")
                .project(project)
                .client(client)
                .build();

        assertEquals(1L, activity.getId());
        assertEquals(startDate, activity.getStartDate());
        assertEquals(expectedEndDate, activity.getExpectedEndDate());
        assertEquals("In Progress", activity.getStatus());
        assertEquals("Sample activity", activity.getDescription());
        assertEquals(project, activity.getProject());
        assertEquals(client, activity.getClient());

        assertNotNull(activity);
    }

    @Test
    public void testValidationSuccess() {
        Activity activity = Activity.builder()
                .startDate(LocalDate.now())
                .expectedEndDate(LocalDate.now().plusDays(7))
                .status("In Progress")
                .description("Sample activity")
                .build();

        Set<ConstraintViolation<Activity>> violations = validator.validate(activity);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationFailure() {
        Activity activity = Activity.builder()
                .startDate(null) // @NotNull validation
                .expectedEndDate(LocalDate.now().plusDays(7))
                .status("") // @NotBlank validation
                .description("Sample activity")
                .build();

        Set<ConstraintViolation<Activity>> violations = validator.validate(activity);

        assertEquals(2, violations.size());
    }

    @Test
    public void testValidationDateFormat() {
        Activity activity = Activity.builder()
                .startDate(LocalDate.now())
                .expectedEndDate(LocalDate.now().plusDays(7))
                .status("In Progress")
                .description("Sample activity")
                .build();

        Set<ConstraintViolation<Activity>> violations = validator.validate(activity);

        assertTrue(violations.isEmpty());
    }
}
