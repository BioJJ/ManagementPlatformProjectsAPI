package io.github.biojj.modules.activity.controller;

import io.github.biojj.modules.activity.model.Activity;
import io.github.biojj.modules.activity.services.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActivityControllerTest {

    @Mock
    private ActivityService activityService;

    private ActivityController activityController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        activityController = new ActivityController(activityService);
    }

    @Test
    public void testSaveActivity() {
        Activity activity = new Activity();
        activity.setId(1L);

        when(activityService.save(any(Activity.class))).thenReturn(activity);

        Activity response = activityController.save(activity);

        assertNotNull(response);
        assertEquals(activity, response);

        verify(activityService, times(1)).save(activity);
    }

    @Test
    public void testFindAllActivities() {
        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity());
        activities.add(new Activity());

        Page<Activity> activityPage = new PageImpl<>(activities);

        when(activityService.findAll(any(Pageable.class))).thenReturn(activityPage);

        Page<Activity> response = activityController.findAll(0, 10, "id");

        assertNotNull(response);
        assertEquals(activities.size(), response.getContent().size());

        verify(activityService, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testFindActivityById() {
        Activity activity = new Activity();
        activity.setId(1L);

        when(activityService.findById(anyLong())).thenReturn(activity);

        Activity response = activityController.findById(1L);

        assertNotNull(response);
        assertEquals(activity, response);

        verify(activityService, times(1)).findById(1L);
    }

    @Test
    public void testFindNonexistentActivityById() {
        when(activityService.findById(anyLong())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThrows(ResponseStatusException.class, () -> activityController.findById(1L));

        verify(activityService, times(1)).findById(1L);
    }

    @Test
    public void testDeleteActivity() {
        doNothing().when(activityService).delete(anyLong());

        assertDoesNotThrow(() -> activityController.deletar(1L));

        verify(activityService, times(1)).delete(1L);
    }

    @Test
    public void testUpdateActivity() {
        Activity updatedActivity = new Activity();
        updatedActivity.setId(1L);

        when(activityService.update(anyLong(), any(Activity.class))).thenReturn(updatedActivity);

        Activity response = activityController.atualizar(1L, updatedActivity);

        assertNotNull(response);
        assertEquals(updatedActivity, response);

        verify(activityService, times(1)).update(1L, updatedActivity);
    }
}

