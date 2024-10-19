package labBd.trabalhoTCC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import labBd.trabalhoTCC.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, String>{

	  List<Aluno> findByPercentualConclusao(Double percentual);
	  List<Aluno> findByNome(String nome);
	  Aluno findByRa(String ra);
}
