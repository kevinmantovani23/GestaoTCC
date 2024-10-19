package labBd.trabalhoTCC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import labBd.trabalhoTCC.model.Area;

public interface AreaRepository extends JpaRepository<Area, String> {
	
	@Query(value = "SELECT a FROM Area a WHERE a.nome LIKE CONCAT('%', :nome, '%')")
	List<Area> findByNome(@Param("nome")String nome);
}
