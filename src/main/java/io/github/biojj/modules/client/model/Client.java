package io.github.biojj.modules.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;

@Entity
@Table(name = "clients")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email()
    @Column(unique = true)
    private String email;

    @NotBlank(message = "{campo.nome.obrigatorio}")
    @Column(unique = true, nullable = false)
    private String name;

}
