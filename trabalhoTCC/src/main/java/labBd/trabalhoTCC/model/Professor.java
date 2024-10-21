package labBd.trabalhoTCC.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "professor")
@Data
public class Professor {
		@Id
	    @Column(name = "codigo")
	    private int codigo;

	    @Column(name = "nome", length = 100, nullable = false)
	    private String nome;  // Nome do professor

	    @Column(name = "titulacao", length = 50, nullable = false)
	    private String titulacao;  // Titulação do professor (Ex: Doutor, Mestre)
	    
	    @OneToMany(mappedBy = "professor")
	    private List<ProfessorArea> professorAreas;
	    
	    @Transient
	    private int qtdGrupos;
}
