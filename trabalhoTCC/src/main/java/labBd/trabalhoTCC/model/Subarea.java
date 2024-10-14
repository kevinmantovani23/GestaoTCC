package labBd.trabalhoTCC.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "subarea")
@Data
public class Subarea {
		@Id
	    @Column(name = "codigo")
	    private int codigo;

	    @Column(name = "nome", length = 100, nullable = false)
	    private String nome;  // Nome da subárea do conhecimento

	    @ManyToOne
	    @JoinColumn(name = "codigoArea")
	    private Area area;
}
