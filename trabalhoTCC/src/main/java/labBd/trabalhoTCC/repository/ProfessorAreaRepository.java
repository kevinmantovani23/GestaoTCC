package labBd.trabalhoTCC.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import labBd.trabalhoTCC.model.ProfessorArea;
import labBd.trabalhoTCC.model.ProfessorAreaID;

public interface ProfessorAreaRepository extends JpaRepository<ProfessorArea, ProfessorAreaID>{
	
	
}
