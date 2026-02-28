package br.centroweg.libera_ai.domain.port;

import br.centroweg.libera_ai.domain.model.Access;

import java.util.Optional;

public interface AccessRepository {
    void save(Access access);

    Optional<Access> findByCodeAndExitIsNull(Integer code);
}
