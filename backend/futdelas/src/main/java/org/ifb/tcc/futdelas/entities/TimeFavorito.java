package org.ifb.tcc.futdelas.entities;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "times_favoritos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeFavorito extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "time_id", nullable = false)
    private Integer timeId;
    
    @Column(name = "usuario_uid", nullable = false)
    private String usuarioUid;
    
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    
    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }
}
