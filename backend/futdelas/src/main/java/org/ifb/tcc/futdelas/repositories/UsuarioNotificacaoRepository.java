package org.ifb.tcc.futdelas.repositories;

import java.util.List;

import org.ifb.tcc.futdelas.entities.UsuarioNotificacao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioNotificacaoRepository implements PanacheRepository<UsuarioNotificacao> {
    public List<UsuarioNotificacao> buscarAtivos() {
        return list("notificacoesAtivas = true");
    }
}

