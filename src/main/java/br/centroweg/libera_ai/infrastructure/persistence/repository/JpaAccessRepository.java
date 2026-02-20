package br.centroweg.libera_ai.infrastructure.persistence.repository;

import br.centroweg.libera_ai.infrastructure.persistence.entity.AccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccessRepository extends JpaRepository<AccessEntity, Integer> {
}
