package org.ifb.tcc.futdelas.repositories;

import org.ifb.tcc.futdelas.entities.Notificacao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificacaoRepository implements PanacheRepository<Notificacao> {

}

