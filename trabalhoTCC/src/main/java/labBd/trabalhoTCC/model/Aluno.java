package labBd.trabalhoTCC.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "aluno")
@Data
public class Aluno {
	
	 	@Id
	    @Column(name = "ra", length = 20, nullable = false)
	    private String ra;  // RA do aluno, identificador único

	    @Column(name = "nome", length = 100, nullable = false)
	    private String nome;  // Nome do aluno

	    @Column(name = "percentual_conclusao", nullable = false)
	    private Double percentualConclusao;  // Percentual de conclusão do curso

	   
}
