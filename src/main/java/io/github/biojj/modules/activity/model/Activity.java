package io.github.biojj.modules.activity.model;

import io.github.biojj.modules.client.model.Client;
import io.github.biojj.modules.project.model.Project;
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
@Table(name = "activities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data de início é obrigatória")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "A data de término prevista é obrigatória")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expected_end_date", nullable = false)
    private LocalDate expectedEndDate;

    @NotBlank(message = "O status da atividade é obrigatório")
    @Column(name = "project_status", nullable = false)
    private String status;

    @NotBlank(message = "A descrição da atividade é obrigatório")
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
