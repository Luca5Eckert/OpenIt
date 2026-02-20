package br.centroweg.libera_ai.infrastructure.persistence.repository;

import br.centroweg.libera_ai.domain.port.AccessRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AccessRepositoryAdapter implements AccessRepository {

    private final JpaAccessRepository jpaAccessRepository;

    public AccessRepositoryAdapter(JpaAccessRepository jpaAccessRepository) {
        this.jpaAccessRepository = jpaAccessRepository;
    }
}
