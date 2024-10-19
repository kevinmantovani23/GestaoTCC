package labBd.trabalhoTCC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import labBd.trabalhoTCC.model.Professor;

import java.util.List;


public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    
	@Query("SELECT p FROM Professor p JOIN ProfessorArea pa ON p.codigo = pa.professorCodigo WHERE pa.areaCodigo = :codArea")
    List<Professor> findByAreaId(int codArea);
    
    @Query(value = "SELECT dbo.fn_QuantidadeGruposPorProfessor(:codigoProfessor)", nativeQuery = true)
    public int quantidadeGrupos(int codigoProfessor);
}
