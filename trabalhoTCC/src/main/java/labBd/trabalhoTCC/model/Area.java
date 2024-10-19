package labBd.trabalhoTCC.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "area")
@Data
public class Area {
	
	 	@Id
	    @Column(name = "nome", length = 100, nullable = false)
	    private String nome;  
	    
	    @OneToMany(mappedBy = "area")
	    private List<ProfessorArea> professorAreas;
}
