package io.github.biojj.modules.project.model;

import io.github.biojj.modules.client.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do projeto é obrigatório")
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @NotNull(message = "A data de início é obrigatória")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "A data de término prevista é obrigatória")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expected_end_date", nullable = false)
    private LocalDate expectedEndDate;

    @NotBlank(message = "O status do projeto é obrigatório")
    @Column(name = "project_status", nullable = false)
    private String projectStatus;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
