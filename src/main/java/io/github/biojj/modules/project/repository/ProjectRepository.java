package io.github.biojj.modules.client.repository;

import io.github.biojj.modules.client.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Project, Long> {

    boolean existsByEmail(String email);
}
