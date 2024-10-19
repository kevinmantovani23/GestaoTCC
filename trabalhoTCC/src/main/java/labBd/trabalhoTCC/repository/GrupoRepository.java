package labBd.trabalhoTCC.repository;
import labBd.trabalhoTCC.model.Grupo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

   
	  @Query("SELECT g FROM Grupo g JOIN Aluno a ON g.codigo = a.codigoGrupo WHERE a.nome LIKE %:nomeIntegrante%")
	  List<Grupo> findByNomeIntegrante(@Param("nomeIntegrante") String nomeIntegrante);

	   
	   @Query("SELECT g FROM Grupo g JOIN Professor p ON g.codigoProfessor = p.codigo WHERE p.nome LIKE %:nomeProfessor%")
	   List<Grupo> findByNomeProfessor(@Param("nomeProfessor") String nomeProfessor);

	   
	   @Query("SELECT g FROM Grupo g JOIN Apresentacao a ON g.codigo = a.codigoGrupo WHERE a.dataApresentacao = :data")
	   List<Grupo> findByDataAgendamento(@Param("data") LocalDate data);
}