package br.centroweg.open_it.module.access.infrastructure.persistence.repository;

import br.centroweg.open_it.module.access.domain.model.Access;
import br.centroweg.open_it.module.access.domain.port.AccessRepository;
import br.centroweg.open_it.module.access.infrastructure.persistence.entity.AccessEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccessRepositoryAdapter implements AccessRepository {

    private final JpaAccessRepository jpaAccessRepository;

    public AccessRepositoryAdapter(JpaAccessRepository jpaAccessRepository) {
        this.jpaAccessRepository = jpaAccessRepository;
    }

    @Override
    public void save(Access access) {
        var accessEntity = AccessEntity.of(access);
        jpaAccessRepository.save(accessEntity);
    }

    @Override
    public Optional<Access> findByCodeAndExitIsNull(Integer code) {
        return jpaAccessRepository.findByCodeAndExitIsNull(code)
                .map(AccessEntity::toDomain);
    }

}
