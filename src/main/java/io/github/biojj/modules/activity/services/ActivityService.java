package io.github.biojj.modules.activity.services;


import io.github.biojj.modules.activity.model.Activity;
import io.github.biojj.modules.activity.repository.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity save(Activity actiActivity) {

        return activityRepository.save(actiActivity);
    }

    public Page<Activity> findAll(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }

    public Activity findById(Long id) {
        return activityRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "actiActivity não encontrado"));
    }

    public void delete(Long id) {
        activityRepository
                .findById(id)
                .map(actiActivity -> {
                    activityRepository.delete(actiActivity);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "actiActivity não encontrado"));
    }

    public Activity update(Long id,
                           Activity actiActivityDTO) {

        return activityRepository
                .findById(id)
                .map(actiActivity -> {
                    actiActivity.setDescription(actiActivityDTO.getDescription());
                    actiActivity.setStartDate(actiActivityDTO.getStartDate());
                    actiActivity.setExpectedEndDate(actiActivityDTO.getExpectedEndDate());
                    actiActivity.setStatus(actiActivityDTO.getStatus());

                    return activityRepository.save(actiActivity);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "actiActivity não encontrado"));
    }

}
