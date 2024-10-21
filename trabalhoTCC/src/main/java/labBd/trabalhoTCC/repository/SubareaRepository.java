package labBd.trabalhoTCC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import labBd.trabalhoTCC.model.Subarea;

public interface SubareaRepository extends JpaRepository<Subarea, String>{

	@Query(value = "SELECT sb.* FROM SubArea sb JOIN Area a ON sb.codigoArea = a.nome WHERE a.nome LIKE %:nomeArea%", nativeQuery = true)
	List<Subarea> findByNomeArea(@Param("nomeArea") String nomeArea);
}
