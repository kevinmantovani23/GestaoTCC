package labBd.trabalhoTCC.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "banca")
@Data
@IdClass(BancaID.class)
public class Banca {
	
		@Id
	    @ManyToOne
	    @JoinColumn(name = "professorCodigo", nullable = false)
	    private Professor professor;

	    @Id
	    @ManyToOne
	    @JoinColumn(name = "apresentacaoCodigo", nullable = false)
	    private Apresentacao apresentacao;

}
