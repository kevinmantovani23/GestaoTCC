package labBd.trabalhoTCC.repository;
import labBd.trabalhoTCC.model.Grupo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

   
	  @Query(value = "SELECT g.* FROM Grupo g JOIN Aluno a ON g.codigo = a.codigoGrupo WHERE a.nome LIKE %:nomeIntegrante%", nativeQuery = true)
	  List<Grupo> findByNomeAluno(@Param("nomeIntegrante") String nomeIntegrante);

	   
	   @Query(value ="SELECT g.* FROM Grupo g JOIN Professor p ON g.codigoProfessor = p.codigo WHERE p.nome LIKE %:nomeProfessor%", nativeQuery = true)
	   List<Grupo> findByNomeProfessor(@Param("nomeProfessor") String nomeProfessor);

	   
	   @Query(value ="SELECT * FROM Grupo g JOIN Apresentacao a ON g.codigo = a.codigoGrupo WHERE a.dataApresentacao = :data", nativeQuery = true)
	   List<Grupo> findByDataAgendamento(@Param("data") LocalDate data);
}