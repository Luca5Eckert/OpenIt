package br.centroweg.open_it.module.access.domain.port;

import br.centroweg.open_it.module.access.domain.model.Access;

import java.util.Optional;

public interface AccessRepository {
    void save(Access access);

    Optional<Access> findByCodeAndExitIsNull(Integer code);
}
