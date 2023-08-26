package io.github.biojj.modules.activity.services;

import io.github.biojj.modules.activity.model.Activity;
import io.github.biojj.modules.activity.repository.ActivityRepository;
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

public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    private ActivityService activityService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        activityService = new ActivityService(activityRepository);
    }

    @Test
    public void testSaveActivity() {
        Activity activity = new Activity();
        when(activityRepository.save(activity)).thenReturn(activity);

        Activity savedActivity = activityService.save(activity);

        assertNotNull(savedActivity);
        assertEquals(activity, savedActivity);

        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    public void testFindAllActivities() {
        Page<Activity> mockPage = mock(Page.class);
        when(activityRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<Activity> activities = activityService.findAll(Pageable.unpaged());

        assertNotNull(activities);
        assertEquals(mockPage, activities);

        verify(activityRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testFindActivityById() {
        Activity activity = new Activity();
        activity.setId(1L);

        when(activityRepository.findById(activity.getId())).thenReturn(Optional.of(activity));

        Activity foundActivity = activityService.findById(activity.getId());

        assertNotNull(foundActivity);
        assertEquals(activity.getId(), foundActivity.getId());

        verify(activityRepository, times(1)).findById(activity.getId());
    }

    @Test
    public void testFindActivityByIdNotFound() {
        Long nonExistingId = 123L;
        when(activityRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> activityService.findById(nonExistingId));

        verify(activityRepository, times(1)).findById(nonExistingId);
    }

    @Test
    public void testDeleteActivity() {
        Long activityId = 1L;
        Activity activity = new Activity();
        activity.setId(activityId);

        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));

        activityService.delete(activityId);

        verify(activityRepository, times(1)).findById(activityId);
        verify(activityRepository, times(1)).delete(activity);
    }

    @Test
    public void testDeleteActivityNotFound() {
        Long nonExistingId = 123L;
        when(activityRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> activityService.delete(nonExistingId));

        verify(activityRepository, times(1)).findById(nonExistingId);
        verify(activityRepository, never()).delete(any());
    }

    @Test
    public void testUpdateActivity() {
        Long activityId = 1L;
        Activity existingActivity = new Activity();
        existingActivity.setId(activityId);

        Activity updatedActivity = new Activity();
        updatedActivity.setDescription("Updated Description");

        when(activityRepository.findById(activityId)).thenReturn(Optional.of(existingActivity));
        when(activityRepository.save(existingActivity)).thenReturn(updatedActivity);

        Activity result = activityService.update(activityId, updatedActivity);

        assertNotNull(result);
        assertEquals(updatedActivity.getDescription(), result.getDescription());

        verify(activityRepository, times(1)).findById(activityId);
        verify(activityRepository, times(1)).save(existingActivity);
    }

    @Test
    public void testUpdateActivityNotFound() {
        Long nonExistingId = 123L;
        when(activityRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> activityService.update(nonExistingId, new Activity()));

        verify(activityRepository, times(1)).findById(nonExistingId);
        verify(activityRepository, never()).save(any());
    }
}
