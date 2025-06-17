package org.ifb.tcc.futdelas.repositories;

import java.util.Optional;

import org.ifb.tcc.futdelas.entities.TimeFavorito;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimeFavoritoRepository implements PanacheRepository<TimeFavorito> {
    
    public Optional<TimeFavorito> buscarPorUsuarioETime(String usuarioUid, Integer timeId) {
        return find("usuarioUid = ?1 and timeId = ?2", usuarioUid, timeId).firstResultOptional();
    }

    public TimeFavorito buscarPorUsuario(String usuarioUid) {
        return find("usuarioUid = ?1", usuarioUid).firstResult();
    }
}
