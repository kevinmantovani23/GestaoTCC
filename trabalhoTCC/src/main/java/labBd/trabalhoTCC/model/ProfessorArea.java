package labBd.trabalhoTCC.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "professorarea")
@Data
@IdClass(ProfessorAreaID.class)
public class ProfessorArea {
	
	@Id
    @ManyToOne
    @JoinColumn(name = "professorCodigo", nullable = false)
    private Professor professor;

    @Id
    @ManyToOne
    @JoinColumn(name = "areaCodigo", nullable = false)
    private Area area;
}
