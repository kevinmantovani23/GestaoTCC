package labBd.trabalhoTCC.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import labBd.trabalhoTCC.model.Apresentacao;

public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Integer>{

	List<Apresentacao> findByDataApresentacao(LocalDate data);
}
