package labBd.trabalhoTCC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import labBd.trabalhoTCC.model.Banca;
import labBd.trabalhoTCC.model.BancaID;

public interface BancaRepository extends JpaRepository<Banca, BancaID> {

	 @Modifying
	 @Transactional
	 @Query(value = "INSERT INTO banca (apresentacaoCodigo, professorCodigo) VALUES (:apresentacaoCodigo, :professorCodigo)", nativeQuery = true)
	 void addProfessores(@Param("apresentacaoCodigo") int apresentacaoCodigo, @Param("professorCodigo") List<Integer> professorCodigos);
}
