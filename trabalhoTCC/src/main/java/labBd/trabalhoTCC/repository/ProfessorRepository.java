package labBd.trabalhoTCC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import labBd.trabalhoTCC.model.Professor;

import java.util.List;


public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    
	@Query(value="SELECT p.* FROM Professor p JOIN ProfessorArea pa ON p.codigo = pa.professorCodigo JOIN"
			+ " Area ar ON pa.areaCodigo = ar.nome WHERE ar.nome LIKE %:nomArea%", nativeQuery= true)
    List<Professor> findByNomeArea(@Param("nomArea") String nomArea);
    
    @Query(value = "SELECT dbo.fn_QuantidadeGruposPorProfessor(:codigoProfessor)", nativeQuery = true)
    public int quantidadeGrupos(@Param("codigoProfessor")int codigoProfessor);
}
