package io.github.biojj.modules.activity.repository;

import io.github.biojj.modules.activity.model.Activity;
import io.github.biojj.modules.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
