package br.edu.iftm.rastreamento.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.iftm.rastreamento.model.Pacote;

@Repository
public interface PacoteRepository extends CrudRepository<Pacote, Long> {
    List<Pacote> findPacotesByStatus(String status);
    List<Pacote> findPacotesByDestinatario(String destinatario);
}
