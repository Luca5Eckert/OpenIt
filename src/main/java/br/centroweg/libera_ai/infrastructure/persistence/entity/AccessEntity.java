package br.centroweg.libera_ai.infrastructure.persistence.entity;

import br.centroweg.libera_ai.domain.model.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "access")
public record AccessEntity(
        int id,
        int code,
        LocalDateTime entry,
        LocalDateTime exit
) {

    public static AccessEntity of(Access access) {
        return new AccessEntity(
                access.getId(),
                access.getCode(),
                access.getEntry(),
                access.getExit()
        );
    }

    public Access toDomain() {
        return new Access(
                this.id,
                this.code,
                this.entry,
                this.exit
        );
    }
}
